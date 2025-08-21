import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { EmailModel } from '../model/email.model';

@Injectable({ providedIn: 'root' })
export class EmailService {
  private apiUrl = 'http://localhost:8080/api/email';

  constructor(private http: HttpClient) {}

  sucessEmail(messageContent: string): Observable<string> {
    const email = {message: messageContent, isError: false}
    console.log(email)
    return this.http.post<string>(this.apiUrl, email).pipe(
      catchError((error) => throwError(error))
    );
  }
  ErrorEmail(messageContent: string): Observable<string> {
    const email = {message: messageContent, isError: true}
    return this.http.post<string>(this.apiUrl, email).pipe(
      catchError((error) => throwError(error))
    );
  }

}
