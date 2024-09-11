import { DatePipe } from '@angular/common';
import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { ProductsService } from 'src/app/services/products.service';
import { ProductComponent } from '../product/product.component';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, DatePipe, MatDatepickerModule, MatInputModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css',
  providers: [{ provide: ProductsService },
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
    ]
})
export class OrderComponent implements OnInit {

  clientForm!: FormGroup;
  clientData: any;
  clients: any[] = [];

  dataSource = new MatTableDataSource();
  filteredClients = new MatTableDataSource();

  searchValue = '';


  subscription$: Subscription[] = [];

  clientServices = inject(ProductsService);


  constructor(

    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,

  ) {
    this.clientData = data.client;
  }


  ngOnInit(): void {
    this.isEditing = !!this.data.client.sharedKey;
    this.clientForm = this.formBuilder.group({
      id: [(this.clientData as any).id, ''],
      name: [(this.clientData as any).name, Validators.required],
      email: [(this.clientData as any).email, [Validators.required]],
      phone: [(this.clientData as any).phone, Validators.required],
      password: [(this.clientData as any).password, Validators.required],
      dateCreation: [(this.clientData as any).dateCreation, Validators.required]
    });
    this.isEditing = !!this.data.client.id;
  }

  save(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.clientServices.create(client).subscribe(res => {
          if(res.statusCode.toString() == '201'){
            alert("Registro creado");
            this.dialogRef.close(201); 
            } else {
               alert('Error al actualizar el cliente');
            }
      })
    ];
  }


  edit(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.clientServices.edit(client).subscribe(res => {
        alert(res.message);
        this.dialogRef.close(client); 
      })
    ];
  }

  refreshTable(): void {
    this.clientServices.getAll().subscribe(res => {
      this.clients = res.objectResponse; 
      this.filteredClients =res.objectResponse; 
    });
  }

  isEditing: boolean = false;

  onSubmit() {
    if (this.data.isEditing) {
      const ClientDTO = {
        id: this.clientForm.controls['id'].value,
        name: this.clientForm.controls['name'].value,
        email: this.clientForm.controls['email'].value,
        phone: this.clientForm.controls['phone'].value,
        password: this.clientForm.controls['password'].value,
        dateCreation: this.clientForm.controls['dateCreation'].value
      };
      this.edit(ClientDTO);
    }
    else {
      const Clients = {
        name: this.clientForm.controls['name'].value,
        email: this.clientForm.controls['email'].value,
        phone: this.clientForm.controls['phone'].value,
        password: this.clientForm.controls['password'].value,
        dateCreation: this.clientForm.controls['dateCreation'].value
      };
      this.save(Clients);

    }

    //this.dialogRef.close(this.clientForm.value);
  }

  clearForm() {
    this.clientForm.reset();
  }

  closeDialog() {
    this.dialogRef.close(this.clientForm.value);
  }

  getAllClients() {
    this.subscription$ = [
      ...this.subscription$,
      this.clientServices.getAll().subscribe(res => {

        this.clients = res.objectResponse;
        this.filteredClients.data = this.clients;
      })
    ];
  }
}
