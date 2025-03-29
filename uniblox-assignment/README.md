# Uniblox Assignment

A Spring Boot application demonstrating REST API development with best practices.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

## Project Structure

```
src/main/java/com/uniblox_store/uniblox/assignment/
├── config/         # Configuration classes
├── controller/     # REST API endpoints
├── dto/           # Data Transfer Objects
├── exception/     # Custom exception handling
├── model/         # Entity classes
├── repository/    # Data access layer
└── service/       # Business logic layer
```

## Getting Started

1. Clone the repository:
```bash
git clone <repository-url>
cd uniblox-assignment
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Documentation

### Cart Management

#### Add Item to Cart
- **POST** `/cart/add`
- **Description**: Add a product to the user's cart
- **Request Body**:
  ```json
  {
    "userId": "string",
    "productId": "string",
    "quantity": "integer"
  }
  ```
- **Response**: Cart object with updated items

#### View Cart
- **GET** `/cart/view?userId={userId}`
- **Description**: Get the contents of a user's cart
- **Response**: Cart object with all items

#### Get Cart Total
- **GET** `/cart/total?userId={userId}`
- **Description**: Calculate the total amount of items in the cart
- **Response**: Total amount as a double

#### Clear Cart
- **DELETE** `/cart/clear?userId={userId}`
- **Description**: Remove all items from the user's cart
- **Response**: Success message

### Order Management

#### Checkout
- **POST** `/order/checkout?userId={userId}&couponId={couponId}`
- **Description**: Process the checkout and create an order
- **Parameters**:
  - `userId`: Required
  - `couponId`: Optional
- **Response**: Created Order object

#### Get All Orders
- **GET** `/order/all`
- **Description**: Retrieve all orders in the system
- **Response**: List of Order objects

#### Get Total Purchase Amount
- **GET** `/order/total-amount`
- **Description**: Get the total amount of all orders
- **Response**: Total amount as a double

#### Get Total Items Sold
- **GET** `/order/total-items`
- **Description**: Get the total number of items sold
- **Response**: Total items count as an integer

### Coupon Management

#### Generate Coupon
- **POST** `/api/coupons/generate`
- **Description**: Generate a new coupon (Admin only)
- **Response**: Created Coupon object

#### Validate Coupon
- **GET** `/api/coupons/validate/{couponId}`
- **Description**: Check if a coupon is valid
- **Response**: Boolean indicating validity

#### Get All Coupons
- **GET** `/api/coupons/all`
- **Description**: Get all available coupons (Admin only)
- **Response**: List of Coupon objects

### Admin Operations

#### Get Total Purchase Amount
- **GET** `/api/admin/total-purchase`
- **Description**: Get the total purchase amount across all orders
- **Response**: Total amount as a double

#### Get Total Items Sold
- **GET** `/api/admin/total-items`
- **Description**: Get the total number of items sold
- **Response**: Total items count as an integer

## Testing

Run the tests using:
```bash
mvn test
```

## Dependencies

- Spring Boot 3.4.4
- Spring Web
- Spring Validation
- Lombok
- Spring Boot Test

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Contact

[Add your contact information here] 