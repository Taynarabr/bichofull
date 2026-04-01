import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserProfile } from '../../models/user.model';
import { DrawService } from '../../services/draw.service';
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
  isLoading = true;

  betType: string = 'group';
  selectedAmount: number = 0;
  selectedAnimal: Animal | null = null;
  
  animalsList: Animal[] = [
    { name: 'Ostrich', group: '01', icon: '🐦' }, { name: 'Eagle', group: '02', icon: '🦅' },
    { name: 'Donkey', group: '03', icon: '🫏' }, { name: 'Butterfly', group: '04', icon: '🦋' },
    { name: 'Dog', group: '05', icon: '🐶' }, { name: 'Goat', group: '06', icon: '🐐' },
    { name: 'Ram', group: '07', icon: '🐏' }, { name: 'Camel', group: '08', icon: '🐪' },
    { name: 'Snake', group: '09', icon: '🐍' }, { name: 'Rabbit', group: '10', icon: '🐰' },
    { name: 'Horse', group: '11', icon: '🐎' }, { name: 'Elephant', group: '12', icon: '🐘' },
    { name: 'Rooster', group: '13', icon: '🐓' }, { name: 'Cat', group: '14', icon: '🐈' },
    { name: 'Alligator', group: '15', icon: '🐊' }, { name: 'Lion', group: '16', icon: '🦁' },
    { name: 'Monkey', group: '17', icon: '🐒' }, { name: 'Pig', group: '18', icon: '🐷' },
    { name: 'Peacock', group: '19', icon: '🦚' }, { name: 'Turkey', group: '20', icon: '🦃' },
    { name: 'Bull', group: '21', icon: '🐂' }, { name: 'Tiger', group: '22', icon: '🐅' },
    { name: 'Bear', group: '23', icon: '🐻' }, { name: 'Deer', group: '24', icon: '🦌' },
    { name: 'Cow', group: '25', icon: '🐄' }
  ];

  constructor(private userService: UserService, private drawService: DrawService, private router: Router) {}

  ngOnInit() {
    console.log('🚀 DASHBOARD: 1. A iniciar Dashboard...');
    const idLogado = localStorage.getItem('userId');
    console.log('🚀 DASHBOARD: 2. ID encontrado no cache:', idLogado);

    if (idLogado && idLogado !== 'undefined' && idLogado !== 'null') {
      console.log('🚀 DASHBOARD: 3. A pedir dados ao Java...');
      this.getUserData(Number(idLogado));
      this.listarSorteios();
    } else {
      console.error('🚀 DASHBOARD: ERRO - Sem ID. Voltando ao login.');
      this.router.navigate(['/login']);
    }
  }

  getUserData(id: number) {
    this.isLoading = true;
    this.userService.getUserById(id).subscribe({
      next: (data) => {
        console.log('✅ DASHBOARD: 4. Dados recebidos com SUCESSO:', data);
        this.userProfile = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('❌ DASHBOARD: 4. ERRO FATAL ao buscar dados no Java:', err);
        this.isLoading = false;
      }
    });
  }

  listarSorteios() {
    this.drawService.findAll().subscribe({
      next: (data) => {
        console.log('✅ DASHBOARD: 5. Sorteios recebidos com SUCESSO', data);
        this.draws = data;
      },
      error: (err) => console.error('❌ DASHBOARD: 5. ERRO ao buscar sorteios', err)
    });
  }

  gerarSorteio() {
    this.drawService.generate().subscribe(() => this.listarSorteios());
  }

  isBetValid(): boolean { return this.selectedAmount > 0; }
  selectAnimal(a: Animal) { if (this.betType === 'group') this.selectedAnimal = a; }
  confirmBet() { alert('Aposta confirmada!'); }
}