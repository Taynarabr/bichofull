import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router'; 

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  styleUrls: ['./login.css'],
  standalone: false 
})
export class LoginComponent {
  loginData = { email: '', password: '' };

  constructor(private userService: UserService, private router: Router) {}

  onLogin() {
    console.log('1. Iniciando tentativa de login...');
    this.userService.login(this.loginData).subscribe({
      next: (res: any) => {
        console.log('2. Resposta do servidor:', res);
        if (res && res.id) {
          localStorage.setItem('userId', res.id.toString());
          console.log('3. Redirecionando para o Dashboard...');
          this.router.navigate(['/dashboard']); 
        } else {
          alert('Erro: Resposta de login inválida.');
        }
      },
      error: (err) => {
        console.error('Erro no Login:', err);
        alert('Falha no login: verifique o seu e-mail e senha.');
      }
    });
  }
}