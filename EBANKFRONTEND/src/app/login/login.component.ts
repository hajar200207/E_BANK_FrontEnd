import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../models/user.model';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent {
    user: User = { username: '', email: '', password: '' };

    constructor(private userService: UserService, private router: Router) {}

    login() {
        this.userService.login(this.user).subscribe(
            token => {
                localStorage.setItem('authToken', token);
                console.log('User login successfully', token);
                this.router.navigate(['/dashboard']);
            },
            error => {
                console.error('Error logging in', error);
                alert('Login failed');
            }
        );
    }
}
