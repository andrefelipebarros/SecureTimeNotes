# Stage de build — já vem com Maven + Temurin 17
FROM maven:3.9-eclipse-temurin-17 AS builder

# melhor trabalhar dentro de /app
WORKDIR /app

# copia apenas o pom primeiro para cache de dependências (opcional)
COPY securetimenotes/pom.xml ./

# copia o resto do código
COPY securetimenotes/ ./ 

# build da aplicação (skip tests se desejar)
RUN mvn -B clean package -DskipTests

# Stage de runtime — usar Temurin (JRE) para imagem menor
FROM eclipse-temurin:17-jre-jammy

EXPOSE 8081

# copia o jar construído do stage 'builder'
COPY --from=builder /app/target/*.jar /app/app.jar

# Adicionando limite de memória no comando para plano free Railway
ENTRYPOINT [ "java", "-XX:-UseContainerSupport", "-jar", "app.jar" ]
