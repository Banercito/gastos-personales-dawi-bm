package com.cibertec.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.Categoria;
import com.cibertec.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	//BANER MURGA & MARYTERE BENAVIDES
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findByActivoTrue();
    }
    
    public List<Categoria> listarCategoriasPorTipo(String tipo) {
        return categoriaRepository.findByTipoAndActivoTrue(tipo);
    }
    
    public Categoria obtenerCategoriaPorId(Long id) {
        return categoriaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
    
    public Categoria crearCategoria(Categoria categoria) {
        categoria.setActivo(true);
        return categoriaRepository.save(categoria);
    }
    
    public Categoria actualizarCategoria(Long id, Categoria categoriaActualizada) {
        Categoria categoria = obtenerCategoriaPorId(id);
        
        categoria.setNombre(categoriaActualizada.getNombre());
        categoria.setDescripcion(categoriaActualizada.getDescripcion());
        categoria.setTipo(categoriaActualizada.getTipo());
        
        return categoriaRepository.save(categoria);
    }
    
    public void eliminarCategoria(Long id) {
        Categoria categoria = obtenerCategoriaPorId(id);
        categoria.setActivo(false); // Borrado lógico
        categoriaRepository.save(categoria);
    }
}