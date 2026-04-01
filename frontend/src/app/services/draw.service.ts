import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Draw } from '../models/draw.model';

@Injectable({ providedIn: 'root' })
export class DrawService {
  private apiUrl = 'http://localhost:8080/api/draws';

  constructor(private http: HttpClient) {}

  findAll(): Observable<Draw[]> {
    return this.http.get<Draw[]>(this.apiUrl);
  }

  generate(): Observable<Draw> {
    return this.http.post<Draw>(`${this.apiUrl}/generate`, {});
  }
}