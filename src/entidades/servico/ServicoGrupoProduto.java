package entidades.servico;
import java.util.List;

import comunicaDB.FabricaDao;
import comunicaDB.GrupoProdutoDao;
import entidades.negocio.GrupoProduto;

public class ServicoGrupoProduto {
	
	private GrupoProdutoDao dao = FabricaDao.cadastroGrupoProdutoDao();

	public List<GrupoProduto> findAll(){
		return dao.findAll();
	}

	public void inserirOuAtualizarGrupoProduto(GrupoProduto grp) {
		if (grp.getId() == null) {
			dao.insert(grp);
		}
		else {
			dao.update(grp);
		}
	}	
}
