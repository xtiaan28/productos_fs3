package com.fs.productos.service;

import java.util.List;
import java.util.Optional;

import com.fs.productos.model.Producto;

public interface ProductoService {
    
    List<Producto> getAllProducts();
    
    Optional<Producto> getProductoById(Long id);

    Producto crearProducto(Producto producto);
    Producto modificarProducto(Long id, Producto producto);
    void borrarProducto(Long id);
}
