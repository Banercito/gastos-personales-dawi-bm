package com.cibertec.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Transaccion;
import com.cibertec.model.Cuenta;
import com.cibertec.model.Categoria;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
	
	//BANER MURGA & MARYTERE BENAVIDES
	
    List<Transaccion> findByCuentaAndActivoTrue(Cuenta cuenta);
    List<Transaccion> findByCategoriaAndActivoTrue(Categoria categoria);
    List<Transaccion> findByActivoTrue();
    java.util.Optional<Transaccion> findByIdAndActivoTrue(Long id);
}