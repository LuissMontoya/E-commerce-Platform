import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseDto } from '../model/client.model';
import { environment } from 'src/environments/environment.development';



@Injectable({
  providedIn: 'root'
})
export class ProductsService {
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
    return this.http.get<ResponseDto>(`${environment.API_PRODUCTS}/getAll` ,{ headers: this.headers });
  }

  getById(key: string | number): Observable<ResponseDto> {
    return this.http.get<ResponseDto>(`${environment.API_PRODUCTS}/search?id=${key}`,{ headers: this.headers });
  }

  create(product: any): Observable<ResponseDto> {
    return this.http.post<ResponseDto>(`${environment.API_PRODUCTS}/create`, product,{ headers: this.headers });
  }

  edit(product: any): Observable<ResponseDto> {
    return this.http.put<ResponseDto>(`${environment.API_PRODUCTS}/update`, product,{ headers: this.headers });
  }

  delete(product: any): Observable<ResponseDto> {
    return this.http.delete<ResponseDto>(`${environment.API_PRODUCTS}/delete?id=${product.id}`,{ headers: this.headers });
  }
}