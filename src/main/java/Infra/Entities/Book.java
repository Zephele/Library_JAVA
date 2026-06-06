package Infra.Entities;

import lombok.Data;

@Data // O Lombok já cria os Getters e Setters para nós
public class Book {

    private Long    id;
    private String  nome;
    private String  autor;
    private String  ano;
    private String  descricao;
    private String  caminhoImagem;
}