import { Component, OnInit } from '@angular/core';
import { Conta } from '../model/conta';
import { ContasService } from '../services/contas.service';
import { catchError } from 'rxjs';

import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-contas-list',
  templateUrl: './contas-list.component.html',
  styleUrls: ['./contas-list.component.scss']
})
export class ContasListComponent implements OnInit {

  contas: Conta[] = [];
  readonly displayedColumns = ['nome', 'status', 'valorOriginal', 
  'valorCorrigido', 'atrasado', 'dataVencimento', 'dataPagamento', 'actions']

  constructor(
    private contasService: ContasService,
    private router: Router,
    private route: ActivatedRoute) {}


  ngOnInit(): void {
    this.listarContas();
  }

  listarContas() {
    this.contasService.listContas().subscribe((contas) => {
      this.contas = contas;
    });
  }

  onAdicionar() {
    this.router.navigate(['criarContas']);
  }

  onPagarConta(conta: Conta) {
    this.contasService.pay(conta).subscribe({
      next: () => {
        this.contasService.listContas().subscribe((contas) => {
          this.contas = contas;
        });
      } 
    });
  }

}
