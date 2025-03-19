import { CommonModule, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private apiService: AuthService) {
    this.registerForm = this.fb.group({
      login: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]]
    }, { validator: this.passwordMatchValidator });
  }

  // Validação de correspondência das senhas
  passwordMatchValidator(group: FormGroup): { [key: string]: boolean } | null {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    if (password !== confirmPassword) {
      return { 'mismatch': true };
    }
    return null;
  }

  // Validação personalizada para a senha
  validatePassword(): boolean {
    const password = this.registerForm.get('password')?.value;
    // Exemplo de uma validação simples (mínimo de 1 letra maiúscula, 1 número e 1 caractere especial)
    const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordPattern.test(password);
  }

  onSubmit(): void {
    if (this.registerForm.valid && this.validatePassword()) {
      const { login, password } = this.registerForm.value;  // Extrai login (username) e password

      const requestPayload = {
        username: login,  // 'login' será usado como 'username'
        password: password
      };

      this.apiService.post('auth/register', requestPayload).subscribe({
        next: (response) => {
          console.log('Usuário registrado com sucesso', response);
        },
        error: (error) => {
          console.error('Erro ao registrar usuário', error);
        }
      });
    } else if (!this.validatePassword()) {
      console.log('A senha não atende aos requisitos');
    } else {
      console.log('Formulário inválido');
    }
  }
}
