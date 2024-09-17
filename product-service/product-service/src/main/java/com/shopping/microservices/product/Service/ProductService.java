package com.shopping.microservices.product.Service;

import com.shopping.microservices.product.Model.Product;
import com.shopping.microservices.product.Repository.ProductRepository;
import com.shopping.microservices.product.dto.ProductRequest;
import com.shopping.microservices.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();
        productRepository.save(product);

        // LOG save
        log.info("Product has been created");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());

    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(prod -> new ProductResponse(prod.getId(), prod.getName(), prod.getDescription(), prod.getPrice()))
                .toList();
    }
}
