import { State, Action, StateContext, Selector } from '@ngxs/store';
import { Injectable } from '@angular/core';
import { ContactService } from '../service/contact.service';
import { EmailService } from '../service/email.service';
import { tap, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { Contact } from '../model/contact.model';

export class LoadContacts {
  static readonly type = '[Contacts] Load';
}
export class CreateContact {
  static readonly type = '[Contacts] Create';
  constructor(public payload: Contact) {}
}
export class UpdateContact {
  static readonly type = '[Contacts] Update';
  constructor(public id: number, public payload: Contact) {}
}
export class DeleteContact {
  static readonly type = '[Contacts] Delete';
  constructor(public id: number) {}
}

export interface ContactsStateModel {
  contacts: Contact[];
  errorMessage: string | null;
}

@State<ContactsStateModel>({
  name: 'contacts',
  defaults: {
    contacts: [],
    errorMessage: null
  }
})
@Injectable()
export class ContactState {
  constructor(private contactsService: ContactService, private emailService: EmailService) {}

  @Selector()
  static getContacts(state: ContactsStateModel) {
    return state.contacts;
  }

  @Selector()
  static errorMessage(state: ContactsStateModel) {
    return state.errorMessage;
  }

  @Action(LoadContacts)
  loadContacts(ctx: StateContext<ContactsStateModel>) {
    return this.contactsService.getAll().pipe(
      tap((contacts) => {
        ctx.patchState({ contacts, errorMessage: null });
      }),
      catchError((error) => {
        ctx.patchState({ errorMessage: 'Erro ao carregar contatos' });
        return throwError(error);
      })
    );
  }

  @Action(CreateContact)
  createContact(ctx: StateContext<ContactsStateModel>, action: CreateContact) {
    this.emailService.sucessEmail("Usuário registrado com sucesso")
    return this.contactsService.create(action.payload).pipe(
      tap((contact) => {
        const state = ctx.getState();
        ctx.patchState({
          contacts: [...state.contacts, contact],
          errorMessage: null
        });
      }),
      catchError((error) => {
        this.emailService.ErrorEmail("Erro ao deletar usuário")
        ctx.patchState({
          errorMessage: error.error?.message || 'Erro ao criar contato'
        });
        return throwError(error);
      })
    );
  }

  @Action(UpdateContact)
  updateContact(ctx: StateContext<ContactsStateModel>, action: UpdateContact) {
    this.emailService.sucessEmail("Usuário atualizado com sucesso")
    return this.contactsService.update(action.id, action.payload).pipe(
      tap((updated) => {
        const state = ctx.getState();
        const updatedList = state.contacts.map((c) =>
          c.id === action.id ? updated : c
        );
        ctx.patchState({ contacts: updatedList, errorMessage: null });
      }),
      catchError((error) => {
        this.emailService.ErrorEmail("Erro ao deletar usuário")
        ctx.patchState({
          errorMessage: error.error?.message || 'Erro ao atualizar contato'
        });
        return throwError(error);
      })
    );
  }

  @Action(DeleteContact)
  deleteContact(ctx: StateContext<ContactsStateModel>, action: DeleteContact) {
    this.emailService.sucessEmail("Usuário deletado com sucesso")
    return this.contactsService.delete(action.id).pipe(
      tap(() => {
        const state = ctx.getState();
        ctx.patchState({
          contacts: state.contacts.filter((c) => c.id !== action.id),
          errorMessage: null
        });
      }),
      catchError((error) => {
        this.emailService.ErrorEmail("Erro ao deletar usuário")
        ctx.patchState({
          errorMessage: error.error?.message || 'Erro ao excluir contato'
        });
        return throwError(error);
      })
    );
  }
}
