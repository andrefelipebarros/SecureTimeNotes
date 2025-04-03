import { CommonModule, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm: FormGroup;
  passwordStrength: string = '';  // Armazenar o estado da força da senha
  passwordStrengthPercentage: number = 0;  // Armazenar a porcentagem da força da senha

  // Armazenar o status dos requisitos de senha
  passwordRequirements = {
    length: false,
    uppercase: false,
    lowercase: false,
    number: false,
    specialChar: false
  };

  constructor(private fb: FormBuilder, private apiService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      login: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],
      terms: [false, [Validators.requiredTrue]]
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

  // Função para calcular a força da senha
  calculatePasswordStrength(password: string): void {
    // Atualizar os requisitos da senha
    this.passwordRequirements.length = password.length >= 8;
    this.passwordRequirements.uppercase = /[A-Z]/.test(password);
    this.passwordRequirements.lowercase = /[a-z]/.test(password);
    this.passwordRequirements.number = /\d/.test(password);
    this.passwordRequirements.specialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

    // Calculando a força da senha com base nos requisitos
    let strength = 0;
    
    if (this.passwordRequirements.length) strength += 20;
    if (this.passwordRequirements.uppercase) strength += 20;
    if (this.passwordRequirements.lowercase) strength += 20;
    if (this.passwordRequirements.number) strength += 20;
    if (this.passwordRequirements.specialChar) strength += 20;

    this.passwordStrengthPercentage = strength;

    // Determinar a força da senha com base na porcentagem
    if (strength < 50) {
      this.passwordStrength = 'Fraca';
    } else if (strength < 75) {
      this.passwordStrength = 'Media';
    } else {
      this.passwordStrength = 'Forte';
    }

    this.passwordStrength = this.passwordStrength.toLowerCase();
  }

  // Função chamada quando a senha é alterada
  onPasswordChange(): void {
    const password = this.registerForm.get('password')?.value;
    this.calculatePasswordStrength(password);
  }

  onSubmit(): void {
    if (this.registerForm.valid && this.validatePassword()) {
      const { login, password } = this.registerForm.value;
      const requestPayload = { username: login, password: password };

      this.apiService.post('auth/register', requestPayload, 'text').subscribe({
        next: (response) => {
          console.log('Usuário registrado com sucesso', response);
          this.router.navigate(['/login']);
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

  // Validação personalizada para a senha
  validatePassword(): boolean {
    const password = this.registerForm.get('password')?.value;
    const passwordPattern = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    return passwordPattern.test(password);
  }
}

