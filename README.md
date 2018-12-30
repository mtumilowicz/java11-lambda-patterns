# java11-lambda-patterns
Functional programming patterns in java.

_Reference_: https://www.youtube.com/watch?v=YnzisJh-ZNI  
_Reference_: https://www.youtube.com/watch?v=e4MT_OguDKg&index=146  
_Reference_: https://www.amazon.com/Modern-Java-Action-functional-programming/dp/1617293563

# introduction
This repo is a mix of functional design patterns that we have seen
in books or on the internet. 

# project description
1.  try to design you API in a composable way (package: **composable**)
    ```
    class ShoppingAPI {
        static Function<List<Item>, Cart> buy() {
            return Cart::new;
        }
    
        static Function<Cart, Order> order() {
            return Order::new;
        }
    
        static Function<Order, Delivery> deliver() {
            return Delivery::new;
        }
    
        static Function<List<Item>, Delivery> oneClickBuy() {
            return buy()
                    .andThen(order())
                    .andThen(deliver());
        }
    }
    ```
    * other used classes are as simple as they can be:
        ```
        @Value
        class Cart {
            ImmutableList<Item> items;
        
            Cart(List<Item> items) {
                this.items = ImmutableList.copyOf(items);
            }
        }
        
        @Value
        class Delivery {
            Order order;
        }
        
        @Value
        class Item {
            int id;
        }
        
        @Value
        class Order {
            Cart cart;
        }
        ```
1. it's often helpful to use currying(https://github.com/mtumilowicz/groovy-closure-currying) 
and functional interfaces to design API
    ```
    @FunctionalInterface
    interface CurrableDoubleBinaryOperator extends DoubleBinaryOperator {
    
        default DoubleUnaryOperator rate(double u) {
            return t -> applyAsDouble(t, u);
        }
    }
    ```
    then we can easily implement conversion classes
    ```
    class RateConverter implements CurrableDoubleBinaryOperator {
    
        @Override
        public double applyAsDouble(double value, double rate) {
            return value * rate;
        }
    
        static DoubleUnaryOperator milesToKmConverter() {
            return new RateConverter().rate(1.609);
        }
    
        static DoubleUnaryOperator celsiusToFahrenheitConverter() {
            return new RateConverter().rate(1.8).andThen(x -> x + 32);
        }
    }
    ```