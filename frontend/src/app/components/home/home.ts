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

  // Rola para o card de login/cadastro
  comecarJogar() {
    this.isLoginTab = false;
    const card = document.querySelector('.auth-card');
    card?.scrollIntoView({ behavior: 'smooth' });
    
    Swal.fire({
      toast: true,
      position: 'top-end',
      icon: 'info',
      title: 'Crie sua conta para começar!',
      showConfirmButton: false,
      timer: 3000
    });
  }

  verRegras() {
    Swal.fire({
      title: 'Como funciona?',
      text: 'O Jogo do Bicho Online permite que você aposte em seus animais favoritos de forma simples. Escolha o bicho, a milhar ou dezena, defina o valor e aguarde o sorteio em tempo real!',
      icon: 'question',
      confirmButtonColor: '#D95360',
      confirmButtonText: 'Entendido!'
    });
  }

  recuperarSenha() {
    Swal.fire({
        title: 'Redefinir Senha',
        html: `
        <div style="text-align: left;">
            <label class="small fw-bold">E-mail cadastrado:</label>
            <input type="email" id="reset-email" class="swal2-input" placeholder="seu@email.com">
            <label class="small fw-bold">Nova Senha:</label>
            <input type="password" id="reset-password" class="swal2-input" placeholder="Digite a nova senha">
            <label class="small fw-bold">Confirme a Senha:</label>
            <input type="password" id="reset-confirm" class="swal2-input" placeholder="Repita a nova senha">
        </div>
        `,
        icon: 'question',
        showCancelButton: true,
        confirmButtonText: 'Alterar Senha',
        cancelButtonText: 'Cancelar',
        confirmButtonColor: '#D95360',
        cancelButtonColor: '#6c757d',
        preConfirm: () => {
          const emailInput = document.getElementById('reset-email') as HTMLInputElement;
          const passwordInput = document.getElementById('reset-password') as HTMLInputElement;
          const confirmInput = document.getElementById('reset-confirm') as HTMLInputElement;

          const email = emailInput?.value;
          const password = passwordInput?.value;
          const confirm = confirmInput?.value;

          if (!email || !password || !confirm) {
              Swal.showValidationMessage('Preencha todos os campos corretamente');
              return false;
          }
          if (password !== confirm) {
              Swal.showValidationMessage('As senhas não coincidem');
              return false;
          }
          return { email, password };
        }
    }).then((result) => {
        if (result.isConfirmed && result.value) {
          this.isLoading = true;
          this.userService.resetPassword(result.value.email, result.value.password).subscribe({
              next: () => {
                this.isLoading = false;
                Swal.fire({
                    title: 'Sucesso!',
                    text: 'Sua senha foi atualizada. Você já pode fazer login!',
                    icon: 'success',
                    confirmButtonColor: '#D95360'
                });
                this.cdr.detectChanges();
              },
              error: (err) => {
                this.isLoading = false;
                const errorMsg = err.error?.message || err.error || 'Não foi possível encontrar este e-mail.';
                Swal.fire({ title: 'Erro', text: errorMsg, icon: 'error', confirmButtonColor: '#D95360' });
                this.cdr.detectChanges();
              }
          });
        }
    });
  }

  abrirRedeSocial(rede: string) {
    const redeFormatada = rede.charAt(0).toUpperCase() + rede.slice(1);
    Swal.fire({
      title: `Conectar com ${redeFormatada}`,
      text: `Deseja usar sua conta ${redeFormatada} para entrar no Bicho Full? Criaremos seu acesso automaticamente.`,
      icon: 'info',
      showCancelButton: true,
      confirmButtonColor: '#D95360',
      confirmButtonText: 'Sim, conectar!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.isLoading = true;
        const idUnico = Math.floor(Math.random() * 1000);
        const socialUser = {
          name: `Usuário ${redeFormatada}`,
          email: `${rede}_${idUnico}@social.com`,
          password: 'senha-padrao-social' 
        };

        this.userService.register(socialUser).subscribe({
          next: () => this.executarLoginSocial(socialUser.email, socialUser.password),
          error: () => this.executarLoginSocial(socialUser.email, socialUser.password)
        });
      }
    });
  }

  private executarLoginSocial(email: string, pass: string) {
    this.userService.login({ email, password: pass }).subscribe({
      next: (res) => {
        this.isLoading = false;
        localStorage.setItem('userId', res.id.toString());
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.isLoading = false;
        Swal.fire('Erro', 'Falha ao autenticar com rede social.', 'error');
      }
    });
  }

  contatoSuporte() {
    Swal.fire({
      title: 'Suporte Online',
      text: 'Deseja abrir o suporte via WhatsApp para tirar suas dúvidas?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#28a745',
      cancelButtonColor: '#6c757d',
      confirmButtonText: 'Sim, chamar no Zap',
      cancelButtonText: 'Agora não'
    }).then((result) => {
      if (result.isConfirmed) {
        window.open('https://wa.me/5500000000000', '_blank');
      }
    });
  }

  handleAuth() {
    if (this.isLoginTab) { this.login(); } else { this.register(); }
  }

  login() {
    this.isLoading = true;
    this.userService.login({ email: this.formData.email, password: this.formData.password }).subscribe({
      next: (res) => {
        localStorage.setItem('userId', res.id.toString());
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.isLoading = false;
        Swal.fire('Erro', 'E-mail ou senha incorretos.', 'error');
        this.cdr.detectChanges();
      }
    });
  }

  register() {
    this.isLoading = true;
    this.userService.register(this.formData).subscribe({
      next: () => {
        this.isLoading = false;
        Swal.fire('Sucesso', 'Conta criada! Faça login.', 'success');
        this.isLoginTab = true;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.isLoading = false;
        Swal.fire('Erro', 'Falha ao cadastrar. Tente novamente.', 'error');
        this.cdr.detectChanges();
      }
    });
  }

  compartilharSite() {
    const url = window.location.origin;
    const texto = "Vem jogar no Bicho Full! O melhor e mais seguro: ";
    if (navigator.share) {
        navigator.share({ title: 'Bicho Full', text: texto, url: url });
    } else {
        navigator.clipboard.writeText(`${texto} ${url}`);
        Swal.fire({ icon: 'success', title: 'Link copiado!', text: 'Link de divulgação copiado.', timer: 2000, showConfirmButton: false });
    }
  }
}