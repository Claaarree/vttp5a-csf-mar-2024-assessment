import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LineItem, Order } from '../models';
import { CartStore } from '../cart.store';
import { lastValueFrom } from 'rxjs';
import { ProductService } from '../product.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css'
})
export class ConfirmCheckoutComponent implements OnInit{
  
  // TODO Task 3
  private fb = inject(FormBuilder);
  private cartStore = inject(CartStore);
  private productSvc = inject(ProductService);
  private router = inject(Router);
  private activateRoute = inject(ActivatedRoute);
  form! : FormGroup;
  cart!: LineItem[];
  totalPrice: number = 0;
  
  ngOnInit(): void {
    this.form = this.createForm();
    this.cartStore.getCart.subscribe(
      value => {
        this.cart = value;
        value.forEach(li => {
          const linePrice = (li.price * li. quantity);
          this.totalPrice += linePrice;
        })
      }
    );
  }

  private createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', Validators.required),
      address: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      priority: this.fb.control<boolean>(false),
      comments: this.fb.control<string>('')
    });
  }

  protected handleSubmit() {
    this.form.value.cart = {lineItems: this.cart};
    const order: Order = this.form.value
    this.productSvc.checkout(order).then(
      val => {
        const message = JSON.stringify(val);
        alert(message);
        if (message.includes("orderId")){
          this.router.navigate(['']);
        }
      }
    );
  }

}
