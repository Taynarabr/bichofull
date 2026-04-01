import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-animal-card',
  standalone: false,
  templateUrl: './animal-card.html'
})
export class AnimalCardComponent {
  @Input() animalData: any;
  @Input() isSelected: boolean = false;
}