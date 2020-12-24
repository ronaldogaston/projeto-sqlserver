package comunicaDB;

import comunicaDB.acesso.GrupoProdutoDaoJDBC;
import comunicaDB.acesso.ProdutoDaoJDBC;
import db.DB;

public class FabricaDao {

	/* MACETE:
	 * A classe vai expor um m�todo que
	 * retorna o tipo da interface
	 * mais internamente vai est�nciar uma implementa��o.
	 * Para n�o precisar expor a implenta��o
	 * Deixa somente a interface.
	 */

	public static ProdutoDao cadastroProdutoDao() { 
		return new ProdutoDaoJDBC(DB.getConnection());
	}

	public static GrupoProdutoDao cadastroGrupoProdutoDao() { 
		return new GrupoProdutoDaoJDBC(DB.getConnection());
	}
}