import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; 
import { UserService } from '../../services/user.service';
import { UserProfile } from '../../models/user.model';
import { DrawService } from '../../services/draw.service';
import { BetService } from '../../services/bet.service';
import { Draw } from '../../models/draw.model';
import { Router } from '@angular/router';

interface Animal { name: string; group: string; icon: string; dezenas: string; }

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  standalone: false
})
export class DashboardComponent implements OnInit {
  userProfile?: UserProfile;
  draws: Draw[] = [];
  userBets: any[] = []; 
  isLoading = true;

  // Identificador da aba ativa
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
    vitoriasTotais: 124,
    maiorPremio: 12450.00,
    diasSeguidos: 12,
    totalGanhos: 48200.00
  };

  dicaDeHoje = "O grupo do Leão (16) não é sorteado na cabeça há 5 dias. Pode ser uma boa aposta para a PT!";

  sorteiosDoDia = [
    { nome: 'PTM', hora: '11:00', realizado: true },
    { nome: 'PT', hora: '14:00', realizado: true },
    { nome: 'PTV', hora: '16:00', realizado: false },
    { nome: 'PTN', hora: '18:00', realizado: false },
    { nome: 'COR', hora: '21:00', realizado: false }
  ];

  get progressoDiario() {
    const realizados = this.sorteiosDoDia.filter(s => s.realizado).length;
    return (realizados / this.sorteiosDoDia.length) * 100;
  }

  get proximoSorteio() {
    return this.sorteiosDoDia.find(s => !s.realizado) || this.sorteiosDoDia[0];
  }

  // Filtra apenas as 3 apostas mais recentes para a home
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
    } else {
      this.router.navigate(['/login']);
    }
  }

  getUserData(id: number) {
    this.isLoading = true;
    this.userService.getUserById(id).subscribe({
      next: (data) => {
        this.userProfile = data;
        this.isLoading = false;
        this.cdr.detectChanges(); 
      },
      error: (err) => {
        console.error('Erro ao buscar dados:', err);
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  listarSorteios() {
    this.drawService.findAll().subscribe({
      next: (data) => {
        this.draws = data;
        this.cdr.detectChanges();
      }
    });
  }

  carregarApostas(userId: number) {
    this.betService.getUserBets(userId).subscribe({
      next: (data) => {
        this.userBets = data;
        this.cdr.detectChanges();
      }
    });
  }

  confirmBet() {
    if (!this.userProfile || !this.isBetValid()) return;

    const betRequest = {
      type: this.betType,
      value: this.selectedAmount,
      choice: this.betType === 'GRUPO' ? this.selectedAnimal?.group : this.betNumber,
      animalGroup: this.betType === 'GRUPO' ? Number(this.selectedAnimal?.group) : null
    };

    this.betService.placeBet(this.userProfile.id, betRequest).subscribe({
      next: (res) => {
        alert('Aposta realizada com sucesso!');
        this.getUserData(this.userProfile!.id); 
        this.carregarApostas(this.userProfile!.id); 
        this.selectedAmount = 0;
        this.selectedAnimal = null;
      },
      error: (err) => alert(err.error?.message || 'Erro ao realizar aposta.')
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
    this.drawService.generate().subscribe(() => this.listarSorteios());
  }

  logout() {
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
}