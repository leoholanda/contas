import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Conta } from '../model/conta';
import { Observable, delay, first } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContasService {

  private readonly API = 'http://localhost:8080/contas';
  
  constructor(private httpClient: HttpClient){}

  listContas(): Observable<Conta[]> {
    return this.httpClient.get<Conta[]>(this.API);
  }

  save(conta: Partial<Conta>) {
    return this.create(conta);
  }

  pay(conta: Conta) {
    return this.httpClient.put(`${this.API}/pago/${conta.id}`, conta).pipe(first());
  }

  private create(conta: Partial<Conta>) {
    return this.httpClient.post<Conta>(this.API, conta).pipe(first());
  }

  private update(conta: Partial<Conta>) {
    return this.httpClient.put(`${this.API}/${conta.id}`, conta).pipe(first());
  }
}
