package com.fs.productos.controller;

import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fs.productos.model.Producto;
import com.fs.productos.repository.ProductoRepository;
import com.fs.productos.service.ProductoService;



@RestController
@RequestMapping("/productos")
public class ProductoController {
    private static final Logger log = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;


    @GetMapping
    public List<Producto> getProductos(){
        log.info("GET/productos");
        log.info("Retornando todos los productos");
        return productoService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity <Object> getProductById(@PathVariable Long id) {
        Optional<Producto> prod = productoService.getProductoById(id);
        if(prod.isEmpty()){
            log.error("No se encontro producto con ID {}", id);
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro producto con ID " + id));
            //return ResponseEntity.notFound().build();
        }
        log.info("Producto encontrado con exito");
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Producto producto){
        Producto createdProduct = productoService.crearProducto(producto);
        if(createdProduct == null){
            log.error("Error al crear producto {}",producto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear producto")); 
        }
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Producto producto) {
        Producto updatedProduct = productoService.modificarProducto(id, producto);
        if(updatedProduct == null){
            log.error("Error al actualizar {}",producto);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al actualizar el producto id "+id)); 
        }
        return ResponseEntity.ok(updatedProduct);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        Optional<Producto> producto = productoService.getProductoById(id);
        if (producto.isEmpty()) {
            log.error("El producto con ID {} no existe", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró producto con ID " + id);
        }
    
        productoService.borrarProducto(id);
        log.info("Producto con ID {} eliminado correctamente", id);
        return ResponseEntity.ok("Producto con ID " + id + " eliminado correctamente");
    }

    static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message){
            this.message = message;
        }

        public String getMessage(){
            return message;
        }
    }
}
