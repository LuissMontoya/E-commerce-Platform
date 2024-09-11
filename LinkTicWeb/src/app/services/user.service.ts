import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ResponseDto } from '../model/client.model';
import { environment } from 'src/environments/environment.development';



@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  create(email: string, password:string): Observable<ResponseDto> {
    return this.http.post<ResponseDto>(`${environment.AUTH}/login?email=${email}&password=${password}`,null);
  }


}