import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IAddress } from 'app/shared/model/address.model';
import { IClient } from 'app/shared/model/client.model';
import { ClientService } from './client.service';
import { HttpResponse } from '@angular/common/http';

@Component({
  selector: 'jhi-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['client.scss']
})
export class ClientDetailComponent implements OnInit {
  client: IClient;
  addresses: IAddress[];

  constructor(protected activatedRoute: ActivatedRoute, protected clientService: ClientService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ client }) => {
      this.client = client;
      this.loadAddresses(this.client.id);
    });
  }

  loadAddresses(id) {
    this.clientService.findAddressesByClientId(id).subscribe((addressResponse: HttpResponse<IAddress[]>) => {
      this.addresses = addressResponse.body;
    });
  }

  previousState() {
    window.history.back();
  }

  trackId(index: number, item: IAddress) {
      return item.id;
  }
}
