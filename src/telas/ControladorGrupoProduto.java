package telas;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import avisos.Alertas;
import entidades.negocio.GrupoProduto;
import entidades.servico.ServicoGrupoProduto;
import gui.ouvintes.AtualizaDadosLista;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilitario.Utils;

public class ControladorGrupoProduto implements Initializable, AtualizaDadosLista {

	private ServicoGrupoProduto service; // Injetar a depe�ncia sem colocar a implementa��o 'new ServicoGrupoProduto'

	@FXML
	private TableView<GrupoProduto> tableViewGrupoProduto; // Tipo TableView

	@FXML
	private TableColumn<GrupoProduto, Integer> colunaId; // Tipo Coluna. OBS: Lembrando que s� declarar o mesmo n�o faz
															// com que funcione. Verifique o m�todo 'initialize (URL
															// uri, ResourceBundle rb)'

	@FXML
	private TableColumn<GrupoProduto, Integer> colunaDescGrupo; // Tipo Coluna. OBS: Lembrando que s� declarar o mesmo
																// n�o faz com que funcione. Verifique o m�todo
																// 'initialize (URL uri, ResourceBundle rb)'
	
	@FXML
	private TableColumn<GrupoProduto, GrupoProduto> colunaEditar; // Tipo Coluna. OBS: Lembrando que s� declarar o mesmo

	@FXML
	private Button btNew; // Tipo Bot�o

	private ObservableList<GrupoProduto> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) { // A��o que ocorrer� ap�s o bot�o 'Novo' ser clicado -- (ActionEvent event) Para ter referencia do controle que receber o evento
		Stage parentStage = Utils.currentStage(event);
		GrupoProduto grp = new GrupoProduto();
		cadastroDialogoFormulario(grp, "/telas/GrupoProdutoCadastro.fxml", parentStage); // Chamada do m�todo de formul�rio de cadastro
	}

	public void setServicoGrupoProduto(ServicoGrupoProduto service) {
		this.service = service;
	}

	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		InitializeNodes(); // Macete para funcionar um componente da tela. Veja o m�todo
							// 'InitializeNodes()'
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Servi�o est� nulo.");
		}
		List<GrupoProduto> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewGrupoProduto.setItems(obsList);
		inicBotaoEditar();
	}

	private void InitializeNodes() {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id")); // Comando para iniciar apropriadamento o
		colunaDescGrupo.setCellValueFactory(new PropertyValueFactory<>("descGrupo")); // Comando para iniciar apropriadamento o comportamento da coluna na tabela

		/*
		 * Comando para a tabela acompanhar a altura da janela ABAIXO
		 */

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewGrupoProduto.prefHeightProperty().bind(stage.heightProperty());
	}

	private void cadastroDialogoFormulario(GrupoProduto grp, String nomeAbsoluto, Stage parentStage) { // Janela de Di�logo
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto)); // Padr�o do m�todo // (getClass().getResource(nomeAbsoluto))
			Pane pane = loader.load();

			ControladorCadastroGrupoProduto controller = loader.getController();
			controller.setGrupoProduto(grp);
			controller.setServicoGrupoProduto(new ServicoGrupoProduto());
			controller.sobrescreveAtualizaDadosLista(this); // Cadeia de chamadas at� o m�todo 'onAtualizaDados'
			controller.updateDados();
			
			Stage dialogoStage = new Stage();

			dialogoStage.setTitle("Informe os dados do GrupoProduto");
			dialogoStage.setScene(new Scene(pane)); // Chamada de nova janela (janela filho) que ir� sobrepor a anterior
			dialogoStage.setResizable(false); // Faz com que a tela N�O possa ser m�ximizada/minimizada (Redimencionada)
			dialogoStage.initOwner(parentStage); // Chamada da janela pai da janela filho
			dialogoStage.initModality(Modality.WINDOW_MODAL); // Bloqueia o acesso as telas de fundos at� que janela
			dialogoStage.showAndWait(); // filho tenha sido finalizada com sucesso

			/*
			 * Fun��o para chamar a Janela do formul�rio de dialogo				
			 * Fun��o para chamar a Janela do formul�rio de dialogo Para preencher o novo
			 * Para preencher o novo departamento				 
			 * departamento
			 */			
		} 
		catch (IOException e) {
			Alertas.showAlert("IO Exception", "Erro ao carrega janela", e.getMessage(), AlertType.ERROR);
		}
	}
	
	@Override
	public void onAtualizaDados() {
		updateTableView();
	}
	
	private void inicBotaoEditar() {
		colunaEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		colunaEditar.setCellFactory(param -> new TableCell<GrupoProduto, GrupoProduto>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(GrupoProduto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> cadastroDialogoFormulario(obj, "/telas/GrupoProdutoCadastro.fxml", Utils.currentStage(event)));
			}
		});
	}
}