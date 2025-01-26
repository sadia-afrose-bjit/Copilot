package bjit.Copilot.controllers;

import bjit.Copilot.models.Cart;
import bjit.Copilot.models.Product;
import bjit.Copilot.repositories.CartRepository;
import bjit.Copilot.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * REST controller for managing the shopping cart.
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Adds a product to the cart.
     *
     * @param cartId the ID of the cart
     * @param productId the ID of the product to add
     * @return the updated cart
     * @apiNote This endpoint adds a product to the user's shopping cart.
     * @apiTitle Add Product to Cart
     * @apiGuideline POST
     * @apiGoal Add a product to the user's shopping cart.
     * @apiProblem The user needs to add a product to their cart.
     * @apiInstruction The endpoint should accept the product ID and quantity, then add it to the cart.
     */
    @PostMapping("/add")
    public Cart addProductToCart(@RequestParam Long cartId, @RequestParam Long productId) {
        Cart cart = cartRepository.findById(cartId).orElseGet(() -> {
            Cart newCart = new Cart();
            cartRepository.save(newCart);
            return newCart;
        });
        if (cart.getProducts() == null) {
            cart.setProducts(new ArrayList<>());
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            boolean productExists = cart.getProducts().stream()
                .anyMatch(p -> p.getId().equals(productId));
            if (!productExists) {
                cart.getProducts().add(product);
                cartRepository.save(cart);
            }
        }
        return cart;
    }

    /**
     * Removes a product from the cart.
     *
     * @param cartId the ID of the cart
     * @param productId the ID of the product to remove
     * @return the updated cart
     * @apiNote This endpoint removes a product from the shopping cart.
     * @apiTitle Remove Product from Cart
     * @apiAction DELETE
     * @apiGuideline Remove a product from the shopping cart.
     * @apiProblem The user wants to remove a specific product from their cart.
     * @apiInstruction The endpoint should accept the product ID and remove the product from the cart.
     */
    @DeleteMapping("/remove")
    public Cart removeProductFromCart(@RequestParam Long cartId, @RequestParam Long productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            cart.getProducts().removeIf(product -> product.getId().equals(productId));
            cartRepository.save(cart);
        }
        return cart;
    }

    /**
     * Updates the cart.
     *
     * @param cart the cart to update
     * @return the updated cart
     * @throws RuntimeException if the cart is not found
     */
    @PutMapping("/update")
    public Cart updateCart(@RequestBody Cart cart) {
        Cart existingCart = cartRepository.findById(cart.getId()).orElse(null);
        if (existingCart != null) {
            if (cart.getProducts() == null) {
                cart.setProducts(new ArrayList<>());
            }
            return cartRepository.save(cart);
        } else {
            throw new RuntimeException("Cart not found");
        }
    }

    /**
     * Retrieves the current contents of the user's shopping cart.
     *
     * @param cartId the ID of the cart
     * @return the cart with all its items, or null if not found
     * @apiNote This endpoint retrieves the current contents of the user's shopping cart.
     * @apiTitle Get Cart Details
     * @apiAction GET
     * @apiGuideline Retrieve the current contents of the user's shopping cart.
     * @apiProblem The user wants to view all items in their cart.
     * @apiInstruction The endpoint should return all items currently in the cart.
     */
    @GetMapping("/details")
    public Cart getCartDetails(@RequestParam Long cartId) {
        return cartRepository.findById(cartId).orElse(null);
    }
}