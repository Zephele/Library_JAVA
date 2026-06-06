package Infra.Entities;

import lombok.Data;

@Data
public class Book {

    private Long    id;
    private String  nome;
    private String  autor;
    private String  ano;
    private String  descricao;
    private String  caminhoImagem;
}