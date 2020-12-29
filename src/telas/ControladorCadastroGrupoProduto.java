package telas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import avisos.Alertas;
import avisos.Restricoes;
import db.ExcecoesDB;
import entidades.excecoes.ValidacaoDeExcecao;
import entidades.negocio.GrupoProduto;
import entidades.servico.ServicoGrupoProduto;
import gui.ouvintes.AtualizaDadosLista;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utilitario.Utils;

public class ControladorCadastroGrupoProduto implements Initializable{
	
	private GrupoProduto entity;
	
	private ServicoGrupoProduto srvGrupoProduto;
	
	private List<AtualizaDadosLista> atualizaDadosLista = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtDescGrupo;
	
	@FXML
	private TextField txtGrupoPai;

	@FXML
	private Label labelErrortxtDescricao;
	
	@FXML
	private Label labelErrortxtDescGrupoPai;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;

	public void setServicoGrupoProduto (ServicoGrupoProduto srvGrupoProduto) {
		this.srvGrupoProduto = srvGrupoProduto;
	}
	
	public void sobrescreveAtualizaDadosLista (AtualizaDadosLista ouvintes) {
		atualizaDadosLista.add(ouvintes);
	}
	
	@FXML
	public void onBtSalvarAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalStateException("Entity está nulo");
		}
		if (srvGrupoProduto == null) {
			throw new IllegalStateException("srvGrupoProduto está nulo");
		}
		try {
			entity = getFormData();
			srvGrupoProduto.inserirOuAtualizarGrupoProduto(entity);
			notificaAtualizaDadosLista();
			Utils.currentStage(event).close();
		}
		catch (ValidacaoDeExcecao e) {
			setMensagemDeErros(e.getErros());
		}
		catch (ExcecoesDB e) {
			Alertas.showAlert("Erro ao salvar departamento", null, e.getMessage(), AlertType.ERROR);
		}
	}
	
	private void notificaAtualizaDadosLista() {
		for (AtualizaDadosLista x : atualizaDadosLista) {
			x.onAtualizaDados();
		}
	}
	
	private GrupoProduto getFormData() {
		GrupoProduto grp = new GrupoProduto();
		
		ValidacaoDeExcecao excecao = new ValidacaoDeExcecao("Validação de erro!");

		grp.setId(Utils.tryParseToInt(txtId.getText())); // Verifica se o campo está preenchido com número inteiro
		
		if (txtDescGrupo.getText() == null || txtDescGrupo.getText().trim().equals("")) { // trim = Para eliminar qualquer espaço em branco no inicio ou no final.
			excecao.addErros("nome", " O campo não pode estar vazio!");
		}
		
		grp.setDescGrupo(txtDescGrupo.getText().toUpperCase());
		
		grp.setGrupoPai(Utils.tryParseToInt(txtGrupoPai.getText())); // Verifica se o campo está preenchido com número inteiro
		
		if (excecao.getErros().size() > 0) { // Teste na coleção de erros, se há algum erro.
			throw excecao;
		}
		
		return grp;
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {	
		Utils.currentStage(event).close();
	}		
	
	public void setGrupoProduto (GrupoProduto entity) {
		this.entity = entity;
	}
	
	public void updateDados() {
		if (entity == null) {
			throw new IllegalStateException("GrupoProduto nulo!");
		}
		txtId.setText(String.valueOf(entity.getId()));
		try {
			entity.getDescGrupo1();
			String res = entity.getDescGrupo1().split("/")[1];
			txtDescGrupo.setText(res);
		} catch (NullPointerException e) {
			txtDescGrupo.setText(entity.getDescGrupo1());
		} catch (ArrayIndexOutOfBoundsException e) {
			txtDescGrupo.setText(entity.getDescGrupo1());
		}
		try {
			txtGrupoPai.setText(String.valueOf(entity.getGrupoPai1()));
		} catch (NullPointerException e) {
			txtGrupoPai.setText(String.valueOf(entity.getGrupoPai()));
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		Restricoes.setTextFieldInteger(txtId);
		Restricoes.setTextFieldMaxLength(txtDescGrupo, 30);
		Restricoes.setTextFieldInteger(txtGrupoPai);
	}
	
	private void setMensagemDeErros(Map<String, String> erros) { //Método para pegar os erros da exceção e anexar na tela
		Set<String> fields = erros.keySet();

		if (fields.contains("nome")) {
			labelErrortxtDescricao.setText(erros.get("nome"));
		}
		if (fields.contains("grupoPai")) {
			labelErrortxtDescGrupoPai.setText(erros.get("grupoPai"));
		}
	}
}
