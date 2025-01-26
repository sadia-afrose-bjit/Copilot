package bjit.Copilot.controllers;

import bjit.Copilot.models.Product;
import bjit.Copilot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Retrieves all products.
     *
     * @return a list of all products
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve
     * @return the product, or null if not found
     * @apiNote This endpoint retrieves a specific product by its ID.
     * @apiTitle Get a Product by ID
     * @apiAction GET
     * @apiGuideline Retrieve a specific product by its ID.
     * @apiProblem We need to retrieve a specific product when the user provides its ID.
     * @apiInstruction The endpoint should return the details of a single product when given a product ID.
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    /**
     * Adds a new product.
     *
     * @param product the product to add
     * @return the added product
     */
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
}