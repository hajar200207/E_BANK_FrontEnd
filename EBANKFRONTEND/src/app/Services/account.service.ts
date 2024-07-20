import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AccountService {
    private apiUrl = 'http://localhost:8087/api/accounts';

    constructor(private http: HttpClient) {}

    getAccounts(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }
}
