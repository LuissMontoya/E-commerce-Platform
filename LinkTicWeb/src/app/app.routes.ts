import { Routes } from '@angular/router';
import { ClientComponent } from './components/client/client.component';
import { ListOrderComponent } from './components/list-order/list-order.component';
import { ListProductComponent } from './components/list-product/list-product.component';


export const routes: Routes = [
    //{ path: 'home', component: HomeComponent },
  { path: 'client', component: ClientComponent },
  { path: 'app-list-order', component: ListOrderComponent },
  { path: 'app-list-product', component: ListProductComponent },
  { path: '**', component: ClientComponent }, 
];
