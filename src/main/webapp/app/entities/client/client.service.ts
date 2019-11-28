import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IClient } from 'app/shared/model/client.model';
import { IAddress } from 'app/shared/model/address.model';

type EntityResponseType = HttpResponse<IClient>;
type EntityArrayResponseType = HttpResponse<IClient[]>;
type EntityAddressArrayResponseType = HttpResponse<IAddress[]>;

@Injectable({ providedIn: 'root' })
export class ClientService {
  public resourceUrl = SERVER_API_URL + 'api/clients';
  public resourceUrlAddress = SERVER_API_URL + 'api/addresses';

  constructor(protected http: HttpClient) {}

  create(client: IClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(client);
    return this.http
      .post<IClient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(client: IClient): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(client);
    return this.http
      .put<IClient>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IClient>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IClient[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  findAddressesByClientId(id: number): Observable<EntityAddressArrayResponseType> {
    return this.http
      .get<IAddress[]>(`${this.resourceUrlAddress}/${id}/addresses`, { observe: 'response' })
      .pipe(map((res: EntityAddressArrayResponseType) => this.convertResponseAddress(res)));
  }

  protected convertDateFromClient(client: IClient): IClient {
    const copy: IClient = Object.assign({}, client, {
      createdAt: client.createdAt != null && client.createdAt.isValid() ? client.createdAt.toJSON() : null,
      updatedAt: client.updatedAt != null && client.updatedAt.isValid() ? client.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((client: IClient) => {
        client.createdAt = client.createdAt != null ? moment(client.createdAt) : null;
        client.updatedAt = client.updatedAt != null ? moment(client.updatedAt) : null;
      });
    }
    return res;
  }

  protected convertResponseAddress(res: EntityAddressArrayResponseType): EntityAddressArrayResponseType {
    const body: IAddress[] = res.body;
    return res.clone({ body });
  }
}
