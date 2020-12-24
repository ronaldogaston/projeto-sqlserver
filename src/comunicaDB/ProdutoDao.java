package comunicaDB;

import java.util.List;

import entidades.negocio.GrupoProduto;
import entidades.negocio.Produto;

public interface ProdutoDao {

	void insert (Produto obj); // Opera��o respons�vel para inserir dados no banco
	void update (Produto obj); // Opera��o respons�vel para atualizar dados no banco
	void deleteById (Integer id); // Opera��o respons�vel para deletar dados no banco
	Produto findById (Integer id); // Opera��o respons�vel para consultar o objeto no banco de dados
	List<Produto> findAll(); // Opera��o respos�vel para buscar todos os Produto do banco de dados
	List<Produto> findByGrupoProduto(GrupoProduto grupoProduto); // Busca Produto por Grupo de Produtos
}
