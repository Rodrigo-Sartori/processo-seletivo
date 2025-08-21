import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Store, Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import {
  CreateContact,
  UpdateContact
} from '../../states/contact.state';
import { ContactState } from '../../states/contact.state';
import { Contact } from 'src/app/model/contact.model';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {
  form!: FormGroup;
  contactId?: number;
  loading = false;

  @Select(ContactState.errorMessage) errorMessage$!: Observable<string>;

  constructor(
    private fb: FormBuilder,
    private store: Store,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.contactId = this.route.snapshot.params['id'];

    this.form = this.fb.group({
      name: ['', Validators.required],
      document: ['', Validators.required],
      phoneNumber: [''],
      description: [''],
      createAddressDTO: this.fb.group({
        cep: [''],
        street: [''],
        number: [null],
        complement: [''],
        district: ['']
      })
    });

    if (this.contactId) {
    this.store
      .selectOnce(ContactState.getContacts)
      .subscribe((contacts) => {
        const existing = contacts.find((c) => c.id === +this.contactId!);
        if (existing) {
          this.form.patchValue(existing);
        }
      });
  }
  }

  onSubmit() {
    if (this.form.invalid) return;
    this.loading = true;

    const contact: Contact = this.form.value;
    if (this.contactId) {
      this.store.dispatch(new UpdateContact(this.contactId, contact)).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/contacts']);
        },
        error: () => {
          this.loading = false
        }
      });
    } else {
      this.store.dispatch(new CreateContact(contact)).subscribe({
        next: () => {
          this.loading = false;
          this.router.navigate(['/contacts']);
        },
        error: () => (this.loading = false)
      });
    }
  }
}
