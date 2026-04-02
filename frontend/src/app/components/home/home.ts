import { Component, ChangeDetectorRef } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-home',
  templateUrl: './home.html',
  styleUrls: ['./home.css'],
  standalone: false
})
export class HomeComponent {
  isLoginTab = true;
  isLoading = false;

  formData = {
    name: '',
    email: '',
    password: ''
  };

  constructor(
    private userService: UserService, 
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  toggleTab(isLogin: boolean) {
    this.isLoginTab = isLogin;
    this.cdr.detectChanges();
  }

  handleAuth() {
    if (this.isLoginTab) {
      this.login();
    } else {
      this.register();
    }
  }

  login() {
    this.isLoading = true;
    this.userService.login({ email: this.formData.email, password: this.formData.password }).subscribe({
      next: (res) => {
        this.isLoading = false;
        localStorage.setItem('userId', res.id.toString());
        this.router.navigate(['/dashboard']);
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.isLoading = false;
        Swal.fire({
          title: 'Erro',
          text: err.error?.message || 'Credenciais inválidas',
          icon: 'error',
          confirmButtonColor: '#D95360'
        });
        this.cdr.detectChanges();
      }
    });
  }

  register() {
    this.isLoading = true;
    this.userService.register(this.formData).subscribe({
      next: () => {
        this.isLoading = false;
        Swal.fire({
          title: 'Sucesso',
          text: 'Conta criada com sucesso! Agora você pode entrar.',
          icon: 'success',
          confirmButtonColor: '#D95360'
        });
        this.isLoginTab = true;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.isLoading = false;
        Swal.fire({
          title: 'Erro',
          text: err.error?.message || 'Falha ao criar conta. Tente outro e-mail.',
          icon: 'error',
          confirmButtonColor: '#D95360'
        });
        this.cdr.detectChanges();
      }
    });
  }
}