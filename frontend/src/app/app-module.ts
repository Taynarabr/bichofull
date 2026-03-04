import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // Resolve o erro do ngModel
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing-module';
import { AppComponent } from './app'; // Se seu arquivo principal for app.ts
import { LoginComponent } from './components/login/login';
import { RegisterComponent } from './components/register/register';

@NgModule({
  declarations: [
    AppComponent,    // Use o nome completo da classe definida no app.ts
    LoginComponent,  // Use o nome completo definido no login.ts
    RegisterComponent // Use o nome completo definido no register.ts
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,       // Habilita o uso do [(ngModel)] nos formulários
    HttpClientModule   // Habilita a comunicação com o backend Java
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }