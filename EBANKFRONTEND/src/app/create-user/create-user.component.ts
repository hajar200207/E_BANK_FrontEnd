import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { User } from '../models/user.model';

@Component({
  selector: 'app-create-user',
  templateUrl: './create-user.component.html',
  styleUrls: ['./create-user.component.css']
})
export class CreateUserComponent {
  username: string = '';
  email: string = '';
  password: string = '';

  constructor(private userService: UserService, private router: Router) {}

  createUser() {
    const user: User = { username: this.username, email: this.email, password: this.password };
    this.userService.createUser(user).subscribe(
        response => {
          console.log('User created successfully', response);
          this.router.navigate(['/dashboard']);
        },
        error => {
          console.error('User creation failed', error);
        }
    );
  }
}
