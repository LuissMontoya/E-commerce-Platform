import { CommonModule, DatePipe } from '@angular/common';
import { Component, Inject, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableDataSource } from '@angular/material/table';
import { Subscription } from 'rxjs';
import { MaterialModule } from 'src/app/modules/material/material.module';
import { CategoryService } from 'src/app/services/category.service';
import { ProductsService } from 'src/app/services/products.service';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [MaterialModule,CommonModule, ReactiveFormsModule, DatePipe, MatDatepickerModule,  MatFormFieldModule,
    MatSelectModule,MatInputModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit{

  productForm!: FormGroup;
  ProductData: any;
  Products: any[] = [];
  Categories: any[] = [];

  dataSource = new MatTableDataSource();
  filteredProducts = new MatTableDataSource();
  searchValue = '';

  subscription$: Subscription[] = [];

  productsService = inject(ProductsService);
  categoryService = inject(CategoryService);

  constructor(

    private formBuilder: FormBuilder,
    public dialogRef: MatDialogRef<ProductComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,

  ) {
    this.ProductData = data.client;
  }


  ngOnInit(): void {
    this.getAllCategories();
    this.isEditing = !!this.data.client.sharedKey;

    console.log('ProductData:', this.ProductData);
console.log('Client Data:', this.data.client);


    this.productForm = this.formBuilder.group({
      id: [(this.ProductData as any).id, ''],
      name: [(this.ProductData as any).name, Validators.required],
      description: [(this.ProductData as any).description, Validators.required],
      price: [(this.ProductData as any).price, [Validators.required]],
      stock: [(this.ProductData as any).stock, Validators.required],
      category: [(this.ProductData as any).category?.id, Validators.required],
    });
    this.isEditing = !!this.data.client.id;

 if (this.isEditing && this.data.client.category) {
      this.productForm.get('category')?.setValue(this.data.client.category.id);
      console.log("this.data.client.category.id " ,this.data.client.category.id);
    }

  }

  save(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.productsService.create(client).subscribe(res => {
          if(res.statusCode.toString() == '201'){
            alert("Registro creado");
            this.dialogRef.close(201); 
            } else {
               alert('Error al actualizar el producto');
            }
      })
    ];
  }


  edit(client: any) {
    this.subscription$ = [
      ...this.subscription$,
      this.productsService.edit(client).subscribe(res => {
        alert(res.message);
        this.dialogRef.close(client); 
      })
    ];
  }

  refreshTable(): void {
    this.productsService.getAll().subscribe(res => {
      this.Products = res.objectResponse; 
      this.filteredProducts =res.objectResponse; 
    });
  }

  isEditing: boolean = false;

  onSubmit() {

    if (this.data.isEditing) {
      const ProductDTO = {
        id: this.productForm.controls['id'].value,
        name: this.productForm.controls['name'].value,
        description: this.productForm.controls['description'].value,
        price: this.productForm.controls['price'].value,
        stock: this.productForm.controls['stock'].value,
        category_id: this.productForm.controls['category'].value
      };
      this.edit(ProductDTO);
    }
    else {
      const Products = {
        name: this.productForm.controls['name'].value,
        description: this.productForm.controls['description'].value,
        price: this.productForm.controls['price'].value,
        stock: this.productForm.controls['stock'].value,
        category_id: this.productForm.controls['category'].value
      };
      this.save(Products);

    }

    //this.dialogRef.close(this.productForm.value);
  }

  clearForm() {
    this.productForm.reset();
  }

  closeDialog() {
    this.dialogRef.close(this.productForm.value);
  }

  getAllProducts() {
    this.subscription$ = [
      ...this.subscription$,
      this.productsService.getAll().subscribe(res => {

        this.Products = res.objectResponse;
        this.filteredProducts.data = this.Products;
      })
    ];
  }

  getAllCategories() {
    this.subscription$ = [
      ...this.subscription$,
      this.categoryService.getAll().subscribe(res => {

        this.Categories = res.objectResponse;
      })
    ];
  }
}
