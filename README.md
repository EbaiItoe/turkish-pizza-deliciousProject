[README (4).md](https://github.com/user-attachments/files/23536169/README.4.md)
# Turkish-PIZZA-delicious - README

## Overview

** TURKISH PIZZA-licious APP** is a command-line Point of Sale (POS) system for a pizza restaurant. It allows customers to build custom pizzas, select drinks, add sides, and generates receipts. The application is built in Java using Maven.

## Project Structure

```
pizzalicious-pos/
├── src/
│   ├── main/java/
│   │   ├── app/App.java              # Main application entry point
│   │   ├── model/                    # Data models
│   │   │   ├── Pizza.java
│   │   │   ├── Order.java
│   │   │   ├── Drink.java
│   │   │   ├── GarlicKnots.java
│   │   │   ├── Size.java
│   │   │   ├── Crust.java
│   │   │   ├── Meat.java
│   │   │   ├── Cheese.java
│   │   │   ├── RegularTopping.java
│   │   │   ├── Sauce.java
│   │   │   ├── DrinkSize.java
│   │   │   └── DrinkFlavor.java
│   │   └── service/                  # Business logic
│   │       ├── PricingService.java
│   │       └── ReceiptService.java
│   └── test/java/
├── receipts/                          # Generated receipt files
├── pom.xml                            # Maven configuration
└── .gitignore
```

## Key Features

### 🍕 Pizza Customization
- **Sizes**: Personal (8"), Medium (12"), Large (16")
- **Crusts**: Thin, Regular, Thick, Cauliflower, Stuffed
- **Meats**: Pepperoni, Sausage, Ham, Bacon, Chicken, Meatball
- **Cheeses**: Mozzarella, Parmesan, Ricotta, Goat Cheese, Buffalo
- **Regular Toppings**: Onions, Mushrooms, Bell Peppers, Olives, Tomatoes, Spinach, Basil, Pineapple, Anchovies
- **Sauces**: Marinara, Alfredo, Pesto, BBQ, Buffalo, Olive Oil
- **Stuffed Crust Option**: Available for all pizza sizes

### 🍹 Beverages
- **Sizes**: Small ($2.00), Medium ($2.50), Large ($3.00)
- **Flavors**: Coke, Diet Coke, Sprite, Fanta, Lemonade, Water

### 🧄 Sides
- **Garlic Knots**: $1.50 each

### 💰 Pricing
Pricing is tiered by pizza size with premium charges for meats and cheeses:
- **Meats**: First meat costs size-dependent price; additional meats cost extra
- **Cheeses**: First cheese costs size-dependent price; additional cheeses cost extra
- **Regular Toppings**: Included at no extra charge

### 📄 Receipt Generation
- Receipts are automatically generated and saved to the `receipts` directory
- Filename format: `yyyyMMdd-HHmmss.txt`
- Receipts include order details, item descriptions, subtotals, and total price

## Architecture

### Model Layer
- `Pizza`: Represents a pizza with customizable components
- `Order`: Aggregates pizzas, drinks, and garlic knots
- `Drink`: Represents a beverage
- `GarlicKnots`: Side item with quantity

### Service Layer
- `PricingService`: Calculates prices for pizzas, drinks, and complete orders
- `ReceiptService`: Generates and persists receipts to disk

### Presentation Layer
- `App`: Interactive CLI menu system for order creation and checkout

## Building & Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Build
```sh
mvn clean compile
```

### Run
```sh
mvn exec:java
```

Or directly:
```sh
mvn clean compile exec:java
```

## Usage

1. Start the application
2. Select "1) New Order" from the home menu
3. Navigate the menu to:
   - Add pizzas with custom toppings
   - Add drinks
   - Add garlic knots
4. Proceed to checkout to review order details
5. Confirm purchase to generate and save receipt

## Example Receipt

```
PIZZA-licious Receipt
---------------------
Pizza 1: 
PERSONAL_8 REGULAR
  Meats: PEPPERONI
  Cheeses: GOAT_CHEESE
  Regular: ONIONS, MUSHROOMS x3
  Sauces: MARINARA
Subtotal: 10.25

Drink: SPRITE SMALL - 2.0
Garlic Knots x4 - 6.0

Total: 18.25
```

## Dependencies

- **Java**: 17
- **Build Tool**: Maven 3.13.0
- **Plugins**: 
  - Maven Compiler Plugin 3.13.0
  - Exec Maven Plugin 3.5.0

## Project Configuration

See `pom.xml` for complete Maven configuration.

---

*Built with ☕ and 🍕*
