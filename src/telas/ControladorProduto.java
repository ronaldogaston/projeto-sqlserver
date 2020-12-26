package telas;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import avisos.Alertas;
import db.DbIntegrityException;
import entidades.negocio.Produto;
import entidades.servico.ServicoProduto;
import gui.ouvintes.AtualizaDadosLista;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utilitario.Utils;

public class ControladorProduto implements Initializable, AtualizaDadosLista {

		private ServicoProduto service; // Injetar a depeência sem colocar a implementação 'new ServicoProduto'

		@FXML
		private TableView<Produto> tableViewProduto; // Tipo TableView

		@FXML
		private TableColumn<Produto, Integer> colunaId; // Tipo Coluna. OBS: Lembrando que só declarar o mesmo não faz
																// com que funcione. Verifique o método 'initialize (URL
																// uri, ResourceBundle rb)'

		@FXML
		private TableColumn<Produto, Integer> colunaCodigo; // Tipo Coluna. OBS: Lembrando que só declarar o mesmo não
																// faz com que funcione. Verifique o método 'initialize (URL
																// uri, ResourceBundle rb)'
		@FXML
		private TableColumn<Produto, String> colunaDescProd; 
		
		@FXML
		private TableColumn<Produto, Double> colunaPreco; 
		
		@FXML
		private TableColumn<Produto, String> colunaGrupoProduto; 
		
		@FXML
		private TableColumn<Produto, Produto> colunaEditar; // Tipo Coluna. OBS: Lembrando que só declarar o mesmo
																		// não faz com que funcione. Verifique o método
																		// 'initialize (URL uri, ResourceBundle rb)'

		@FXML
		private TableColumn<Produto, Produto> colunaRemove;

		@FXML
		private Button btNew; // Tipo Botão

		private ObservableList<Produto> obsList;

		@FXML
		public void onBtNewAction(ActionEvent event) { // Ação que ocorrerá após o botão 'Novo' ser clicado -- (ActionEvent
														// event) Para ter referencia do controle que receber o evento
			Stage parentStage = Utils.currentStage(event);
			Produto prod = new Produto();
			cadastroDialogoFormulario(prod, "/telas/ProdutoLista.fxml", parentStage); // Chamada do método de formulário de
																						// cadastro
		}

		public void setServicoProduto(ServicoProduto service) {
			this.service = service;
		}

		@Override
		public void initialize(URL uri, ResourceBundle rb) {
			InitializeNodes(); // Macete para funcionar um componente da tela. Veja o método
								// 'InitializeNodes()'
		}

		private void InitializeNodes() {
			colunaId.setCellValueFactory(new PropertyValueFactory<>("id")); // Comando para iniciar apropriadamento o
																			// comportamento da coluna na tabela
			colunaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo")); // Comando para iniciar apropriadamento o
																				// comportamento da coluna na tabela
			colunaDescProd.setCellValueFactory(new PropertyValueFactory<>("descProd"));
			
			colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
			Utils.formatTableColumnDouble(colunaPreco, 2);
			
			colunaGrupoProduto.setCellValueFactory(new PropertyValueFactory<>("grupoProduto"));

			/*
			 * Comando para a tabela acompanhar a altura da janela ABAIXO
			 */

			Stage stage = (Stage) Main.getMainScene().getWindow();
			tableViewProduto.prefHeightProperty().bind(stage.heightProperty());
		}

		public void updateTableView() {
			if (service == null) {
				throw new IllegalStateException("Serviço está nulo.");
			}
			List<Produto> list = service.findAll();
			obsList = FXCollections.observableArrayList(list);
			tableViewProduto.setItems(obsList);
			inicBotaoEditar();
			inicBotaoRemove();
		}

		private void cadastroDialogoFormulario(Produto prod, String nomeAbsoluto, Stage parentStage) { // Janela de
//																											// Diálogo
//			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource(nomeAbsoluto)); // Padrão do método '
//																							// (getClass().getResource(nomeAbsoluto))
//																							// '
//				Pane pane = loader.load();
	//
//				ProdutoFormController controller = loader.getController();
//				controller.setProduto(dep);
//				controller.setServicoProduto(new ServicoProduto());
//				controller.sobrescreveAtualizaDadosLista(this); // Cadeia de chamadas até o método 'onAtualizaDados'
//				controller.updateDados();
	//
//				Stage dialogoStage = new Stage();
	//
//				dialogoStage.setTitle("Informe os dados do departamento");
//				dialogoStage.setScene(new Scene(pane)); // Chamada de nova janela (janela filho) que irá sobrepor a anterior
//				dialogoStage.setResizable(false); // Faz com que a tela NÃO possa ser máximizada/minimizada (Redimencionada)
//				dialogoStage.initOwner(parentStage); // Chamada da janela pai da janela filho
//				dialogoStage.initModality(Modality.WINDOW_MODAL); // Bloqueia o acesso as telas de fundos até que janela
//																	// filho tenha sido finalizada com sucesso
//				dialogoStage.showAndWait();
	//
//				/*
//				 * Função para chamar a Janela do formulário de dialogo Para preencher o novo
//				 * departamento
//				 */
//			} catch (IOException e) {
//				Alertas.showAlert("IO Exception", "Erro ao carrega janela", e.getMessage(), AlertType.ERROR);
//			}
		}

		@Override
		public void onAtualizaDados() {
			updateTableView();
		}

		private void inicBotaoEditar() {
			colunaEditar.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			colunaEditar.setCellFactory(param -> new TableCell<Produto, Produto>() {
				private final Button button = new Button("Editar");

				@Override
				protected void updateItem(Produto obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(event -> cadastroDialogoFormulario(obj, "/telas/ProdutoLista.fxml",
							Utils.currentStage(event)));
				}
			});
		}

		private void inicBotaoRemove() {
			colunaRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
			colunaRemove.setCellFactory(param -> new TableCell<Produto, Produto>() {
				private final Button button = new Button("remove");

				@Override
				protected void updateItem(Produto obj, boolean empty) {
					super.updateItem(obj, empty);
					if (obj == null) {
						setGraphic(null);
						return;
					}
					setGraphic(button);
					button.setOnAction(event -> removeEntity(obj));
				}
			});
		}

		private void removeEntity(Produto obj) {
			Optional<ButtonType> result = Alertas.showConfirmation("Confirmação", "Tem certeza que deseja excluir ?");
			//Optional é o objeto que carrega outro objeto podendo estar presente ou não.
			if (result.get() == ButtonType.OK) { // result.get = Para poder acessar esse outro objeto.
				if (service == null) { //Teste se o programador esqueceu de 'injetar' a dependencia
					throw new IllegalStateException("Serviço está nulo");
				}
				try {
					service.remove(obj);
					updateTableView(); // Força atualização dos dados da tabela.
				}
				catch (DbIntegrityException e) {
					Alertas.showAlert("Erro ao tentar remover !", null, e.getMessage(), AlertType.ERROR);
				}
			}
		}
}
