import { Component } from '@angular/core';

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
export class DashboardComponent {
  // KPIs com nomes em inglês para bater com o HTML
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

  selectedAnimal: Animal | null = null;
  selectedAmount: number = 0;

  selectAnimal(animal: Animal) {
    this.selectedAnimal = animal;
  }

  setAmount(amount: number) {
    this.selectedAmount = amount;
  }
}