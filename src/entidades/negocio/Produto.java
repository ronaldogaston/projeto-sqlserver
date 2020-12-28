package entidades.negocio;

import java.io.Serializable;
import java.util.Date;

public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Integer codigo;
	private String descProd;
	private Double preco;
	private Date sysRegistro;
	private Date sysAltRegistro;
	
	private GrupoProduto grupoProduto;

	public Produto() {
	}

	public Produto(Integer id, Integer codigo, String descProd, Double preco, GrupoProduto grupoProduto, Date sysRegistro, Date sysAltRegistro) {
		this.id = id;
		this.codigo = codigo;
		this.descProd = descProd;
		this.preco = preco;
		this.grupoProduto = grupoProduto;
		this.sysRegistro = sysRegistro;
		this.sysAltRegistro = sysAltRegistro;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescProd() {
		return descProd;
	}

	public void setDescProd(String descProd) {
		this.descProd = descProd;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public GrupoProduto getGrupoProduto() {
		return grupoProduto;
	}

	public void setGrupoProduto(GrupoProduto grupoProduto) {
		this.grupoProduto = grupoProduto;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produto [id=" + id + ", codigo=" + codigo + ", descProd=" + descProd + ", preco=" + String.format("2.f%", preco)
				+ ", grupoProduto=" + grupoProduto + "]";
	}
}
