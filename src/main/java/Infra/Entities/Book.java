package Infra.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books")
@Data // O Lombok já cria os Getters e Setters para nós
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String autor;
    private String ano;
    private String descricao;
    private String caminhoImagem;
}