import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core'; 
import { UserService } from '../../services/user.service';
import { UserProfile } from '../../models/user.model';
import { DrawService } from '../../services/draw.service';
import { BetService } from '../../services/bet.service';
import { Draw } from '../../models/draw.model';
import { Router } from '@angular/router';
import Swal from 'sweetalert2'; 
import { interval, Subscription } from 'rxjs'; 

interface Animal { name: string; group: string; icon: string; dezenas: string; }

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  standalone: false
})
export class DashboardComponent implements OnInit, OnDestroy { 
  userProfile?: UserProfile;
  draws: Draw[] = [];
  userBets: any[] = []; 
  isLoading = true;
  
  autoRefreshSub?: Subscription; 

  activeTab: string = 'jogo';

  betType: string = 'GRUPO'; 
  selectedAmount: number = 0;
  selectedAnimal: Animal | null = null;
  betNumber: string = ''; 
  
  animalsList: Animal[] = [
    { name: 'Avestruz', group: '01', icon: '🐦', dezenas: '01-04' }, { name: 'Águia', group: '02', icon: '🦅', dezenas: '05-08' },
    { name: 'Burro', group: '03', icon: '🫏', dezenas: '09-12' }, { name: 'Borboleta', group: '04', icon: '🦋', dezenas: '13-16' },
    { name: 'Cachorro', group: '05', icon: '🐶', dezenas: '17-20' }, { name: 'Cabra', group: '06', icon: '🐐', dezenas: '21-24' },
    { name: 'Carneiro', group: '07', icon: '🐏', dezenas: '25-28' }, { name: 'Camelo', group: '08', icon: '🐪', dezenas: '29-32' },
    { name: 'Cobra', group: '09', icon: '🐍', dezenas: '33-36' }, { name: 'Coelho', group: '10', icon: '🐰', dezenas: '37-40' },
    { name: 'Cavalo', group: '11', icon: '🐎', dezenas: '41-44' }, { name: 'Elefante', group: '12', icon: '🐘', dezenas: '45-48' },
    { name: 'Galo', group: '13', icon: '🐓', dezenas: '49-52' }, { name: 'Gato', group: '14', icon: '🐈', dezenas: '53-56' },
    { name: 'Jacaré', group: '15', icon: '🐊', dezenas: '57-60' }, { name: 'Leão', group: '16', icon: '🦁', dezenas: '61-64' },
    { name: 'Macaco', group: '17', icon: '🐒', dezenas: '65-68' }, { name: 'Porco', group: '18', icon: '🐷', dezenas: '69-72' },
    { name: 'Pavão', group: '19', icon: '🦚', dezenas: '73-76' }, { name: 'Peru', group: '20', icon: '🦃', dezenas: '77-80' },
    { name: 'Touro', group: '21', icon: '🐂', dezenas: '81-84' }, { name: 'Tigre', group: '22', icon: '🐅', dezenas: '85-88' },
    { name: 'Urso', group: '23', icon: '🐻', dezenas: '89-92' }, { name: 'Veado', group: '24', icon: '🦌', dezenas: '93-96' },
    { name: 'Vaca', group: '25', icon: '🐄', dezenas: '97-00' }
  ];

  estatisticas = {
    vitoriasTotais: 0,
    maiorPremio: 0,
    diasSeguidos: 1, 
    totalGanhos: 0
  };

  dicaDeHoje = "O grupo do Leão (16) está muito forte hoje. Pode ser uma ótima aposta!";

  sorteiosDoDia = [
    { nome: 'PTM', hora: '11:00', realizado: false },
    { nome: 'PT', hora: '14:00', realizado: false },
    { nome: 'PTV', hora: '16:00', realizado: false },
    { nome: 'PTN', hora: '18:00', realizado: false },
    { nome: 'COR', hora: '21:00', realizado: false }
  ];

  depositAmount: number = 0;
  pixGenerated: boolean = false;
  pixCode: string = '00020126580014br.gov.bcb.pix0136pix@jogodobicho.com.br0203Pix520400005303986540510.005802BR5909SAO PAULO6009JOGO BIXO6207050300063041D3D';
  
  // ==========================================
  // VARIÁVEIS DO PERFIL
  // ==========================================
  editProfileData = {
    name: '',
    email: '',
    password: ''
  };

  get progressoDiario() {
    const realizados = this.sorteiosDoDia.filter(s => s.realizado).length;
    return (realizados / this.sorteiosDoDia.length) * 100;
  }

