package comunicaDB.acesso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import comunicaDB.ProdutoDao;
import db.DB;
import db.ExcecoesDB;
import entidades.negocio.GrupoProduto;
import entidades.negocio.Produto;

public class ProdutoDaoJDBC implements ProdutoDao{

	private Connection conn;

	public ProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Produto produto) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(
					"INSERT INTO produto "
					+ "(codigo, descProd, preco, idGrupoProduto) "
					+ "VALUES "
					+ "(?, ?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, produto.getCodigo());
			st.setString(2, produto.getDescProd());
			st.setDouble(3, produto.getPreco());
			st.setInt(4, produto.getGrupoProduto().getId());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					produto.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new ExcecoesDB("Erro inesperado, nehuma linha afetada!");
			}
		}
		catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Produto produto) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement(
					"UPDATE Produto "
					+ "SET codigo = ?, descProd = ?, preco = ?, idGrupoProduto = ? "
					+ "WHERE Id = ?");

			st.setInt(1, produto.getCodigo());
			st.setString(2, produto.getDescProd());
			st.setDouble(3, produto.getPreco());
			st.setInt(4, produto.getGrupoProduto().getId());

			st.executeUpdate();
		}
		catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM Produto WHERE idProduto = ?");

			st.setInt(1, id);

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas == 0) {
				throw new ExcecoesDB("Nenhuma linha foi afetada.");
			}
			else {
				System.out.println("Deletado com sucesso!");
			}
		}
		catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Produto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT Produto.*, GrupoProduto.DescGrupo as Desc_Grupo "
					+ "FROM Produto INNER JOIN GrupoProduto "
					+ "ON Produto.IdGrupoProduto "
					+ "WHERE Produto.proGrupoProduto = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				GrupoProduto grp = instanciaGrupoProduto(rs);
				Produto produto = instanciaProduto(rs, grp);
				return produto;
			}
			else {
				return null;
			}
		}
		catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Produto instanciaProduto(ResultSet rs, GrupoProduto grp) throws SQLException {
		Produto produto = new Produto();
		produto.setId(rs.getInt("idProduto"));
		produto.setDescProd(rs.getString("descProd"));
		produto.setGrupoProduto(grp);
		return produto;
	}

	private GrupoProduto instanciaGrupoProduto(ResultSet rs) throws SQLException {
		GrupoProduto grp = new GrupoProduto();
		grp.setId(rs.getInt("idGrupoProduto"));
		grp.setDescGrupo(rs.getString("DescGrupo"));
		return grp;
	}

	@Override
	public List<Produto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT Produto.*, GrupoProduto.DescNome as Desc_Grupo "
					+ "FROM produto INNER JOIN GrupoProduto "
					+ "ON Produto.proGrupoProduto = GrupoProduto.idGrupoProduto "
					+ "ORDER BY DescProd");

			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();
			Map<Integer, GrupoProduto> map = new HashMap<>(); // Será guardado qualquer grpartamento instaciado

			while (rs.next()) {

				GrupoProduto grp = map.get(rs.getInt("idGrupoProduto")); // Busca o ID do GrupoProduto do Banco

				if (grp == null) { // Realiza a instanciação caso o 'grp' tenha vindo como nulo acima
					grp = instanciaGrupoProduto(rs);
					map.put(rs.getInt("idGrupoProduto"), grp);
				}
				Produto produto = instanciaProduto(rs, grp);
				list.add(produto);
			}
			return list;
		}
		catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Produto> findByGrupoProduto(GrupoProduto grpGrupoProduto) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT Produto.*, GrupoProduto.descGrupo as Desc_Grupo "
					+ "FROM Produto INNER JOIN GrupoProduto "
					+ "ON Produto.proGrupoProduto = GrupoProduto.idGrupoProduto "
					+ "WHERE GrupoProduto.idGrupoProduto = ? "
					+ "ORDER BY DescProd");

			st.setInt(1, grpGrupoProduto.getId());
			rs = st.executeQuery();

			List<Produto> list = new ArrayList<>();
			Map<Integer, GrupoProduto> map = new HashMap<>(); // Será guardado qualquer GrupoProduto instaciado

			while (rs.next()) {

				GrupoProduto grp = map.get(rs.getInt("idGrupoProduto")); // Busca o ID do GrupoProduto do Banco

				if (grp == null) { // Realiza a instanciação caso o 'grp' tenha vindo como nulo acima
					grp = instanciaGrupoProduto(rs);
					map.put(rs.getInt("idGrupoProduto"), grp);
				}
				Produto produto = instanciaProduto(rs, grp);
				list.add(produto);
			}
			return list;
		}
		catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
