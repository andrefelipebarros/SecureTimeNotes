import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  private apiUrl = 'https://securetimenotes.up.railway.app/';

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('token'); // ou pegue de outro lugar, se preferir
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getNotes(): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}user/notes`, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }

  createNote(note: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}user/notes`, note, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }

  updateNote(id: string, note: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}user/notes/${id}`, note, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }

  deleteNote(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}user/notes/${id}`, { headers: this.getHeaders() })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Erro desconhecido!';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Erro: ${error.error.message}`;
    } else {
      errorMessage = `Erro do servidor: ${error.status} - ${error.message}`;
    }
    return throwError(() => new Error(errorMessage));
  }
}
