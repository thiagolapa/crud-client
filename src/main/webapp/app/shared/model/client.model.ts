import { Moment } from 'moment';
import { IAddress } from 'app/shared/model/address.model';
import { Risk } from 'app/shared/model/enumerations/risk.model';

export interface IClient {
  id?: number;
  name?: string;
  monthlyIncome?: number;
  risk?: Risk;
  createdAt?: Moment;
  updatedAt?: Moment;
  addresses?: IAddress[];
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public name?: string,
    public monthlyIncome?: number,
    public risk?: Risk,
    public createdAt?: Moment,
    public updatedAt?: Moment,
    public addresses?: IAddress[]
  ) {}
}
