import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { ProductsService } from 'src/app/services/products.service';
import { UserService } from 'src/app/services/user.service';
import { ProductComponent } from '../product/product.component';
import { HttpClientModule } from '@angular/common/http';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MaterialModule } from 'src/app/modules/material/material.module';

@Component({
  selector: 'app-list-product',
  standalone: true,
  imports: [MaterialModule, MatToolbarModule,ProductComponent,HttpClientModule],
  templateUrl: './list-product.component.html',
  styleUrl: './list-product.component.css',
  providers: [ProductsService]
})
export class ListProductComponent {
  displayedColumns: string[] = [
    'id',
    'name',
    'price',
    'stock',
    'category',
    'actions',
  ];
  products: any[] = [];

  dataSource = new MatTableDataSource();
  filteredproducts = new MatTableDataSource();
  searchValue= '';

  subscription$: Subscription[] = [];

  productservices=inject(ProductsService);
  authServices=inject(UserService);

  isEditing: boolean = false;


  constructor(private cdRef: ChangeDetectorRef, public dialog: MatDialog ) {}

  ngOnInit(): void {
    this.getAllproducts();
 
  }


  getAllproducts() {
    this.subscription$ = [
      ...this.subscription$,
      this.productservices.getAll().subscribe(res => {

        this.products = res.objectResponse;
        this.filteredproducts.data = res.objectResponse;
      })
    ];
  }

  getProductPorClave() {
    console.log(this.searchValue);
    if(this.searchValue==""){
      this.getAllproducts();
    }else{
      this.subscription$ = [
        ...this.subscription$,
        this.productservices.getById(this.searchValue).subscribe(res => {
  
          this.products = res.objectResponse;
          this.filteredproducts.data = this.products;
        })
      ];
    }
    
  }

  save(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.productservices.create(client).subscribe(res => {
        console.log(res);
        this.refreshTable();
      })
    ];
  }

  deleteClient(client:any){
    console.log(client);
    const confirmed = window.confirm('¿Está seguro de que desea eliminar este usuario?');
    if (confirmed) {
      this.subscription$ = [
        ...this.subscription$,
        this.productservices.delete(client).subscribe(res => {
         alert(res.objectResponse);
         this.refreshTable();
        })
      ];
    }
  }

  refreshTable(): void {
    this.filteredproducts = new MatTableDataSource<any>();
    this.productservices.getAll().subscribe(res => {
      this.products = res.objectResponse; 
      this.filteredproducts =res.objectResponse; 
    });
  }

  editClient(client: any) {
    this.isEditing = true;
    const dialogRef = this.dialog.open(ProductComponent, {
      data: { client: client, isEditing: this.isEditing }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log("resultado",result);
      this.refreshTable();
    });
    
  }


  openClientForm() {
    const dialogRef = this.dialog.open(ProductComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.refreshTable();
    });
  }

  addNewClient() {
    this.isEditing = false;
    const dialogRef = this.dialog.open(ProductComponent, {
      data: { client: { id: 0, name: '', email: '', phone: '', password: '', dateCreation: '' } }
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      console.log("resultado",result);
      if (result ==201) { 
        this.refreshTable();
      }
    });

   
  }
  exportProducts() {
    const dataToExport = this.products.map((client) => ({
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
