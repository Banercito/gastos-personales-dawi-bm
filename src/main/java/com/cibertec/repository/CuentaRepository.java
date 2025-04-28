package com.cibertec.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.model.Cuenta;
import com.cibertec.model.Usuario;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
	
	//BANER MURGA & MARYTERE BENAVIDES
	
	
	
    List<Cuenta> findByUsuarioAndActivoTrue(Usuario usuario);
    List<Cuenta> findByActivoTrue();
    java.util.Optional<Cuenta> findByIdAndActivoTrue(Long id);
}