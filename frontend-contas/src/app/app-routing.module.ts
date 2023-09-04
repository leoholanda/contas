import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContasListComponent } from './contas/contas-list/contas-list.component';
import { ContaFormComponent } from './contas/conta-form/conta-form.component';

const routes: Routes = [
  { path: '', pathMatch: 'prefix', redirectTo: 'listarContas' },
  { path: 'listarContas', component: ContasListComponent },
  { path: 'criarContas', component: ContaFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
