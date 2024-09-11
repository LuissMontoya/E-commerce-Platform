import { CommonModule, DatePipe } from '@angular/common';
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
import { ProductComponent } from '../product/product.component';
import { OrdersService } from 'src/app/services/orders.service';
import { ProductsService } from 'src/app/services/products.service';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule,MatSelectModule, CommonModule,DatePipe, MatDatepickerModule, MatInputModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.css',
  providers: [{ provide: OrdersService },
    { provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE] },
    { provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS },
    ]
})
export class OrderComponent implements OnInit {

  ordersForm!: FormGroup;
  ordersData: any;
  orders: any[] = [];
  products: any[] = [];

  dataSource = new MatTableDataSource();
  filteredorders = new MatTableDataSource();
  filteredproducts = new MatTableDataSource();

  searchValue = '';
  subscription$: Subscription[] = [];

  ordersService = inject(OrdersService);
  productservices=inject(ProductsService);


  constructor(

    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,

  ) {
    this.ordersData = data.order;
  }


  ngOnInit(): void {
    this.isEditing = !!this.data.order.id;
    console.log("this.data.order  ",this.data.order);
    this.getAllproducts();
    this.ordersForm = this.formBuilder.group({
      id: [(this.ordersData as any).id, ''],
      date: [(this.ordersData as any).date, Validators.required],
      customerName: [(this.ordersData as any).customerName, [Validators.required]],
      totalAmount: [(this.ordersData as any).totalAmount, Validators.required],
      products: [(this.ordersData as any).products, Validators.required]
    });
    this.isEditing = !!this.data.order.id;

    if (this.isEditing && this.data.order) {
      console.log("this.data.client.category.id " ,this.data.order.products);
      for(var i=0; i< this.data.order.products.length; i++){
        console.log("i "+this.data.order.products[i].name);
        this.products.push(this.data.order.products[i].id);
      }
      
      this.ordersForm.get('products')?.setValue(this.products);
     
    }
  }

  save(order: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.ordersService.create(order).subscribe(res => {
          if(res.statusCode.toString() == '201'){
            alert("Registro creado");
            this.dialogRef.close(201); 
            } else {
               alert('Error al actualizar el orden');
            }
      })
    ];
  }


  edit(order: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.ordersService.edit(order).subscribe(res => {
        alert(res.message);
        this.dialogRef.close(order); 
      })
    ];
  }

  refreshTable(): void {
    this.ordersService.getAll().subscribe(res => {
      this.orders = res.objectResponse; 
      this.filteredorders =res.objectResponse; 
    });
  }

  isEditing: boolean = false;

  onSubmit() {
    if (this.data.isEditing) {
      const orderDTO = {
        id: this.ordersForm.controls['id'].value,
        date: this.ordersForm.controls['date'].value,
        customerName: this.ordersForm.controls['customerName'].value,
        totalAmount: this.ordersForm.controls['totalAmount'].value,
        products: this.ordersForm.controls['products'].value
      };
      this.edit(orderDTO);
    }
    else {
      const orders = {
        date: this.ordersForm.controls['date'].value,
        customerName: this.ordersForm.controls['customerName'].value,
        totalAmount: this.ordersForm.controls['totalAmount'].value,
        products: this.ordersForm.controls['products'].value,
      };
      this.save(orders);

    }

    //this.dialogRef.close(this.ordersForm.value);
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

  clearForm() {
    this.ordersForm.reset();
  }

  closeDialog() {
    this.dialogRef.close(this.ordersForm.value);
  }

  getAllorders() {
    this.subscription$ = [
      ...this.subscription$,
      this.ordersService.getAll().subscribe(res => {

        this.orders = res.objectResponse;
        this.filteredorders.data = this.orders;
      })
    ];
  }
}
