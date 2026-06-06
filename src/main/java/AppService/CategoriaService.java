package AppService;

import Infra.Entities.Categoria;
import Repository.CategoriaDAO;
import java.util.List;

public class CategoriaService {
    private final CategoriaDAO categoriaDAO;

    public CategoriaService(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public List<Categoria> listarTodas() {
        return categoriaDAO.listarTodas();
    }

    public void salvar(String nome) {
        Categoria cat = new Categoria();
        cat.setNome(nome);
        categoriaDAO.salvar(cat);
    }

    public void atualizar(Long id, String novoNome) {
        categoriaDAO.atualizar(id, novoNome);
    }

    public void deletar(Long id) {
        categoriaDAO.deletar(id);
    }
}