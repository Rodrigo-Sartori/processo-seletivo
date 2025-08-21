import { Address } from './address.model';

export interface Contact {
  id: number;
  name: string;
  document: string;
  phoneNumber: string;
  description: string;
  createAddressDTO: Address;
}