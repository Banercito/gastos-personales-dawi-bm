package com.cibertec.service;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cibertec.model.Transaccion;
import com.cibertec.model.Cuenta;
import com.cibertec.model.Categoria;
import com.cibertec.repository.TransaccionRepository;
import com.cibertec.repository.CuentaRepository;
import com.cibertec.repository.CategoriaRepository;

@Service
public class TransaccionService {
	
	//BANER MURGA & MARYTERE BENAVIDES
    
    @Autowired
    private TransaccionRepository transaccionRepository;
    
    @Autowired
    private CuentaRepository cuentaRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
 // Listar transacciones   
    public List<Transaccion> listarTransacciones() {
        return transaccionRepository.findByActivoTrue();
    }
 // Listar transacción por cuenta
    public List<Transaccion> listarTransaccionesPorCuenta(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findByIdAndActivoTrue(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));
        return transaccionRepository.findByCuentaAndActivoTrue(cuenta);
    }
    // Listar transacción por ID
    public Transaccion obtenerTransaccionPorId(Long id) {
        return transaccionRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
    }
    // Crear transacción
    

    @Transactional
    public Transaccion crearTransaccion(Long cuentaId, Long categoriaId, Transaccion transaccion) {
        Cuenta cuenta = cuentaRepository.findByIdAndActivoTrue(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        Categoria categoria = categoriaRepository.findByIdAndActivoTrue(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        // Validar tipo y saldo
        if ("Ingreso".equalsIgnoreCase(transaccion.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().add(transaccion.getMonto()));
        } else if ("Gasto".equalsIgnoreCase(transaccion.getTipo())) {
            if (cuenta.getSaldo().compareTo(transaccion.getMonto()) < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente para realizar el gasto.");
            }
            cuenta.setSaldo(cuenta.getSaldo().subtract(transaccion.getMonto()));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de transacción no válido. Use 'Ingreso' o 'Gasto'.");
        }

        cuentaRepository.save(cuenta);

        transaccion.setCuenta(cuenta);
        transaccion.setCategoria(categoria);
        transaccion.setActivo(true);

        return transaccionRepository.save(transaccion);
    }

   


 // Actualizar transacción
    @Transactional
    public Transaccion actualizarTransaccion(Long id, Transaccion transaccionActualizada) {
        Transaccion transaccion = obtenerTransaccionPorId(id);
        Cuenta cuenta = transaccion.getCuenta();
        
        // Revertir transacción anterior
        if ("Ingreso".equals(transaccion.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().subtract(transaccion.getMonto()));
        } else if ("Gasto".equals(transaccion.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().add(transaccion.getMonto()));
        }
        
        // Aplicar nueva transacción
        if ("Ingreso".equals(transaccionActualizada.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().add(transaccionActualizada.getMonto()));
        } else if ("Gasto".equals(transaccionActualizada.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().subtract(transaccionActualizada.getMonto()));
        }
        
        cuentaRepository.save(cuenta);
        
        transaccion.setMonto(transaccionActualizada.getMonto());
        transaccion.setDescripcion(transaccionActualizada.getDescripcion());
        transaccion.setFecha(transaccionActualizada.getFecha());
        transaccion.setTipo(transaccionActualizada.getTipo());
        
        return transaccionRepository.save(transaccion);
    }
    
 // Eliminar transacción
    @Transactional
    public void eliminarTransaccion(Long id) {
        Transaccion transaccion = obtenerTransaccionPorId(id);
        transaccion.setActivo(false); // Borrado lógico
        
        Cuenta cuenta = transaccion.getCuenta();
        
        // Revertir efecto de la transacción en el saldo
        if ("Ingreso".equals(transaccion.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().subtract(transaccion.getMonto()));
        } else if ("Gasto".equals(transaccion.getTipo())) {
            cuenta.setSaldo(cuenta.getSaldo().add(transaccion.getMonto()));
        }
        
        cuentaRepository.save(cuenta);
        transaccionRepository.save(transaccion);
    }
}