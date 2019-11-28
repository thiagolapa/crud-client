import { Component, OnInit, ViewChild } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IClient, Client } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { IAddress, Address } from 'app/shared/model/address.model';
import { AddressService } from 'app/entities/address/address.service';
import { Risk } from 'app/shared/model/enumerations/risk.model';

@Component({
  selector: 'jhi-client-update',
  templateUrl: './client-update.component.html',
  styleUrls: ['client.scss']
})
export class ClientUpdateComponent implements OnInit {
  isSaving: boolean;
  client: Client = new Client();
  addresses: IAddress[];
  address: Address = new Address();
  loadingNewAddress: boolean;

  @ViewChild('editForm', { static: false }) public editForm: NgForm;

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected clientService: ClientService,
    protected addressService: AddressService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      this.loadingNewAddress = false;
      if (client.id !== undefined) {
        this.client.addresses = client.addresses;
        this.address = this.client.addresses[0];
      } else {
        this.client.addresses = new Array<Address>();
        this.address = new Address();
      }
    });
    this.addressService
      .query()
      .subscribe((res: HttpResponse<IAddress[]>) => (this.addresses = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.client.id !== undefined) {
      this.subscribeToSaveResponse(this.clientService.update(this.client));
    } else {
      this.subscribeToSaveResponse(this.clientService.create(this.client));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClient>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackAddressById(index: number, item: IAddress) {
    return item.id;
  }

  formIsInvalid(editForm): boolean {
    if (!editForm) {
      return false;
    }

    return (
      editForm.form.invalid ||
      this.client.addresses.length === 0
    );
  }

  verifyAddress() {
    return !this.client.addresses.some(address => address.typeAddress === this.address.typeAddress);
  }

  newAddress() {
    this.address = new Address();
    this.loadingNewAddress = true;
  }

  removeAddress(streetAddress: string) {
    this.client.addresses = this.client.addresses.filter(address => address.streetAddress !== streetAddress);
  }

  addAddress() {
    if (
      this.address.streetAddress !== undefined &&
      this.address.streetAddress !== '' &&
      this.address.postalCode !== undefined &&
      this.address.postalCode !== '' &&
      this.address.typeAddress !== undefined &&
      this.address.district !== undefined &&
      this.address.district !== ''
    ) {
      this.client.addresses.push(this.address);
      this.loadingNewAddress = false;
    }
  }

  updateRisk() {
    if ( this.client.monthlyIncome <= 2000 ) {
      this.client.risk = Risk.C;
    } else if ( this.client.monthlyIncome > 2000 && this.client.monthlyIncome <= 8000 ) {
      this.client.risk = Risk.B;
    } else if ( this.client.monthlyIncome > 8000 ) {
      this.client.risk = Risk.A;
    }
  }


}
