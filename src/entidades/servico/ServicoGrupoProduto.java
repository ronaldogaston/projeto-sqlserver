package entidades.servico;
import java.util.ArrayList;
import java.util.List;

import entidades.negocio.GrupoProduto;

public class ServicoGrupoProduto {

	public List<GrupoProduto> findAll(){
		List<GrupoProduto> list = new ArrayList<>();
		list.add(new GrupoProduto(1, "BEBIDAS"));
		list.add(new GrupoProduto(2, "LANCHES"));
		list.add(new GrupoProduto(3, "DOCES"));
		return list;
	}

}
