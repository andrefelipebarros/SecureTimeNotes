import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../service/auth.service';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  loginError: string | null = null;

  constructor(private fb: FormBuilder, private apiService: AuthService, private router: Router) {
    this.loginForm = this.fb.group({
      login: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const { login, password } = this.loginForm.value;

      const requestPayload = {
        username: login,
        password: password
      };

      this.apiService.post('auth/login', requestPayload).subscribe({
        next: (response: any) => {
          console.log('Login bem-sucedido', response);
          this.loginError = null;
          localStorage.setItem('token', response.token); // Armazena o token no localStorage
          this.router.navigate(['/home']);
        },
        error: (error) => {
          console.error('Erro ao fazer login', error);
          this.loginError = 'Usuário ou senha inválidos';
        }
      });
    } else {
      console.log('Formulário inválido');
    }
  }
}