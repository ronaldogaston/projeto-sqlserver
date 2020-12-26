package entidades.servico;

import java.util.List;

import comunicaDB.FabricaDao;
import comunicaDB.ProdutoDao;
import entidades.negocio.Produto;

public class ServicoProduto {
	
	private ProdutoDao dao = FabricaDao.cadastroProdutoDao();

	public List<Produto> findAll(){
		return dao.findAll();
	}

	public void inserirOuAtualizarProduto(Produto prod) {
		if (prod.getId() == null) {
			dao.insert(prod);
		}
		else {
			dao.update(prod);
		}
	}

	public void remove(Produto prod) {
		dao.deleteById(prod.getId());
	}

}