  get proximoSorteio() {
    return this.sorteiosDoDia.find(s => !s.realizado) || this.sorteiosDoDia[0];
  }

  get ultimasTresApostas() {
    return this.userBets ? this.userBets.slice(0, 3) : [];
  }

  constructor(
    private userService: UserService, 
    private drawService: DrawService,
    private betService: BetService,
    private router: Router,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit() {
    const idLogado = localStorage.getItem('userId');
    if (idLogado && idLogado !== 'undefined') {
      this.getUserData(Number(idLogado)); 
      this.listarSorteios();
      this.carregarApostas(Number(idLogado));
      this.atualizarStatusSorteios(); 
      
      this.iniciarAutoRefresh(Number(idLogado)); 
    } else {
      this.router.navigate(['/login']);
    }
  }

  ngOnDestroy() {
    if (this.autoRefreshSub) {
      this.autoRefreshSub.unsubscribe();
    }
  }

  iniciarAutoRefresh(userId: number) {
    this.autoRefreshSub = interval(10000).subscribe(() => {
      this.getUserData(userId, true); 
      this.listarSorteios();
      this.carregarApostas(userId);
      this.atualizarStatusSorteios();
    });
  }

  atualizarStatusSorteios() {
    const agora = new Date();
    const horaAtual = agora.getHours().toString().padStart(2, '0') + ":" + agora.getMinutes().toString().padStart(2, '0');
    
    this.sorteiosDoDia.forEach(s => {
      s.realizado = horaAtual >= s.hora;
    });
  }

  calcularEstatisticas() {
    if (!this.userBets || this.userBets.length === 0) return;

    const apostasGanhas = this.userBets.filter(bet => bet.status === 'GANHOU');

    this.estatisticas.vitoriasTotais = apostasGanhas.length;
    this.estatisticas.totalGanhos = apostasGanhas.reduce((acumulador, aposta) => acumulador + (aposta.prize || 0), 0);
    
    if (apostasGanhas.length > 0) {
      this.estatisticas.maiorPremio = Math.max(...apostasGanhas.map(aposta => aposta.prize || 0));
    }
  }

  getUserData(id: number, silent: boolean = false) {
    if (!silent) this.isLoading = true; 
    
    this.userService.getUserById(id).subscribe({
      next: (data) => {
        this.userProfile = data;
        if (!silent) this.isLoading = false;
        this.cdr.detectChanges(); 
      },
      error: (err) => {
        console.error('Erro ao buscar dados:', err);
        if (!silent) this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  listarSorteios() {
    this.drawService.findAll().subscribe({
      next: (data) => {
        this.draws = data.reverse(); 
        this.cdr.detectChanges();
      }
    });
  }

  carregarApostas(userId: number) {
    this.betService.getUserBets(userId).subscribe({
      next: (data) => {
        this.userBets = data.reverse(); 
        this.calcularEstatisticas(); 
        this.cdr.detectChanges();
      }
    });
  }

  confirmBet() {
    if (!this.userProfile || !this.isBetValid()) return;

    this.isLoading = true; 
    const betRequest = {
      type: this.betType,
      value: this.selectedAmount,
      choice: this.betType === 'GRUPO' ? this.selectedAnimal?.group : this.betNumber,
      animalGroup: this.betType === 'GRUPO' ? Number(this.selectedAnimal?.group) : null
    };

    this.betService.placeBet(this.userProfile.id, betRequest).subscribe({
      next: (res) => {
        Swal.fire({
          title: 'Aposta Confirmada!',
          text: 'Sua aposta foi registrada. Boa sorte!',
          icon: 'success',
          confirmButtonColor: '#D95360'
        });

        this.getUserData(this.userProfile!.id, true); 
        this.carregarApostas(this.userProfile!.id); 
        this.selectedAmount = 0;
        this.selectedAnimal = null;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        Swal.fire({
          title: 'Ops!',
          text: err.error?.message || 'Erro ao realizar a aposta.',
          icon: 'error',
          confirmButtonColor: '#D95360'
        });

        this.isLoading = false; 
        this.cdr.detectChanges();
      }
    });
  }

  isBetValid(): boolean {
    if (this.selectedAmount <= 0) return false;
    if (this.betType === 'GRUPO' && !this.selectedAnimal) return false;
    if (this.betType !== 'GRUPO' && !this.betNumber) return false;
    return true;
  }

  selectAnimal(a: Animal) {
    if (this.betType === 'GRUPO') this.selectedAnimal = a;
  }

  gerarSorteio() {
    this.drawService.generate().subscribe(() => {
      Swal.fire({
        title: 'Sorteio Realizado!',
        text: 'Os resultados já estão disponíveis no sistema.',
        icon: 'success',
        timer: 2000,
        showConfirmButton: false
      });
      this.listarSorteios();
    });
  }

  logout() {
    if (this.autoRefreshSub) {
      this.autoRefreshSub.unsubscribe(); 
    }
    localStorage.removeItem('userId'); 
    this.router.navigate(['/login']);  
  }

  setAmountShortcut(value: number) {
    this.selectedAmount = value;
  }

  getPotencialGanho(): number {
    const amount = Number(this.selectedAmount) || 0;
    if (amount <= 0) return 0;

    switch (this.betType) {
      case 'GRUPO': return amount * 18;
      case 'DEZENA': return amount * 60;
      case 'MILHAR': return amount * 4000;
      default: return 0;
    }
  }

  setDepositAmount(amount: number) {
    this.depositAmount = amount;
    this.pixGenerated = false; 
  }

  generatePix() {
    if (this.depositAmount < 1) {
      Swal.fire({
        title: 'Atenção',
        text: 'O valor mínimo de depósito é R$ 1,00.',
        icon: 'warning',
        confirmButtonColor: '#28a745'
      });
      return;
    }
    this.pixGenerated = true;
  }

  copyPix() {
    navigator.clipboard.writeText(this.pixCode).then(() => {
      const Toast = Swal.mixin({
        toast: true,
        position: 'top-end',
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast: HTMLElement) => {
          toast.addEventListener('mouseenter', Swal.stopTimer)
          toast.addEventListener('mouseleave', Swal.resumeTimer)
        }
      });

      Toast.fire({
        icon: 'success',
        title: 'Código PIX copiado!'
      });
    });
  }

  simulatePayment() {
    if (!this.userProfile) return;

    this.isLoading = true; 

    this.userService.deposit(this.userProfile.id, this.depositAmount).subscribe({
      next: (updatedUser) => {
        this.userProfile = updatedUser;
        
        Swal.fire({
          title: 'Pagamento Aprovado!',
          html: `Seu depósito de <strong>R$ ${this.depositAmount.toFixed(2)}</strong> caiu na conta.`,
          icon: 'success',
          confirmButtonText: 'Ver Saldo',
          confirmButtonColor: '#28a745'
        });
        
        this.depositAmount = 0;
        this.pixGenerated = false;
        this.activeTab = 'jogo';
        
        this.isLoading = false; 
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Erro ao fazer depósito:', err);
        Swal.fire({
          title: 'Falha no Pagamento',
          text: 'Não conseguimos conectar com o banco. Tente novamente.',
          icon: 'error',
          confirmButtonColor: '#D95360'
        });
        this.isLoading = false; 
        this.cdr.detectChanges();
      }
    });
  }

  // ==========================================
  // FUNÇÕES DO PERFIL
  // ==========================================
  abrirPerfil() {
    if (this.userProfile) {
      this.editProfileData.name = this.userProfile.name;
      this.editProfileData.email = this.userProfile.email;
      this.editProfileData.password = ''; // Deixamos a senha em branco por segurança
      this.activeTab = 'perfil';
    }
  }

  saveProfile() {
    if (!this.userProfile) return;
    this.isLoading = true;
    
    // Mandamos apenas Nome e E-mail por padrão
    const payload: any = {
      name: this.editProfileData.name,
      email: this.editProfileData.email
    };

    // Só adicionamos a senha no pacote se o usuário de fato digitou alguma coisa
    if (this.editProfileData.password && this.editProfileData.password.trim() !== '') {
      payload.password = this.editProfileData.password;
    }

    this.userService.updateUser(this.userProfile.id, payload).subscribe({
      next: (res) => {
        this.isLoading = false;
        this.userProfile = res;
        this.editProfileData.password = ''; 
        this.cdr.detectChanges(); 

        Swal.fire({
          title: 'Sucesso!',
          text: 'Seus dados foram atualizados.',
          icon: 'success',
          confirmButtonColor: '#D95360'
        });
      },
      error: (err) => {
        this.isLoading = false;
        this.cdr.detectChanges(); 

        console.error('Erro detalhado:', err);
        Swal.fire({
          title: 'Ops!',
          text: err.error?.message || 'Erro ao atualizar o perfil. Verifique se o e-mail já existe.',
          icon: 'error',
          confirmButtonColor: '#D95360'
        });
      }
    });
  }
}