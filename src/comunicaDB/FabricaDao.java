package comunicaDB;

import comunicaDB.acesso.GrupoProdutoDaoJDBC;
import comunicaDB.acesso.ProdutoDaoJDBC;
import db.DB;

public class FabricaDao {

	/* MACETE:
	 * A classe vai expor um método que
	 * retorna o tipo da interface
	 * mais internamente vai estânciar uma implementação.
	 * Para não precisar expor a implentação
	 * Deixa somente a interface.
	 */

	public static ProdutoDao cadastroProdutoDao() { 
		return new ProdutoDaoJDBC(DB.getConnection());
	}

	public static GrupoProdutoDao cadastroGrupoProdutoDao() { 
		return new GrupoProdutoDaoJDBC(DB.getConnection());
	}
}