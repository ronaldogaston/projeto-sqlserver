package comunicaDB;

import java.util.List;

import entidades.negocio.GrupoProduto;
import entidades.negocio.Produto;

public interface ProdutoDao {

	void insert (Produto obj); // Operação responsável para inserir dados no banco
	void update (Produto obj); // Operação responsável para atualizar dados no banco
	void deleteById (Integer id); // Operação responsável para deletar dados no banco
	Produto findById (Integer id); // Operação responsável para consultar o objeto no banco de dados
	List<Produto> findAll(); // Operação resposável para buscar todos os Produto do banco de dados
	List<Produto> findByGrupoProduto(GrupoProduto grupoProduto); // Busca Produto por Grupo de Produtos
}
