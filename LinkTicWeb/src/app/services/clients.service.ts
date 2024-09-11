import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseDto } from '../model/client.model';
import { environment } from 'src/environments/environment.development';



@Injectable({
  providedIn: 'root'
})
export class ClientsService {
  private headers: HttpHeaders;

  constructor(private http: HttpClient) {
    this.headers = this.getAuthHeaders();
  }

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  
  getAll(): Observable<ResponseDto> {
    return this.http.get<ResponseDto>(`${environment.API_URL}/getAll` ,{ headers: this.headers });
  }

  getById(key: string | number): Observable<ResponseDto> {
    return this.http.get<ResponseDto>(`${environment.API_URL}/search?id=${key}`,{ headers: this.headers });
  }

  create(client: any): Observable<ResponseDto> {
    return this.http.post<ResponseDto>(`${environment.API_URL}/create`, client,{ headers: this.headers });
  }

  edit(client: any): Observable<ResponseDto> {
    return this.http.put<ResponseDto>(`${environment.API_URL}/update`, client,{ headers: this.headers });
  }

  delete(client: any): Observable<ResponseDto> {
    return this.http.delete<ResponseDto>(`${environment.API_URL}/delete?id=${client.id}`,{ headers: this.headers });
  }
}