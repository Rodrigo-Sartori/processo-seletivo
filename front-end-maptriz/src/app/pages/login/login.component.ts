import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngxs/store';
import { Login, Logout } from '../../states/auth.actions';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private store: Store, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
      this.store.dispatch(new Logout());
    }

  submit() {
    if (this.form.valid) {
      const { email, password } = this.form.value;
      this.store.dispatch(new Login(email, password)).subscribe({
        next: () => this.router.navigate(['/contacts']),
        error: (err) => console.error('Login falhou', err)
      });
    }
  }
}