[![Build Status](https://travis-ci.com/mtumilowicz/java11-lambda-patterns.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-lambda-patterns)

# java11-lambda-patterns
Functional programming patterns in java.

_Reference_: https://www.youtube.com/watch?v=YnzisJh-ZNI  
_Reference_: https://www.youtube.com/watch?v=e4MT_OguDKg  
_Reference_: https://www.youtube.com/watch?v=ePXnCezwRuw  
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
1. it's often helpful to use currying 
(https://github.com/mtumilowicz/groovy-closure-currying) 
and functional interfaces to design API (package: **converter**)
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
1. use tuples and know the stream API (package: **customer**)
    ```
    @Value
    @Builder
    public class Customer {
        ImmutableList<Order> orders;
        ImmutableList<Expense> expenses;
        
        // ... methods
    }
    
    @Value
    @Builder
    class Expense {
        Year year;
        ImmutableSet<String> tags;
    
        Stream<String> getTagsStream() {
            return SetUtils.emptyIfNull(tags).stream();
        }
    }
    
    @Value
    @Builder
    class Order {
        int id;
        BigDecimal price;
    
        boolean hasPrice() {
            return nonNull(price);
        }
    }
    ```
    examples:
    * find order with max price
        ```
        Optional<Order> findOrderWithMaxPrice() {
            return ListUtils.emptyIfNull(orders).stream()
                    .filter(Order::hasPrice)
                    .max(comparing(Order::getPrice));
        
        }
        ```
    * find top3 orders by price
        ```
        Triple<Order, Order, Order> findTop3OrdersByPrice() {
            return ListUtils.emptyIfNull(orders).stream()
                    .filter(Order::hasPrice)
                    .sorted(comparing(Order::getPrice, reverseOrder()))
                    .limit(3)
                    .collect(collectingAndThen(toList(), ListToTripleConverter::convert));
        }
        ```
    * construct an immutable map with (key, value) = (year, tags from that year)
        ```
        ImmutableMap<Year, Set<String>> yearTagsExpensesMap() {
            return ListUtils.emptyIfNull(expenses).stream()
                    .collect(collectingAndThen(groupingBy(Expense::getYear, flatMapping(Expense::getTagsStream, toSet())),
                            ImmutableMap::copyOf)
                    );
        }
        ```
1. try to avoid decorator pattern - use function 
composition instead (package: **decorator**)
    ```
    @Value
    @RequiredArgsConstructor
    class Camera {
        Function<Color, Color> transformColors;
    
        Camera() {
            this.transformColors = Function.identity();
        }
    
        Camera withFilter(Function<Color, Color> transform) {
            return new Camera(transformColors.andThen(transform));
        }
    
        Color snap(Color color) {
            return transformColors.apply(color);
        }
    }
    ```
    and a library of functions to transform colors
    ```
    class ColorTransformers {
        static Color brighten(Color color, int modifier) {
            Preconditions.checkArgument(nonNull(color));
            Preconditions.checkArgument(modifier >= 0);
    
            return new Color(red(color) + modifier,
                    green(color) + modifier,
                    blue(color) + modifier);
        }
    
        static Color negate(Color color) {
            Preconditions.checkArgument(nonNull(color));
    
            return new Color(negate(red(color)), negate(green(color)), negate(blue(color)));
        }
    
        private static int negate(int color) {
            Preconditions.checkArgument(color <= 255);
            Preconditions.checkArgument(color >= 0);
    
            return 255 - color;
        }
    
        private static int red(Color color) {
            return color.getRed();
        }
    
        private static int green(Color color) {
            return color.getGreen();
        }
    
        private static int blue(Color color) {
            return color.getBlue();
        }
    }
    ```
    examples:
    ```
    given:
    def camera = new Camera().withFilter({ ColorTransformers.negate(it) })
            .withFilter({ ColorTransformers.brighten(it, 20) })
    
    expect:
    camera.snap(new Color(100, 100, 100)) == new Color(175, 175, 175)
    ```
1. create complex DSL with hiding creation 
inside (package: **dsl**)
    ```
    @Value
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public class Mailer {
        private static final Mailer EMPTY = new Mailer();
    
        String from;
        String to;
    
        private Mailer() {
            this.from = "";
            this.to = "";
        }
    
        Mailer from(String from) {
            return new Mailer(StringUtils.defaultIfEmpty(from, ""), to);
        }
    
        Mailer to(String to) {
            return new Mailer(from, StringUtils.defaultIfEmpty(to, ""));
        }
    
        static void send(UnaryOperator<Mailer> block) {
            System.out.println(block.apply(EMPTY));
        }
    }
    ```
    and the example of usage:
    ```
    Mailer.send(
        mailer -> mailer.from("mtumilowicz01@gmail.com")
                        .to("abc@o2.pl")
    )
    ```
    **note that at any point we don't have direct access to the object,
    we cannot create object manually and we cannot reuse it
    (there is NO Mailer object)**
1. know the comparator API (package: **person**)

    suppose we want to compare person by name, then by surname (if surname is null goes first)
    ```
    @Value
    @Builder
    class Person {
        static final Comparator<Person> NAME_SURNAME_COMPARATOR = comparing(Person::getName)
                .thenComparing(Person::getSurname, nullsFirst(naturalOrder()));
    
        String name;
        String surname;
    }
    ```
    and tests:
    ```
    given:
    def B_B = Person.builder().name("B").surname("B_B").build()
    def C_A = Person.builder().name("C").surname("C_A").build()
    def A = Person.builder().name("A").surname("A").build()
    def B_A = Person.builder().name("B").surname("B_A").build()
    def C_null = Person.builder().name("C").surname(null).build()
    def C_null2 = Person.builder().name("C").surname(null).build()
    
    when:
    def list = List.of(B_B, C_A, A, B_A, C_null, C_null2)
            .stream()
            .sorted(Person.NAME_SURNAME_COMPARATOR)
            .collect(toList())
    
    then:
    list == [A, B_A, B_B, C_null, C_null2, C_A]
    ```
1. compose behaviours instead of accumulating objects in lists
(package: **salary**)

    suppose we want to calculate salary according to some
    salary rules
    ```
    public enum SalaryRules {
        TAX(new RateConverter().rate(0.81)),
        BONUS(new RateConverter().rate(1.2)),
        ADDITION(salary -> salary + 100);
    
        public final DoubleUnaryOperator operator;
    
        SalaryRules(DoubleUnaryOperator operator) {
            this.operator = operator;
        }
    }
    ```
    * naive approach
        ```
        class NaiveSalaryCalculator {
            final List<SalaryRules> operators = new LinkedList<>();
        
            NaiveSalaryCalculator with(SalaryRules rule) {
                operators.add(rule);
        
                return this;
            }
        
            double calculate(double salary) {
                return operators.stream()
                        .map(rule -> rule.operator)
                        .reduce(DoubleUnaryOperator.identity(), DoubleUnaryOperator::andThen)
                        .applyAsDouble(salary);
        
            }
        }
        ```
    * better approach - composing functions
        ```
        class SalaryCalculator {
            private final DoubleUnaryOperator operator;
        
            SalaryCalculator() {
                this(DoubleUnaryOperator.identity());
            }
        
            private SalaryCalculator(DoubleUnaryOperator operator) {
                this.operator = operator;
            }
        
            SalaryCalculator with(SalaryRules rule) {
                return new SalaryCalculator(operator.andThen(rule.operator));
            }
        
            double calculate(double salary) {
                return operator.applyAsDouble(salary);
        
            }
        }
        ```
    * tests
        ```
        given:
        def calculator = new SalaryCalculator().with(SalaryRules.BONUS)
                .with(SalaryRules.ADDITION)
                .with(SalaryRules.TAX)
        
        expect:
        calculator.calculate(1000) == 1053
        ```
1. strategy pattern (library of functions) (package: **strategy**)

    we have `PriceProvider` to get the current stock price (`Stock` class
    is as simple as possible)
    ```
    @Value
    class PriceProvider {
        @Getter(AccessLevel.NONE)
        IntUnaryOperator priceSource;
    
        int getPrice(int id) {
            return priceSource.applyAsInt(id);
        }
    }
    
    @Value
    class Stock {
        int id;
    }
    ```
    example: suppose we want to calculate prices
    for a given stream of stocks (with some
    custom filtering)
    ```
    @Value
    class Calculator {
        PriceProvider priceProvider;
    
        int totalValues(List<Stock> integers, IntPredicate take) {
            return integers.stream()
                    .map(Stock::getId)
                    .mapToInt(priceProvider::getPrice)
                    .filter(take)
                    .sum();
        }
        
        // library of functions
        static IntPredicate priceLessThan(int limit) {
            return it -> it < limit;
        }
    
        static IntPredicate priceEquals(int limit) {
            return it -> it == limit;
        }
    }
    ```
    suppose we want to sum stocks with prices < 3 or prices == 5
    ```
    given:
    def stocks = [new Stock(1),
                  new Stock(2),
                  new Stock(3),
                  new Stock(4),
                  new Stock(5),
                  new Stock(6),
                  new Stock(7)]
    
    def calculator = new Calculator(new PriceProvider(IntUnaryOperator.identity()))
    
    when:
    def sum = calculator.sumPrices(stocks, Calculator.priceLessThan(3) | Calculator.priceEquals(5))
    
    then:
    sum == 8
    ```
1. template method (variation of DSL example) (package: **template**)

    suppose we have `AutoCloseable` resource
    ```
    @Value
    class Resource implements AutoCloseable {
        private Resource(String param) {
            System.out.println("create");
        }
    
        void op1() {
            System.out.println("op1");
        }
    
        void op2() {
            System.out.println("op2");
        }
    
        static void use(String param, Consumer<Resource> block) {
            try (final var resource = new Resource(param)) {
                block.accept(resource);
            }
        }
    
        @Override
        public void close() {
            System.out.println("close");
        }
    }
    ```
    we publish only template method (`use`), we don't have direct 
    access to the object - so we guarantee that if someone
    will use resource it will be closed in the end
    
    example:
    ```
    Resource.use("param", resource -> {resource.op1(); resource.op2();})
    ```
    produces output:
    ```
    create
    op1
    op2
    close
    ```