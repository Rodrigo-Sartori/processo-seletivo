import { Component, OnInit } from '@angular/core';
import { Store, Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { ContactState, LoadContacts, DeleteContact } from '../../states/contact.state';
import { Router } from '@angular/router';
import { Contact } from 'src/app/model/contact.model';

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html'
})
export class ContactsComponent implements OnInit {
  @Select(ContactState.getContacts) contacts$!: Observable<any[]>;

  displayedColumns: string[] = ['name', 'email', 'phone', 'actions'];

  constructor(private store: Store, private router: Router) {}

  ngOnInit() {
    this.store.dispatch(new LoadContacts());
  }

  editContact(contact: Contact) {
    this.router.navigate(['/contacts', contact.id]);
  }

  deleteContact(contact: Contact) {
    if (confirm(`Tem certeza que deseja excluir ${contact.name}?`)) {
      this.store.dispatch(new DeleteContact(contact.id));
    }
  }

  addContact() {
    this.router.navigate(['/contacts/new']);
  }
}