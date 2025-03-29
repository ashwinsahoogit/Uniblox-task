# Uniblox E-commerce API

A Spring Boot-based REST API for an e-commerce system with features like product management, shopping cart, order processing, and coupon management.

## Features

- Product Management
- Shopping Cart Operations
- Order Processing
- Coupon Management
- User Management
- Admin Operations

## Tech Stack

- Java 17
- Spring Boot 3.x
- Maven
- JUnit 5
- Mockito
- Spring MVC

## API Endpoints

### Products

#### Get All Products
- **Endpoint**: `GET /api/products`
- **Description**: Retrieve all available products
- **Response**: List of products
```json
[
  {
    "id": "p1",
    "name": "Laptop",
    "description": "Gaming Laptop",
    "amount": 1000.0
  }
]
```

#### Get Product by ID
- **Endpoint**: `GET /api/products/{id}`
- **Description**: Get a specific product by its ID
- **Parameters**: 
  - `id`: Product ID
- **Response**: Single product object
- **Status Codes**:
  - 200: Success
  - 404: Product not found

### Cart

#### Add Item to Cart
- **Endpoint**: `POST /cart/add`
- **Description**: Add a product to the user's cart
- **Request Body**:
```json
{
  "userId": "user123",
  "productId": "p1",
  "quantity": 2
}
```
- **Response**: Updated cart object
- **Status Codes**:
  - 200: Success
  - 400: Invalid request
  - 404: Product not found

#### View Cart
- **Endpoint**: `GET /cart/view`
- **Description**: Get the contents of a user's cart
- **Parameters**: 
  - `userId`: User ID
- **Response**: Cart object with items
```json
{
  "userId": "user123",
  "items": [
    {
      "product": {
        "id": "p1",
        "name": "Laptop",
        "amount": 1000.0
      },
      "quantity": 2
    }
  ]
}
```

#### Get Cart Total
- **Endpoint**: `GET /cart/total`
- **Description**: Calculate the total amount of items in the cart
- **Parameters**: 
  - `userId`: User ID
- **Response**: Total amount as a number
```json
2000.0
```

#### Clear Cart
- **Endpoint**: `DELETE /cart/clear`
- **Description**: Remove all items from the user's cart
- **Parameters**: 
  - `userId`: User ID
- **Response**: Success message
```json
"Cart cleared successfully"
```

### Orders

#### Checkout
- **Endpoint**: `POST /order/checkout`
- **Description**: Process the checkout and create an order
- **Parameters**: 
  - `userId`: User ID (required)
  - `couponId`: Coupon ID (optional)
- **Response**: Created order object
```json
{
  "id": "order123",
  "userId": "user123",
  "products": [...],
  "totalAmount": 2000.0,
  "couponId": "coupon123",
  "discount": 200.0,
  "orderValue": 1800.0
}
```

#### Get All Orders
- **Endpoint**: `GET /order/all`
- **Description**: Retrieve all orders in the system
- **Response**: List of order objects

#### Get Total Purchase Amount
- **Endpoint**: `GET /order/total-amount`
- **Description**: Get the total amount of all orders
- **Response**: Total amount as a number

#### Get Total Items Sold
- **Endpoint**: `GET /order/total-items`
- **Description**: Get the total number of items sold
- **Response**: Total items count as a number

### Users

#### Register User
- **Endpoint**: `POST /api/users/register`
- **Description**: Register a new user
- **Request Body**:
```json
{
  "name": "John Doe",
  "type": "user"
}
```
- **Response**: Created user object
```json
{
  "id": "user123",
  "name": "John Doe",
  "type": "user"
}
```

#### Get User
- **Endpoint**: `GET /api/users/{userId}`
- **Description**: Get user details by ID
- **Parameters**: 
  - `userId`: User ID
- **Response**: User object
- **Status Codes**:
  - 200: Success
  - 404: User not found

### Coupons

#### Generate Coupon
- **Endpoint**: `POST /api/coupons/generate`
- **Description**: Generate a new coupon (Admin only)
- **Response**: Created coupon object
```json
{
  "id": "coupon123",
  "name": "DISCOUNT10",
  "discountPercentage": 10.0,
  "isUsed": false
}
```

#### Validate Coupon
- **Endpoint**: `GET /api/coupons/validate/{couponId}`
- **Description**: Check if a coupon is valid
- **Parameters**: 
  - `couponId`: Coupon ID
- **Response**: Boolean indicating validity
```json
true
```

#### Get All Coupons
- **Endpoint**: `GET /api/coupons/all`
- **Description**: Get all available coupons (Admin only)
- **Response**: List of coupon objects

### Admin

#### Get Total Purchase Amount
- **Endpoint**: `GET /api/admin/total-purchase`
- **Description**: Get the total purchase amount across all orders
- **Response**: Total amount as a number

#### Get Total Items Sold
- **Endpoint**: `GET /api/admin/total-items`
- **Description**: Get the total number of items sold
- **Response**: Total items count as a number

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the following command to build the project:
   ```bash
   # On Windows
   .\mvnw clean install
   
   # On Unix-based systems (Linux/MacOS)
   ./mvnw clean install
   ```
4. Start the application:
   ```bash
   # On Windows
   .\mvnw spring-boot:run
   
   # On Unix-based systems (Linux/MacOS)
   ./mvnw spring-boot:run
   ```

## Running Tests

```bash
# On Windows
.\mvnw test

# On Unix-based systems (Linux/MacOS)
./mvnw test
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/uniblox_store/uniblox/assignment/
│   │       ├── controller/    # REST controllers
│   │       ├── service/       # Business logic
│   │       ├── model/         # Data models
│   │       ├── dto/           # Data Transfer Objects
│   │       ├── repository/    # Data access
│   │       └── exception/     # Custom exceptions
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/uniblox_store/uniblox/assignment/
            └── *Test.java     # Test classes
```

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Test
- Spring Boot Starter Validation
- Lombok
- Mockito
- JUnit Jupiter

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.
