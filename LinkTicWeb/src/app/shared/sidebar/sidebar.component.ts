import { Component,inject,OnInit } from '@angular/core';
import { MaterialModule } from '../../modules/material/material.module';
import { AsyncPipe,CommonModule } from '@angular/common';
import { ListComponent } from 'src/app/components/list/list.component';
import { UserService } from 'src/app/services/user.service';
import { ListProductComponent } from 'src/app/components/list-product/list-product.component';
import { ListOrderComponent } from 'src/app/components/list-order/list-order.component';



@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [MaterialModule,AsyncPipe,ListComponent,CommonModule,ListProductComponent,ListOrderComponent],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
  showClientList: boolean = false;
  showProductList: boolean = false;
  showOrderList: boolean = false;

  authServices=inject(UserService);

  ngOnInit() {
    this.login();
    console.log('SidebarComponent initialized, showClientList:', this.showClientList);
  }

  toggleClientList(): void {
    this.showClientList = !this.showClientList;
    this.showProductList = false;
    this.showOrderList = false;
  }

  toggleProductsList(): void {
    this.showClientList = false;
    this.showProductList = !this.showProductList;
    this.showOrderList = false;
  }

  toggleOrderList(): void {
    this.showClientList = false;
    this.showProductList = false;
    this.showOrderList = !this.showOrderList;
  }

  login(): void {
    this.authServices.create("juan.perez@example.com", "admin").subscribe(
      response => {
        localStorage.setItem('token', response.objectResponse);
        console.log('Login successful', response);
      },
      error => {
        console.error('Login failed', error);
      }
    );
  }

}

