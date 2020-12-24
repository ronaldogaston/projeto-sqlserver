package entidades.negocio;

import java.io.Serializable;

public class GrupoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descGrupo;
	
	public GrupoProduto() {
	}

	public GrupoProduto(Integer id, String descGrupo) {
		super();
		this.id = id;
		this.descGrupo = descGrupo;
	}

	public Integer getId() {
		return id;
	}

	public String getDescGrupo() {
		return descGrupo;
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoProduto other = (GrupoProduto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GrupoProduto [id=" + id + ", descGrupo=" + descGrupo + "]";
	}
}
