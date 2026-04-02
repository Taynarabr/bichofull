import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BetService {
  private apiUrl = 'http://localhost:8080/api/bets';

  constructor(private http: HttpClient) {}

  // Envia a aposta para o endpoint: POST /api/bets/user/{userId}
  placeBet(userId: number, betData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/user/${userId}`, betData);
  }

  // Busca as apostas do usuário para o histórico
  getUserBets(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${userId}`);
  }
}