package com.cibertec.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias")
public class Categoria {
	
	//BANER MURGA & MARYTERE BENAVIDES
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private String tipo; // Ingreso o Gasto
    private boolean activo; // Para el borrado lógico

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "categoria") // Lado gestionado, se serializa
    private List<Transaccion> transacciones;
}