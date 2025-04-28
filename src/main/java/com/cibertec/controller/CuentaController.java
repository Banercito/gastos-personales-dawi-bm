package com.cibertec.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.cibertec.model.Cuenta;
import com.cibertec.service.CuentaService;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
	
	//BANER MURGA & MARYTERE BENAVIDES
    
    @Autowired
    private CuentaService cuentaService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'AUDITORIA')")
    public ResponseEntity<List<Cuenta>> listarCuentas() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'AUDITORIA')")
    public ResponseEntity<List<Cuenta>> listarCuentasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.listarCuentasPorUsuario(usuarioId));
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'USUARIO', 'AUDITORIA')")
    public ResponseEntity<Cuenta> obtenerCuenta(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerCuentaPorId(id));
    }
    
    @PostMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<Cuenta> crearCuenta(@PathVariable Long usuarioId, @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.crearCuenta(usuarioId, cuenta));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUDITORIA')")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(id, cuenta));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'AUDITORIA')")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.ok().build();
    }
}