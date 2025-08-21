import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './guard/auth.guard';
import { ContactFormComponent } from './pages/contact-form/contact-form.component';
import { LoginComponent } from './pages/login/login.component';
import { ContactsComponent } from './pages/contacts/contacts.component';
import { RegisterComponent } from './pages/register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {path: 'register', component: RegisterComponent},
  {
    path: 'contacts',
    canActivate: [AuthGuard],
    children: [
      { path: '', component: ContactsComponent },
      { path: 'new', component: ContactFormComponent },
      { path: ':id', component: ContactFormComponent }
    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
