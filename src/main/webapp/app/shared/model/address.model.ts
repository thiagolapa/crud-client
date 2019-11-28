import { Moment } from 'moment';
import { TypeAddress } from 'app/shared/model/enumerations/type-address.model';

export interface IAddress {
  id?: number;
  streetAddress?: string;
  postalCode?: string;
  typeAddress?: TypeAddress;
  number?: number;
  complement?: string;
  district?: string;
  createdAt?: Moment;
  updatedAt?: Moment;
  clientId?: number;
}

export class Address implements IAddress {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public postalCode?: string,
    public typeAddress?: TypeAddress,
    public number?: number,
    public complement?: string,
    public district?: string,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public clientId?: number
  ) {}
}
