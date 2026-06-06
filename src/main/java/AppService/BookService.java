package AppService;

import Infra.Entities.Book;
import Repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> listarTodos() {
        return repository.findAll();
    }

    // Para a funcionalidade UPDATE do CRUD
    public void atualizarLivro(Long id, String nome, String autor, String ano, String descricao) {
        repository.findById(id).ifPresent(livro -> {
            livro.setNome(nome);
            livro.setAutor(autor);
            livro.setAno(ano);
            livro.setDescricao(descricao);
            repository.save(livro);
        });
    }

    // Para o Dashboard
    public long contarLivros() {
        return repository.count();
    }

    public void salvarLivro(String nome, String autor, String ano, String descricao, String caminhoImagem) {
        Book livro = new Book();
        livro.setNome(nome);
        livro.setAutor(autor);
        livro.setAno(ano);
        livro.setDescricao(descricao);
        livro.setCaminhoImagem(caminhoImagem);
        repository.save(livro);
    }
    
    // Método para o botão de "Excluir" funcionar de verdade
    public void deletarLivro(Long id) {
        repository.deleteById(id);
    }
}