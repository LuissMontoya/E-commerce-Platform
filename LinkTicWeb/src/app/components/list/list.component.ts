import { Component, ChangeDetectorRef, inject } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MaterialModule } from '../../modules/material/material.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatDialog } from '@angular/material/dialog';
import { ClientComponent } from '../client/client.component';
import { HttpClientModule } from '@angular/common/http';
import { ClientsService } from '../../services/clients.service';
import { Subscription } from 'rxjs';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-list',
  standalone: true,
  imports: [MaterialModule, MatToolbarModule,ClientComponent,HttpClientModule],
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
  providers: [ClientsService]
})
export class ListComponent {
  displayedColumns: string[] = [
    'id',
    'name',
    'email',
    'phone',
    'dateCreation',
    'actions',
  ];
  clients: any[] = [];

  dataSource = new MatTableDataSource();
  filteredClients = new MatTableDataSource();
  searchValue= '';

  subscription$: Subscription[] = [];

  clientServices=inject(ClientsService);
  authServices=inject(UserService);

  isEditing: boolean = false;


  constructor(private cdRef: ChangeDetectorRef, public dialog: MatDialog ) {}

  ngOnInit(): void {
    this.getAllClients();
 
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

  getClientePorClave() {
    this.subscription$ = [
      ...this.subscription$,
      this.clientServices.getById(this.searchValue).subscribe(res => {

        this.clients = res.objectResponse;
        this.filteredClients.data = this.clients;
      })
    ];
  }

  save(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.clientServices.create(client).subscribe(res => {
        console.log(res);
      })
    ];
  }

  editClient(client: any) {
    this.isEditing = true;
    const dialogRef = this.dialog.open(ClientComponent, {
      data: { client: client, isEditing: this.isEditing }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(result);
      this.getAllClients();
    });
    
  }


  openClientForm() {
    const dialogRef = this.dialog.open(ClientComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
    });
  }

  addNewClient() {
    this.isEditing = false;
    const dialogRef = this.dialog.open(ClientComponent, {
      data: { client: { id: 0, name: '', email: '', phone: '', password: '', dateCreation: '' } }
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.getAllClients();
    });

   
  }
  exportClients() {
    const dataToExport = this.clients.map((client) => ({
      id: client.id,
      name: client.name,
      email: client.email,
      phone: client.phone,
      dateCreation: client.dateCreation,
    }));

    const csvData = this.convertToCSV(dataToExport);
    const blob = new Blob([csvData], { type: 'text/csv' });

    const a = document.createElement('a');
    a.href = window.URL.createObjectURL(blob);
    a.download = 'users.csv';
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }

  convertToCSV(data: any[]): string {
    const header = Object.keys(data[0]).join(',');
    const csv = data.map((row) => Object.values(row).join(',')).join('\n');
    return `${header}\n${csv}`;
  }

  ngOnDestroy(): void {
    if (this.subscription$) {
      this.subscription$.forEach(sub => {
        sub.unsubscribe();
      });
    }
  }



}


