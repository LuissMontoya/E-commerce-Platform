import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {MaterialModule} from './modules/material/material.module';
import { ClientComponent } from './components/client/client.component';
import {SidebarComponent} from './shared/sidebar/sidebar.component';
import { ListComponent } from './components/list/list.component';
import { ListProductComponent } from './components/list-product/list-product.component';
import { OrderComponent } from './components/order/order.component';
import { ListOrderComponent } from './components/list-order/list-order.component';
import { ProductComponent } from './components/product/product.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,MaterialModule,ClientComponent,ListComponent,SidebarComponent,ListProductComponent,ProductComponent,OrderComponent,ListOrderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'client-management-app';
}
