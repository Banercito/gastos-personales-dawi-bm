package com.cibertec.service;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.model.Cuenta;
import com.cibertec.model.Usuario;
import com.cibertec.repository.CuentaRepository;
import com.cibertec.repository.UsuarioRepository;

@Service
public class CuentaService {
	
	//BANER MURGA & MARYTERE BENAVIDES
    
    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Cuenta> listarCuentas() {
        return cuentaRepository.findByActivoTrue();
    }
    
    public List<Cuenta> listarCuentasPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return cuentaRepository.findByUsuarioAndActivoTrue(usuario);
    }
    
    public Cuenta obtenerCuentaPorId(Long id) {
        return cuentaRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
    }
    
    public Cuenta crearCuenta(Long usuarioId, Cuenta cuenta) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        cuenta.setUsuario(usuario);
        cuenta.setActivo(true);
        cuenta.setSaldo(BigDecimal.ZERO); // <-- saldo inicial en cero

        return cuentaRepository.save(cuenta);
    }

    
    public Cuenta actualizarCuenta(Long id, Cuenta cuentaActualizada) {
        Cuenta cuenta = obtenerCuentaPorId(id);
        
        cuenta.setNombre(cuentaActualizada.getNombre());
        cuenta.setSaldo(cuentaActualizada.getSaldo());
        cuenta.setTipoCuenta(cuentaActualizada.getTipoCuenta());
        
        return cuentaRepository.save(cuenta);
    }
    
    public void eliminarCuenta(Long id) {
        Cuenta cuenta = obtenerCuentaPorId(id);
        cuenta.setActivo(false); // Borrado lógico
        cuentaRepository.save(cuenta);
    }
}