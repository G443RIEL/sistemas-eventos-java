package sistemaseventos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class MainFX extends Application {
    private SistemaEventos sistema = new SistemaEventos();
    @Override
    public void start(Stage stage) {
        sistema.carregarEventosDeArquivo();

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 20; -fx-background-color: #f4f4f4;");

        Label titulo = new Label("Sistema de Eventos");
        titulo.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        ListView<String> listaEventos = new ListView<>();
        atualizarLista(listaEventos);
        
        Button btnNovo = new Button("Novo Evento");
        btnNovo.setOnAction(_ -> mostrarDialogoNovoEvento(listaEventos));

        root.getChildren().addAll(titulo, listaEventos, btnNovo);

        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.setTitle("Eventos");
        stage.show();
    }

    private void atualizarLista(ListView<String> lista) {
        lista.getItems().clear();
        for (Evento e : sistema.getEventos()) {
            lista.getItems().add(e.getNome() + " - " + e.getHorario());
        }
    }

    private void mostrarDialogoNovoEvento(ListView<String> listaEventos) {
        Dialog<Evento> dialog = new Dialog<>();
        dialog.setTitle("Novo Evento");

        GridPane grid = new GridPane();
        grid.setVgap(10); grid.setHgap(10);

        TextField nome = new TextField();
        TextField endereco = new TextField();
        TextField categoria = new TextField();
        TextField descricao = new TextField();
        TextField data = new TextField("2025-08-27T19:00");

        grid.add(new Label("Nome:"), 0, 0); grid.add(nome, 1, 0);
        grid.add(new Label("Endereço:"), 0, 1); grid.add(endereco, 1, 1);
        grid.add(new Label("Categoria:"), 0, 2); grid.add(categoria, 1, 2);
        grid.add(new Label("Descrição:"), 0, 3); grid.add(descricao, 1, 3);
        grid.add(new Label("Data e Hora:"), 0, 4); grid.add(data, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(btn -> {
            if (btn == ButtonType.OK) {
                try {
                    return new Evento(
                        sistema.getEventos().size() + 1,
                        nome.getText(),
                        endereco.getText(),
                        categoria.getText(),
                        LocalDateTime.parse(data.getText()),
                        descricao.getText()
                    );
                } catch (DateTimeParseException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Data e hora inválidas! Use o formato: yyyy-MM-ddTHH:mm (exemplo: 2025-08-27T19:00)");
                    alert.showAndWait();
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(evento -> {
            sistema.cadastrarEvento(evento);
            sistema.salvarEventosNoArquivo();
            atualizarLista(listaEventos);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}