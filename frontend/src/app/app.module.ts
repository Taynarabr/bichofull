import { NgModule, LOCALE_ID } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { CommonModule, registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http'; 
import localePt from '@angular/common/locales/pt';

registerLocaleData(localePt);

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app';
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { DashboardComponent } from './components/dashboard/dashboard';
import { AnimalCardComponent } from './components/animal-card/animal-card';

import { UserService } from './services/user.service';
import { DrawService } from './services/draw.service';
import { BetService } from './services/bet.service';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    AnimalCardComponent 
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,   
    CommonModule,
    HttpClientModule  
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' },
    UserService,
    DrawService,
    BetService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }