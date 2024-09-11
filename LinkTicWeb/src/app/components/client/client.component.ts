import { Component, OnInit, Inject, inject, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MaterialModule } from '../../modules/material/material.module'
import { ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClientsService } from '../../services/clients.service';
import { Subscription } from 'rxjs';
import { MatTableDataSource } from '@angular/material/table';
import { DatePipe } from '@angular/common';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE, MatNativeDateModule, NativeDateAdapter } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MAT_MOMENT_DATE_FORMATS, MomentDateAdapter } from '@angular/material-moment-adapter';


@Component({
  selector: 'app-client',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, DatePipe, MatDatepickerModule, MatInputModule],
  templateUrl: './client.component.html',
  styleUrl: './client.component.css',
  providers: [{ provide: ClientsService },
  { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
  { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },

  ]
})

export class ClientComponent implements OnInit {
  private _clientServices: ClientsService | undefined;
  clientForm!: FormGroup;
  clientData: any;
  clients: any[] = [];

  dataSource = new MatTableDataSource();
  filteredClients = new MatTableDataSource();

  searchValue = '';


  subscription$: Subscription[] = [];

  clientServices = inject(ClientsService);


  constructor(

    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ClientComponent>,
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
        console.log("respuesta ");
        if (res != null) {
          alert(res.message);
          this.getAllClients();
        }
      })
    ];
  }


  edit(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.clientServices.edit(client).subscribe(res => {
      })
    ];
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

    this.dialogRef.close(this.clientForm.value);
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