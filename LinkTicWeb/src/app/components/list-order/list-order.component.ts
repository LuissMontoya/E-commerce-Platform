import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { OrderComponent } from '../order/order.component';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { MatToolbarModule } from '@angular/material/toolbar';
import { HttpClientModule } from '@angular/common/http';
import { OrdersService } from 'src/app/services/orders.service';
import { UserService } from 'src/app/services/user.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-list-order',
  standalone: true,
  imports: [MaterialModule, MatToolbarModule,CommonModule,HttpClientModule,OrderComponent,HttpClientModule],
  templateUrl: './list-order.component.html',
  styleUrl: './list-order.component.css'
})
export class ListOrderComponent {
  displayedColumns: string[] = [
    'id',
    'date',
    'customerName',
    'totalAmount',
    'products',
    'actions',
  ];
  orders: any[] = [];

  dataSource = new MatTableDataSource();
  filteredOrders = new MatTableDataSource();
  searchValue= '';

  subscription$: Subscription[] = [];

  ordersServices=inject(OrdersService);
  authServices=inject(UserService);

  isEditing: boolean = false;


  constructor(private cdRef: ChangeDetectorRef, public dialog: MatDialog ) {}

  ngOnInit(): void {
    this.getAllOrders();
 
  }


  getAllOrders() {
    this.subscription$ = [
      ...this.subscription$,
      this.ordersServices.getAll().subscribe(res => {
        console.log(res.objectResponse[0]);
        this.orders = res.objectResponse;
        this.filteredOrders.data = res.objectResponse;
      })
    ];
  }

  getProductPorClave() {
    console.log(this.searchValue);
    if(this.searchValue==""){
      this.getAllOrders();
    }else{
      this.subscription$ = [
        ...this.subscription$,
        this.ordersServices.getById(this.searchValue).subscribe(res => {
  
          this.orders = res.objectResponse;
          this.filteredOrders.data = this.orders;
        })
      ];
    }
    
  }

  save(order: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.ordersServices.create(order).subscribe(res => {
        console.log(res);
        this.refreshTable();
      })
    ];
  }

  deleteClient(client:any){
    console.log(client);
    const confirmed = window.confirm('¿Está seguro de que desea eliminar esta orden?');
    if (confirmed) {
      this.subscription$ = [
        ...this.subscription$,
        this.ordersServices.delete(client).subscribe(res => {
         alert(res.objectResponse);
         this.refreshTable();
        })
      ];
    }
  }

  refreshTable(): void {
    this.filteredOrders = new MatTableDataSource<any>();
    this.ordersServices.getAll().subscribe(res => {
      this.orders = res.objectResponse; 
      this.filteredOrders =res.objectResponse; 
    });
  }

  editClient(order: any) {
    this.isEditing = true;
    const dialogRef = this.dialog.open(OrderComponent, {
      data: { order: order, isEditing: this.isEditing }
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log("resultado",result);
      this.refreshTable();
    });
    
  }


  openOrderForm() {
    const dialogRef = this.dialog.open(OrderComponent);

    dialogRef.afterClosed().subscribe((result) => {
      console.log('The dialog was closed');
      this.refreshTable();
    });
  }

  addNewOrder() {
    this.isEditing = false;
    const dialogRef = this.dialog.open(OrderComponent, {
      data: { order: { id: 0, date: '', customerName: '', totalAmount: '', products: '' } }
    });
  
    dialogRef.afterClosed().subscribe((result) => {
      console.log("resultado",result);
      if (result ==201) { 
        this.refreshTable();
      }
    });

   
  }
  exportOrders() {
    const dataToExport = this.orders.map((order) => ({
      id: order.id,
      date: order.date,
      customerName: order.customerName,
      totalAmount: order.totalAmount,
      products: order.products.map((product: { name: any; }) => product.name).join(', ') // Une los nombres de los productos en una cadena
    }));

    const csvData = this.convertToCSV(dataToExport);
    const blob = new Blob([csvData], { type: 'text/csv' });

    const a = document.createElement('a');
    a.href = window.URL.createObjectURL(blob);
    a.download = 'orders.csv';
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
