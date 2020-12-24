package comunicaDB;

import java.util.List;

import entidades.negocio.GrupoProduto;

public interface GrupoProdutoDao {

	void insert (GrupoProduto obj); // Opera��o respons�vel para inserir dados no banco
	void update (GrupoProduto obj); // Opera��o respons�vel para atualizar dados no banco
	void deleteById (Integer id); // Opera��o respons�vel para deletar dados no banco
	GrupoProduto findById (Integer id); // Opera��o respons�vel para consultar o objeto no banco de dados
	List<GrupoProduto> findAll(); // Opera��o respos�vel para buscar todos os Grupo Produto do banco de dados
}
