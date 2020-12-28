package entidades.negocio;

import java.io.Serializable;
import java.util.Date;

public class GrupoProduto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String descGrupo;
	private Integer grupoPai;
	private Date sysRegistro;
	private Date sysAltRegistro;

	public GrupoProduto() {
	}

	public GrupoProduto(Integer id, String descGrupo, Integer grupoPai, Date sysRegistro, Date sysAltRegistro) {
		super();
		this.id = id;
		this.descGrupo = descGrupo;
		this.grupoPai = grupoPai;
		this.sysRegistro = sysRegistro;
		this.sysAltRegistro = sysAltRegistro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescGrupo1() {
		return descGrupo;
	}

	public String getDescGrupo() {
		if (getGrupoPai() == 0 || getGrupoPai() == null) {
			return descGrupo;
		} else {
			return "	  " + descGrupo;
		}
	}

	public void setDescGrupo(String descGrupo) {
		this.descGrupo = descGrupo;
	}

	public Integer getGrupoPai1() {
		if (getGrupoPai() == 0 || getGrupoPai() == null) {
			return null;
		} else {
			return grupoPai;
		}
	}

	public Integer getGrupoPai() {
		return grupoPai;
	}

	public void setGrupoPai(Integer grupoPai) {
		this.grupoPai = grupoPai;
	}

	public Date getSysRegistro() {
		return sysRegistro;
	}

	public void setSysRegistro(Date sysRegistro) {
		this.sysRegistro = sysRegistro;
	}

	public Date getSysAltRegistro() {
		return sysAltRegistro;
	}

	public void setSysAltRegistro(Date sysAltRegistro) {
		this.sysAltRegistro = sysAltRegistro;
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
			//if (getGrupoPai() == 0 || getGrupoPai() == null) {
				return "[" + id + "] " 
						+ getDescGrupo1();
			/*} else {
				return  "[" + getGrupoPai() + "]"
						//+ getDescGrupo1() 
						+ " / " 
						+ "[" + id + "] " 
						+ getDescGrupo1();
			}*/
	}
}
