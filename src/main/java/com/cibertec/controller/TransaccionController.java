package com.cibertec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cibertec.model.Transaccion;
import com.cibertec.service.TransaccionService;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
	
	//BANER MURGA & MARYTERE BENAVIDES
    
    @Autowired
    private TransaccionService transaccionService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'AUDITORIA')")
    public ResponseEntity<List<Transaccion>> listarTransacciones() {
        return ResponseEntity.ok(transaccionService.listarTransacciones());
    }
    
    @GetMapping("/cuenta/{cuentaId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'AUDITORIA')")
    public ResponseEntity<List<Transaccion>> listarTransaccionesPorCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(transaccionService.listarTransaccionesPorCuenta(cuentaId));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'AUDITORIA')")
    public ResponseEntity<Transaccion> obtenerTransaccion(@PathVariable Long id) {
        return ResponseEntity.ok(transaccionService.obtenerTransaccionPorId(id));
    }
    
    @PostMapping("/cuenta/{cuentaId}/categoria/{categoriaId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Transaccion> crearTransaccion(
            @PathVariable Long cuentaId,
            @PathVariable Long categoriaId,
            @RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(transaccionService.crearTransaccion(cuentaId, categoriaId, transaccion));
    }
    

    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUDITORIA')")
    public ResponseEntity<Transaccion> actualizarTransaccion(@PathVariable Long id, @RequestBody Transaccion transaccion) {
        return ResponseEntity.ok(transaccionService.actualizarTransaccion(id, transaccion));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUDITORIA')")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable Long id) {
        transaccionService.eliminarTransaccion(id);
        return ResponseEntity.ok().build();
    }
}