package com.fs.productos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fs.productos.model.Producto;
import com.fs.productos.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements ProductoService{

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<Producto> getAllProducts() {
        return productoRepository.findAll();
    }

    @Override
    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }
    @Override
    public Producto crearProducto(Producto producto) {

        return productoRepository.save(producto);
    }

    @Override
    public Producto modificarProducto(Long id, Producto producto) {
        return productoRepository
        .findById(id)
        .map(prod -> {
            prod.setNombreProducto(producto.getNombreProducto());
            prod.setCantidad(producto.getCantidad());
            prod.setPrecioCosto(producto.getPrecioCosto());
            prod.setPrecioVenta(producto.getPrecioVenta());
            prod.setCategoria(producto.getCategoria());
          return productoRepository.save(prod);
        })
        .orElseThrow(() -> new RuntimeException("No se encontró el producto " + id));
    }

    public void borrarProducto(Long id){
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
          } else {
            throw new RuntimeException("No se encontró el producto: " + id);
          }
    }
    
}
