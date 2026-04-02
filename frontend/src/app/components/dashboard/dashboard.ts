import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; // <-- 1. Importado aqui
import { UserService } from '../../services/user.service';
import { UserProfile } from '../../models/user.model';
import { DrawService } from '../../services/draw.service';
import { BetService } from '../../services/bet.service';
import { Draw } from '../../models/draw.model';
import { Router } from '@angular/router';

interface Animal { name: string; group: string; icon: string; }

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

  betType: string = 'GRUPO'; 
  selectedAmount: number = 0;
  selectedAnimal: Animal | null = null;
  betNumber: string = ''; 
  
  animalsList: Animal[] = [
    { name: 'Avestruz', group: '01', icon: '🐦' }, { name: 'Águia', group: '02', icon: '🦅' },
    { name: 'Burro', group: '03', icon: '🫏' }, { name: 'Borboleta', group: '04', icon: '🦋' },
    { name: 'Cachorro', group: '05', icon: '🐶' }, { name: 'Cabra', group: '06', icon: '🐐' },
    { name: 'Carneiro', group: '07', icon: '🐏' }, { name: 'Camelo', group: '08', icon: '🐪' },
    { name: 'Cobra', group: '09', icon: '🐍' }, { name: 'Coelho', group: '10', icon: '🐰' },
    { name: 'Cavalo', group: '11', icon: '🐎' }, { name: 'Elefante', group: '12', icon: '🐘' },
    { name: 'Galo', group: '13', icon: '🐓' }, { name: 'Gato', group: '14', icon: '🐈' },
    { name: 'Jacaré', group: '15', icon: '🐊' }, { name: 'Leão', group: '16', icon: '🦁' },
    { name: 'Macaco', group: '17', icon: '🐒' }, { name: 'Porco', group: '18', icon: '🐷' },
    { name: 'Pavão', group: '19', icon: '🦚' }, { name: 'Peru', group: '20', icon: '🦃' },
    { name: 'Touro', group: '21', icon: '🐂' }, { name: 'Tigre', group: '22', icon: '🐅' },
    { name: 'Urso', group: '23', icon: '🐻' }, { name: 'Veado', group: '24', icon: '🦌' },
    { name: 'Vaca', group: '25', icon: '🐄' }
  ];

  constructor(
    private userService: UserService, 
    private drawService: DrawService,
    private betService: BetService,
    private router: Router,
    private cdr: ChangeDetectorRef // <-- 2. Injetado aqui (A Marreta)
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
        this.cdr.detectChanges(); // <-- 3. Força a tela a atualizar IMEDIATAMENTE
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
}