import { Component } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { Store } from "@ngxs/store";
import { Register } from "src/app/states/auth.actions";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  form: FormGroup;

  constructor(private fb: FormBuilder, private store: Store, private router: Router) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  submit() {
    if (this.form.valid) {
      const {email, password } = this.form.value;
      this.store.dispatch(new Register(email, password)).subscribe({
        next: () => this.router.navigate(['/login']),
        error: (err) => console.error('Falha no cadastro', err)
      });
    }
  }
}