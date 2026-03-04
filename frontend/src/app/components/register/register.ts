import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
  standalone: false
})
export class RegisterComponent {
  userData = {
    name: '',
    email: '',
    password: ''
  };

  constructor(private userService: UserService) { }

  onSubmit() {
    this.userService.register(this.userData).subscribe({
      next: (res) => alert('Sucesso'),
      error: (err) => alert('Erro!')
    });
  }
}