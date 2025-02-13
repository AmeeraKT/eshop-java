package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Product findById(String id) {
        return productData.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Product update(Product updatedProduct) {
        Optional<Product> existingProduct = productData.stream()
                .filter(p -> p.getProductId().equals(updatedProduct.getProductId()))
                .findFirst();

        existingProduct.ifPresent(product -> {
            int index = productData.indexOf(product);
            productData.set(index, updatedProduct);
        });

        return existingProduct.orElse(null);
    }


    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
