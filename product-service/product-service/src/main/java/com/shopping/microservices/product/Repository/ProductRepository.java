package com.shopping.microservices.product.Repository;

import com.shopping.microservices.product.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {


}
