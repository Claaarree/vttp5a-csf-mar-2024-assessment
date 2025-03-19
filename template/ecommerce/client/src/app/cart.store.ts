
// TODO Task 2

import { ComponentStore } from "@ngrx/component-store";
import { Cart, LineItem } from "./models";

const INIT_STORE = {
    lineItems:[]
}

// Use the following class to implement your store
export class CartStore extends ComponentStore<Cart>{

    constructor() { super(INIT_STORE); }

    // addLineItem(li: LineItem)
    readonly addLineItem = this.updater<LineItem>(
        (state: Cart, li: LineItem) => {
            const newCart: Cart = {
                lineItems: [...state.lineItems, li]
            };
            return newCart;
        }
    );

    // getItemCount(): number
    readonly getItemCount = this.select<number>(
        (state: Cart) => state.lineItems.length
    );

    // getCart(): LineItem[]
    readonly getCart = this.select<LineItem[]>(
        (state: Cart) => state.lineItems
    )
}
