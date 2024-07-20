import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class TransferService {
    private apiUrl = 'http://localhost:8087/api/transfers';

    constructor(private http: HttpClient) {}

    getTransfers(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }
}
