package com.project.code.Controller;

import com.project.code.Model.*;
import com.project.code.Repo.*;
import com.project.code.Service.ServiceClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private ProductRepository  productRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ServiceClass serviceClass;


   @PutMapping("/update")
   public Map<String, String> updateInventory(@RequestBody CombinedRequest request){

        Product product = request.getProduct();
        Inventory inventory = request.getInventory();

        if(!serviceClass.validateProductId(product.getId())){
            return Map.of("message","Product does not exist");
        }   

        Inventory existingInventory = serviceClass.getInventoryId(inventory);
        if(existingInventory != null){
            existingInventory.setStockLevel(inventory.getStockLevel());
            inventoryRepository.save(existingInventory);
            return Map.of("message","Inventory updated Successfully");
        }

        return Map.of("message","No inventory Found! ");
   }

   @PostMapping("/save")
   public Map<String , String> saveInventory(@RequestBody Inventory inventory){
        
        if(!serviceClass.validateInventory(inventory)){
            return Map.of("message","Inventory Already Exists");
        }

        inventoryRepository.save(inventory);
         return Map.of("message","Inventory saved Successfully");

   }


   @GetMapping("/store/{storeId}")
   public Map<String,List<Product>> getAllProducts(@PathVariable Long storeId){

    List<Inventory> inventories = inventoryRepository.findByStore_Id(storeId);
    List<Product> products = new ArrayList<>();

    for(Inventory inv : inventories){
        products.add(inv.getProduct());
    }

    return Map.of("products", products);
   }
 @GetMapping("/filter")
    public Map<String, List<Product>> getProductName(
            @RequestParam String category,
            @RequestParam String name) {

        List<Product> products;

        if ("null".equalsIgnoreCase(category) && "null".equalsIgnoreCase(name)) {
            products = productRepository.findAll();
        } else if ("null".equalsIgnoreCase(category)) {
            products = List.of(productRepository.findByName(name));
        } else if ("null".equalsIgnoreCase(name)) {
            products = productRepository.findByCategory(category);
        } else {
            products = productRepository.findByCategory(category)
                    .stream()
                    .filter(p -> p.getName().equalsIgnoreCase(name))
                    .toList();
        }

        return Map.of("product", products);
    }

    @GetMapping("/search")
    public Map<String, List<Product>> searchProduct(
            @RequestParam String name,
            @RequestParam Long storeId) {

        List<Product> products = productRepository.findByNameLike(storeId, name);

        return Map.of("product", products);
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, String> removeProduct(@PathVariable Long id) {

        if (!serviceClass.validateProductId(id)) {
            return Map.of("message", "Product not found");
        }

        inventoryRepository.deleteByProduct_Id(id);
        productRepository.deleteById(id);

        return Map.of("message", "Product deleted successfully");
    }

    @GetMapping("/validate")
    public boolean validateQuantity(
            @RequestParam Long productId,
            @RequestParam Long storeId,
            @RequestParam Integer quantity) {

        Inventory inventory = inventoryRepository
                .findByProduct_IdAndStore_Id(productId, storeId);

        if (inventory == null) {
            return false;
        }

        return inventory.getStockLevel() >= quantity;
    }
}
