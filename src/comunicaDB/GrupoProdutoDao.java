package comunicaDB;

import java.util.List;

import entidades.negocio.GrupoProduto;

public interface GrupoProdutoDao {

	void insert (GrupoProduto obj); // Operação responsável para inserir dados no banco
	void update (GrupoProduto obj); // Operação responsável para atualizar dados no banco
	void deleteById (Integer id); // Operação responsável para deletar dados no banco
	GrupoProduto findById (Integer id); // Operação responsável para consultar o objeto no banco de dados
	List<GrupoProduto> findAll(); // Operação resposável para buscar todos os Grupo Produto do banco de dados
}
