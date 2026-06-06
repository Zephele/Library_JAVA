package AppService;

import Infra.Entities.Book;
import Repository.BookDAO;
import java.util.List;

public class BookService {

    private final BookDAO bookDAO;

    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> listarTodos() {
        return bookDAO.listarTodos();
    }

    public void salvarLivro(String nome, String autor, String ano, String descricao, String caminhoImagem) {
        Book livro = new Book();
        livro.setNome(nome);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setDescricao(descricao);
        livro.setCaminhoImagem(caminhoImagem);
        
        bookDAO.salvarLivro(livro);
    }

    public void atualizarLivro(Long id, String nome, String autor, String ano, String descricao, String caminhoImagem) {
        bookDAO.atualizarLivro(id, nome, autor, ano, descricao, caminhoImagem);
    }

    public void deletarLivro(Long id) {
        bookDAO.deletarLivro(id);
    }

    public long contarLivros() {
        return bookDAO.contarLivros();
    }
}