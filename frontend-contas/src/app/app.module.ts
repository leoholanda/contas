import { DEFAULT_CURRENCY_CODE, LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ContasListComponent } from './contas/contas-list/contas-list.component';
import { ContaFormComponent } from './contas/conta-form/conta-form.component';
import { HttpClientModule } from '@angular/common/http';

import ptBr from '@angular/common/locales/pt';
import { registerLocaleData } from '@angular/common';
registerLocaleData(ptBr);

@NgModule({
  declarations: [
    AppComponent,
    ContasListComponent,
    ContaFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'pt' },
    {provide: DEFAULT_CURRENCY_CODE, useValue: 'BRL'},
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
