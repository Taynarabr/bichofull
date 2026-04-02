import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserProfile } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  // ==========================================
  // AUTENTICAÇÃO
  // ==========================================
  login(loginData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, loginData);
  }

  register(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}`, userData);
  }

  // ==========================================
  // PERFIL
  // ==========================================
  getUserById(id: number): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/${id}`);
  }

  updateUser(id: number, userData: any): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.apiUrl}/${id}`, userData);
  }

  deposit(userId: number, amount: number): Observable<UserProfile> {
    return this.http.post<UserProfile>(`${this.apiUrl}/${userId}/deposit?amount=${amount}`, {});
  }

  withdraw(userId: number, amount: number): Observable<UserProfile> {
    return this.http.post<UserProfile>(`${this.apiUrl}/${userId}/withdraw?amount=${amount}`, {});
  }
}