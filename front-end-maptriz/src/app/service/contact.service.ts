import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Contact } from '../model/contact.model';

@Injectable({ providedIn: 'root' })
export class ContactService {
  private apiUrl = 'http://localhost:8080/api/contact';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Contact[]> {
    return this.http.get<Contact[]>(this.apiUrl).pipe(
      catchError((error) => throwError(error))
    );
  }

  getById(id: number): Observable<Contact> {
    return this.http.get<Contact>(`${this.apiUrl}/${id}`).pipe(
      catchError((error) => throwError(error))
    );
  }

  create(contact: Contact): Observable<Contact> {
    return this.http.post<Contact>(this.apiUrl, contact).pipe(
      catchError((error) => throwError(error))
    );
  }

  update(id: number, contact: Contact): Observable<Contact> {
    return this.http.put<Contact>(`${this.apiUrl}/${id}`, contact).pipe(
      catchError((error) => throwError(error))
    );
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`).pipe(
      catchError((error) => throwError(error))
    );
  }
}
