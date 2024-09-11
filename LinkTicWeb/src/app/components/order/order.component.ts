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

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [MaterialModule, ReactiveFormsModule, CommonModule,DatePipe, MatDatepickerModule, MatInputModule],
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

  dataSource = new MatTableDataSource();
  filteredorders = new MatTableDataSource();

  searchValue = '';


  subscription$: Subscription[] = [];

  ordersService = inject(OrdersService);


  constructor(

    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,

  ) {
    this.ordersData = data.order;
    console.log("data recibida ",data.order);
  }


  ngOnInit(): void {
    this.isEditing = !!this.data.order.id;
    console.log("this.data.order.id ",this.data.order.id);
    this.ordersForm = this.formBuilder.group({
      id: [(this.ordersData as any).id, ''],
      date: [(this.ordersData as any).date, Validators.required],
      customerName: [(this.ordersData as any).customerName, [Validators.required]],
      totalAmount: [(this.ordersData as any).totalAmount, Validators.required],
      products: [(this.ordersData as any).products, Validators.required]
    });
    this.isEditing = !!this.data.order.id;
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
