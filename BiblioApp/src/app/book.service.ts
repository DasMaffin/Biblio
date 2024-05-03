import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Book } from './book';

@Injectable({
  providedIn: 'root'
})
export class BookService {
    private apiServerUrl = 'http://193.34.77.221:8080';

    constructor(private http: HttpClient){}

    public getBooks(): Observable<Book[]>{
        return this.http.get<Book[]>(`${this.apiServerUrl}/book/all`);
    }

    public addBook(book: Book): Observable<Book>{
        return this.http.post<Book>(`${this.apiServerUrl}/book/add`, book).pipe(catchError(this.handleError));
    }

    public updateBook(book: Book): Observable<Book>{
        return this.http.put<Book>(`${this.apiServerUrl}/book/update`, book);
    }

    public deleteBook(ISBN: string): Observable<void>{
        return this.http.delete<void>(`${this.apiServerUrl}/book/delete/${ISBN}`);
    }

    private handleError(error: HttpErrorResponse) {
        if (error.error instanceof ErrorEvent) {
          // A client-side or network error occurred. Handle it accordingly.
          console.error('An error occurred:', error.error.message);
        } else {
          // The backend returned an unsuccessful response code.
          // The response body may contain clues as to what went wrong.
          console.error(
            `Backend returned code ${error.status}, ` +
            `body was: ${error.error.message}`);
        }
        // Return an observable with a user-facing error message.
        return throwError(() => new Error('Confirm ISBN to be correct and not already listed. '));
      }
}
