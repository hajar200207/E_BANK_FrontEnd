import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8087/api/users';

    constructor(private http: HttpClient) {}

    createUser(user: User): Observable<User> {
        return this.http.post<User>(`${this.apiUrl}/user`, user);
    }

    login(user: User): Observable<string> {
        return this.http.post(`${this.apiUrl}/login`, user, { responseType: 'text' });
    }

    isLoggedIn(): boolean {
        return !!localStorage.getItem('authToken');
    }
}
