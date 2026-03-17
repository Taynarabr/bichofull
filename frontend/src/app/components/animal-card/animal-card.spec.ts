import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalCardComponent } from './animal-card';

describe('AnimalCard', () => {
  let component: AnimalCardComponent;
  let fixture: ComponentFixture<AnimalCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AnimalCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AnimalCardComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
