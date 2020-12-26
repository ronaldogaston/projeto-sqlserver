package telas;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import avisos.Alertas;
import avisos.Restricoes;
import db.ExcecoesDB;
import entidades.excecoes.ValidacaoDeExcecao;
import entidades.negocio.GrupoProduto;
import entidades.negocio.Produto;
import entidades.servico.ServicoGrupoProduto;
import entidades.servico.ServicoProduto;
import gui.ouvintes.AtualizaDadosLista;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import utilitario.Utils;

public class ControladorCadastroProduto implements Initializable{

		private Produto entity;

		private ServicoProduto srvProduto;
		
		private ServicoGrupoProduto srvGrupoProduto;

		private List<AtualizaDadosLista> atualizaDadosLista = new ArrayList<>();

		@FXML
		private TextField txtId;

		@FXML
		private TextField txtCodigo;
		
		@FXML
		private TextField txtDescProd;
		
		@FXML
		private TextField txtPreco;
		
		@FXML
		private ComboBox<GrupoProduto> comboBoxGrupoProduto;

		@FXML
		private Label labelErrorCodigo;
		
		@FXML
		private Label labelErrorDescProd;
		
		@FXML
		private Label labelErrorPreco;
		
		@FXML
		private Label labelErrorGrupoProduto;

		@FXML
		private Button btSalvar;

		@FXML
		private Button btCancelar;
		
		private ObservableList<GrupoProduto> obsList;

		public void setProduto (Produto entity) {
			this.entity = entity;
		}

		public void setServicos(ServicoProduto srvProduto, ServicoGrupoProduto srvGrupoProduto) {
			this.srvProduto = srvProduto;
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
			if (srvProduto == null) {
				throw new IllegalStateException("srvProduto está nulo");
			}
			try {
				entity = getFormData();
				srvProduto.inserirOuAtualizarProduto(entity);
				notificaAtualizaDadosLista();
				Utils.currentStage(event).close();
			}
			catch (ValidacaoDeExcecao e) {
				setMensagemDeErros(e.getErros());
			}
			catch (ExcecoesDB e) {
				Alertas.showAlert("Erro ao salvar Grupo Produto", null, e.getMessage(), AlertType.ERROR);
			}
		}

		private void notificaAtualizaDadosLista() {
			for (AtualizaDadosLista x : atualizaDadosLista) {
				x.onAtualizaDados();
			}
		}

		private Produto getFormData() {
			Produto prod = new Produto();

			ValidacaoDeExcecao excecao = new ValidacaoDeExcecao("Validação de erro!");

			prod.setId(Utils.tryParseToInt(txtId.getText())); // Verifica se o campo está preenchido com número inteiro
			prod.setCodigo(Utils.tryParseToInt(txtCodigo.getText()));
			
			if (txtDescProd.getText() == null || txtDescProd.getText().trim().equals("")) { // trim = Para eliminar qualquer espaço em branco no inicio ou no final.
				excecao.addErros("descricao", " O campo não pode estar vazio!");
			}
			prod.setDescProd(txtDescProd.getText());
			prod.setPreco(Utils.tryParseToDouble(txtPreco.getText()));
			
			prod.setGrupoProduto(comboBoxGrupoProduto.getValue());
			
			if (excecao.getErros().size() > 0) { // Teste na coleção de erros, se há algum erro.
				throw excecao;
			}

			return prod;
		}

		@FXML
		public void onBtCancelarAction(ActionEvent event) {
			Utils.currentStage(event).close();
		}

		@Override
		public void initialize(URL url, ResourceBundle rb) {
			initializeNodes();
		}

		private void initializeNodes() {
			Restricoes.setTextFieldInteger(txtId);
			Restricoes.setTextFieldInteger(txtCodigo);
			Restricoes.setTextFieldMaxLength(txtDescProd, 30);
			Restricoes.setTextFieldDouble(txtPreco);
			
			initializeComboBoxGrupoProduto();
		}

		public void updateDados() {
			if (entity == null) {
				throw new IllegalStateException("Produto nulo!");
			}
			txtId.setText(String.valueOf(entity.getId()));
			txtCodigo.setText(String.valueOf(entity.getCodigo()));
			txtDescProd.setText(entity.getDescProd());
			Locale.setDefault(Locale.US);
			txtPreco.setText(String.format("%.2f", entity.getPreco()));
			if (entity.getGrupoProduto() == null) {
				comboBoxGrupoProduto.getSelectionModel().selectFirst();
			} else {
				comboBoxGrupoProduto.setValue(entity.getGrupoProduto());
			}
		}

		private void setMensagemDeErros(Map<String, String> erros) { //Método para pegar os erros da exceção e anexar na tela
			Set<String> fields = erros.keySet();

			labelErrorCodigo.setText((fields.contains("codigo") ? erros.get("codigo") : ""));
			labelErrorDescProd.setText((fields.contains("descProd") ? erros.get("descProd") : ""));
			labelErrorPreco.setText((fields.contains("preco") ? erros.get("preco") : ""));
			labelErrorGrupoProduto.setText((fields.contains("grupoProduto") ? erros.get("grupoProduto") : ""));
		}
		
		public void carregarObjAssociados() {
			if (srvGrupoProduto == null) {
				throw new IllegalStateException("Serviço de GrupoProduto é nulo"); // Programação defensiva - Injeção de
																					// Dependência faltando
			}
			List<GrupoProduto> list = srvGrupoProduto.findAll();
			obsList = FXCollections.observableArrayList(list);
			comboBoxGrupoProduto.setItems(obsList);
		}
		
		private void initializeComboBoxGrupoProduto() {
			Callback<ListView<GrupoProduto>, ListCell<GrupoProduto>> factory = lv -> new ListCell<GrupoProduto>() {
				@Override
				protected void updateItem(GrupoProduto item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.getDescGrupo());
				}
			};
			comboBoxGrupoProduto.setCellFactory(factory);
			comboBoxGrupoProduto.setButtonCell(factory.call(null));
		}
		
}
