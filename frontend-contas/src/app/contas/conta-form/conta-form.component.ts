import { Component, OnInit } from '@angular/core';
import { DatePipe, Location } from '@angular/common';
import { FormBuilder, NonNullableFormBuilder, Validators } from '@angular/forms';
import { ContasService } from '../services/contas.service';
import { ActivatedRoute } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-conta-form',
  templateUrl: './conta-form.component.html',
  styleUrls: ['./conta-form.component.scss']
})
export class ContaFormComponent implements OnInit {

  form = this.formBuilder.group({
    id: [''],
    nome: ['', [Validators.required, Validators.minLength(3)]],
    status: ['', [Validators.required]],
    valorOriginal: [, [Validators.required, Validators.min(0.1)]],
    dataVencimento: ['', [Validators.required]]
  });

  constructor(
    private formBuilder: NonNullableFormBuilder,
    private service: ContasService,
    private location: Location,
    private route: ActivatedRoute,
    private snackbar: MatSnackBar) {}

    ngOnInit(): void {
      
    }


    onSubmit() {
      this.service.save(this.form.value)
      .subscribe({
        next: (res: any) => {
          this.onSuccess()
          console.log(res)
          this.form.reset(this.form);
        },
        error: (err: any) => {
          this.onError()
          console.log(this.form.value)
          console.log(err)
        }
      });
    }

    onCancel() {
      this.location.back();
    }

    onSuccess() {
      this.snackbar.open('Conta adicionada com sucesso', '', {
        duration: 5000,
        verticalPosition: 'top',
        panelClass: ['success-snackbar']
      });
    }

    onError() {
      this.snackbar.open('Erro ao salvar conta', '', {
        duration: 5000,
        verticalPosition: 'top',
        panelClass: ['error-snackbar']
      });
    }

    getErrorMessage(fieldName: string) {
      const field = this.form.get(fieldName);

      if(field?.hasError('required')) {
        return 'Campo obrigatório'
      }

      if(field?.hasError('min')) {
        return 'Valor não pode ser zero'
      }

      if(field?.hasError('minlength')){
        const requiredLength = field.errors ? field.errors['minlength']['requiredLength'] : 3
        return `Tamanho mínimo precisa ser de ${requiredLength} caracteres`
      }

      return 'Campo inválido';
    }

}
