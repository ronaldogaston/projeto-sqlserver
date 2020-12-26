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

import comunicaDB.GrupoProdutoDao;
import db.DB;
import db.DbIntegrityException;
import db.ExcecoesDB;
import entidades.negocio.GrupoProduto;

public class GrupoProdutoDaoJDBC implements GrupoProdutoDao {

	private Connection conn;

	public GrupoProdutoDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(GrupoProduto grupoProduto) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("INSERT INTO GrupoProduto " + "(grpDescGrupo) " + "VALUES " + "(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, grupoProduto.getDescGrupo());

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					grupoProduto.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new ExcecoesDB("Erro inesperado, nehuma linha afetada!");
			}
		} catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(GrupoProduto grupoProduto) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("UPDATE GrupoProduto " + "SET grpDescGrupo = ? " + "WHERE idGrupoProduto = ?");

			st.setString(1, grupoProduto.getDescGrupo());
			st.setInt(2, grupoProduto.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;

		try {
			st = conn.prepareStatement("DELETE FROM GrupoProduto WHERE idGrupoProduto = ?");

			st.setInt(1, id);

			int linhasAfetadas = st.executeUpdate();

			if (linhasAfetadas == 0) {
				throw new ExcecoesDB("Nenhuma linha foi afetada.");
			} else {
				System.out.println("Deletado com sucesso!");
			}
		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public GrupoProduto findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * " + "FROM GrupoProduto " + "WHERE idGrupoProduto = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				GrupoProduto grp = instanciaGrupoProduto(rs);
				return grp;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private GrupoProduto instanciaGrupoProduto(ResultSet rs) throws SQLException {
		GrupoProduto grp = new GrupoProduto();
		grp.setId(rs.getInt("idGrupoProduto"));
		grp.setDescGrupo(rs.getString("grpDescGrupo"));
		return grp;
	}

	@Override
	public List<GrupoProduto> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * " + "FROM GrupoProduto " + "ORDER BY idGrupoProduto");

			rs = st.executeQuery();

			List<GrupoProduto> list = new ArrayList<>();
			Map<Integer, GrupoProduto> map = new HashMap<>(); // Será guardado qualquer grupoProduto instaciado

			while (rs.next()) {

				GrupoProduto grp = map.get(rs.getInt("idGrupoProduto")); // Busca o ID do GrupoProduto do Banco

				if (grp == null) { // Realiza a instanciação caso o 'grp' tenha vindo como nulo acima
					grp = instanciaGrupoProduto(rs);
					map.put(rs.getInt("idGrupoProduto"), grp);
				}
				list.add(grp);
			}
			return list;
		} catch (SQLException e) {
			throw new ExcecoesDB(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
