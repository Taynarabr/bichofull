import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { UserProfile } from '../../models/user.model';

interface Animal {
  name: string;
  group: string;
  icon: string;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css'],
  standalone: false
})
export class DashboardComponent implements OnInit {
  betType: string = 'group';
  betNumber: string = '';
  selectedAmount: number = 0;
  selectedAnimal: Animal | null = null;

  drawSchedule = [
    { name: 'PTM', time: '11:20', status: 'Encerrado' },
    { name: 'PT',  time: '14:20', status: 'Encerrado' },
    { name: 'PTV', time: '16:20', status: 'Encerrado' },
    { name: 'PTN', time: '18:20', status: 'Aberto' },
    { name: 'COR', time: '21:20', status: 'Aberto' }
  ];

  userProfile?: UserProfile;

  kpis = [
    { title: 'Total Bets', value: '1.284', subtitle: '↑ 12% since yesterday' },
    { title: 'Entries (R$)', value: 'R$ 12.450', subtitle: '↑ 8.5% since yesterday' },
    { title: 'Prizes Paid', value: 'R$ 8.920', subtitle: '' },
    { title: 'Net Profit', value: 'R$ 3.530', subtitle: '↑ 14.2% since yesterday' }
  ];

  animalsList: Animal[] = [
    { name: 'Ostrich', group: '01', icon: '🐦' },
    { name: 'Eagle', group: '02', icon: '🦅' },
    { name: 'Donkey', group: '03', icon: '🫏' },
    { name: 'Butterfly', group: '04', icon: '🦋' },
    { name: 'Dog', group: '05', icon: '🐶' },
    { name: 'Goat', group: '06', icon: '🐐' },
    { name: 'Ram', group: '07', icon: '🐏' },
    { name: 'Camel', group: '08', icon: '🐪' },
    { name: 'Snake', group: '09', icon: '🐍' },
    { name: 'Rabbit', group: '10', icon: '🐰' },
    { name: 'Horse', group: '11', icon: '🐎' },
    { name: 'Elephant', group: '12', icon: '🐘' },
    { name: 'Rooster', group: '13', icon: '🐓' },
    { name: 'Cat', group: '14', icon: '🐈' },
    { name: 'Alligator', group: '15', icon: '🐊' },
    { name: 'Lion', group: '16', icon: '🦁' },
    { name: 'Monkey', group: '17', icon: '🐒' },
    { name: 'Pig', group: '18', icon: '🐷' },
    { name: 'Peacock', group: '19', icon: '🦚' },
    { name: 'Turkey', group: '20', icon: '🦃' },
    { name: 'Bull', group: '21', icon: '🐂' },
    { name: 'Tiger', group: '22', icon: '🐅' },
    { name: 'Bear', group: '23', icon: '🐻' },
    { name: 'Deer', group: '24', icon: '🦌' },
    { name: 'Cow', group: '25', icon: '🐄' }
  ];

  launches = [
    { time: '14:30', animalName: 'Rabbit', group: '10', amount: 'R$ 50,00', prize: 'R$ 0,00', status: 'Lost' },
    { time: '11:00', animalName: 'Horse', group: '11', amount: 'R$ 20,00', prize: 'R$ 360,00', status: 'Won' }
  ];

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.getUserData(1); 
  }

  getUserData(id: number) {
    this.userService.getUserById(id).subscribe({
      next: (data: UserProfile) => {
        this.userProfile = data;
      },
      error: (err: any) => console.error('Error fetching user data:', err)
    });
  }

  isBetValid(): boolean {
    const hasBalance = this.userProfile ? this.selectedAmount <= this.userProfile.balance : false;
    if (this.selectedAmount <= 0 || !hasBalance) return false;

    if (this.betType === 'group') return !!this.selectedAnimal;
    if (this.betType === 'ten') return this.betNumber.length === 2;
    if (this.betType === 'thousand') return this.betNumber.length === 4;
    
    return false;
  }

  resetBetFields() {
    this.betNumber = '';
    this.selectedAnimal = null;
  }

  selectAnimal(animal: Animal) {
    if (this.betType === 'group') {
      this.selectedAnimal = animal;
    }
  }

  setAmount(amount: number) {
    this.selectedAmount = amount;
  }

  confirmBet() {
    if (this.isBetValid()) {
      const betData = {
        type: this.betType,
        value: this.selectedAmount,
        number: this.betType === 'group' ? this.selectedAnimal?.group : this.betNumber,
        animalName: this.selectedAnimal?.name
      };
      
      console.log('Enviando aposta para o Java:', betData);
      alert(`Aposta de R$ ${this.selectedAmount} confirmada!`);
    }
  }
}