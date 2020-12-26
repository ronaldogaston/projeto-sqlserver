package utilitario;

import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;

public class Utils {

	public static Stage currentStage(ActionEvent event) { // Serve para deixar essa nova janela como principal, sem
															// podendo editar as demais telas se essa n�o for encerrada
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}

	public static Integer tryParseToInt(String str) { // Faz a leitura de uma 'caixa de texto' e obriga que seja n�meros
														// inteiros
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Double tryParseToDouble(String str) { // Faz a leitura de uma 'caixa de texto' e obriga que seja
														// n�meros
		// inteiros
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) { // M�todo
		// para
		// ponto
		// flutuante
		tableColumn.setCellFactory(column -> {
			TableCell<T, Double> cell = new TableCell<T, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						Locale.setDefault(Locale.US);
						setText(String.format("%." + decimalPlaces + "f", item));
					}
				}
			};
			return cell;
		});
	}

}