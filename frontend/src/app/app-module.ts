import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // Resolve o erro do ngModel
import { CommonModule } from '@angular/common'; // Resolve o erro do uppercase e ngClass

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { DashboardComponent } from './components/dashboard/dashboard';
import { AnimalCardComponent } from './components/animal-card/animal-card'; // Importe correto

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    AnimalCardComponent // Nome da classe que está dentro do arquivo .ts
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,   // Essencial para o login/registro funcionar
    CommonModule   // Essencial para pipes e diretivas
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }