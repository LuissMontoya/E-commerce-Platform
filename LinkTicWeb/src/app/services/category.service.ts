import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseDto } from '../model/client.model';
import { environment } from 'src/environments/environment.development';



@Injectable({
  providedIn: 'root'
})
export class CategoryService {
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
    return this.http.get<ResponseDto>(`${environment.API_CATEGORY}/getAll` ,{ headers: this.headers });
  }

  getById(key: string | number): Observable<ResponseDto> {
    return this.http.get<ResponseDto>(`${environment.API_CATEGORY}/search?id=${key}`,{ headers: this.headers });
  }

  create(category: any): Observable<ResponseDto> {
    return this.http.post<ResponseDto>(`${environment.API_CATEGORY}/create`, category,{ headers: this.headers });
  }

  edit(category: any): Observable<ResponseDto> {
    return this.http.put<ResponseDto>(`${environment.API_CATEGORY}/update`, category,{ headers: this.headers });
  }

  delete(category: any): Observable<ResponseDto> {
    return this.http.delete<ResponseDto>(`${environment.API_CATEGORY}/delete?id=${category.id}`,{ headers: this.headers });
  }
}