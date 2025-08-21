import { State, Action, StateContext, Selector } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { Login, Logout, Register } from './auth.actions';
import { tap } from 'rxjs/operators';

export interface AuthStateModel {
  token: string | null;
}

@State<AuthStateModel>({
  name: 'auth',
  defaults: {
    token: localStorage.getItem('token')
  }
})
@Injectable()
export class AuthState {
  constructor(private authService: AuthService) {}

  @Selector()
  static token(state: AuthStateModel): string | null {
    return state.token;
  }

  @Selector()
  static isAuthenticated(state: AuthStateModel): boolean {
    return !!state.token;
  }

  @Action(Login)
  login(ctx: StateContext<AuthStateModel>, action: Login) {
    return this.authService.login(action.email, action.password).pipe(
      tap((result) => {
        localStorage.setItem('token', result.token);
        ctx.patchState({ token: result.token });
      })
    );
  }

  @Action(Logout)
  logout(ctx: StateContext<AuthStateModel>) {
    localStorage.removeItem('token');
    ctx.setState({ token: null });
  }

  @Action(Register)
  register(ctx: StateContext<AuthStateModel>, action: Register) {
    return this.authService.register(action.email, action.password).pipe(
      tap(() => {
        console.log('Usuário registrado com sucesso');
    })
  );
}
}