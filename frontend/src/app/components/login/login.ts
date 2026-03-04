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
  loginData = {
    email: '',
    password: ''
  };

  constructor(
    private userService: UserService, 
    private router: Router
  ) {}

  onLogin() {
    this.userService.login(this.loginData).subscribe({
      next: (res) => {
        alert('Login realizado com sucesso!');
        this.router.navigate(['/home']); 
      },
      error: (err) => {
        alert('Falha no login: verifique e-mail e senha.');
        console.error(err);
      }
    });
  }
}