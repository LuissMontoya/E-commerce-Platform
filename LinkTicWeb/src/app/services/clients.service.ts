import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseDto } from '../model/client.model';
import { environment } from 'src/environments/environment.development';



@Injectable({
  providedIn: 'root'
})
export class ClientsService {

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  
  getAll(): Observable<ResponseDto> {
    const headers = this.getAuthHeaders();
    return this.http.get<ResponseDto>(`${environment.API_URL}/getAll` ,{headers});
  }

  getById(key: string): Observable<ResponseDto> {
    const headers = this.getAuthHeaders();
    return this.http.get<ResponseDto>(`${environment.API_URL}/search?id=${key}`,{headers});
  }

  create(client: any): Observable<ResponseDto> {
    const headers = this.getAuthHeaders();
    return this.http.post<ResponseDto>(`${environment.API_URL}/create`, client,{headers});
  }

  edit(client: any): Observable<ResponseDto> {
    const headers = this.getAuthHeaders();
    return this.http.post<ResponseDto>(`${environment.API_URL}/update`, client,{headers});
  }

  delete(key: number): Observable<ResponseDto> {
    const headers = this.getAuthHeaders();
    return this.http.delete<ResponseDto>(`${environment.API_URL}/delete?id=${key}`,{headers});
  }
}