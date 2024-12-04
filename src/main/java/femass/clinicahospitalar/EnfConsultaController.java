package femass.clinicahospitalar;

import femass.clinicahospitalar.negocios.Enfermeiro;
import femass.clinicahospitalar.negocios.Genero;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Persistencia.PJason;
import Persistencia.PXml;
import Persistencia.SelectPersistencia;

public class EnfConsultaController {

    // inclusão da tabela consulta 
    @FXML
    private TableView<Enfermeiro> tConsultaEnf;
    @FXML
    private TableColumn<Enfermeiro, String> tNome;
    @FXML
    private TableColumn<Enfermeiro, Long> tID;
    @FXML
    private TableColumn<Enfermeiro, String> tSetor;
    @FXML
    private TableColumn<Enfermeiro, Date> tDtNas;
    @FXML
    private TableColumn<Enfermeiro, Genero> tGenero;
    @FXML
    private TableColumn<Enfermeiro, Boolean> tOpRX;
    @FXML
    private TableColumn<Enfermeiro, Integer> tCargaHoraria;
    @FXML
    private TableColumn<Enfermeiro, String> tfRua;
    @FXML
    private TableColumn<Enfermeiro, String> tfBairro;
    @FXML
    private TableColumn<Enfermeiro, Integer> tfCep;
    @FXML
    private TableColumn<Enfermeiro, String> tfCidade;
    @FXML
    private TableColumn<Enfermeiro, String> tfEstado;
    @FXML
    private TableColumn<Enfermeiro, Integer> tfNumero;
    @FXML
    private TableColumn<Enfermeiro, String> tfTelefone;
    @FXML
    private TableColumn<Enfermeiro, String> tfCelular;
    @FXML
    private TableColumn<Enfermeiro, String> tfEmail;

    @FXML
    private Button btDeletarE;
    @FXML
    private Button btEdtitarE;
    @FXML
    private Button btTelaInicial;

    private ObservableList<Enfermeiro> listaEnfermeiros;

    // inicializa tabela
    @FXML
    public void initialize() {
        // Configura as colunas para usar os getters da classe Person
        tNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        tID.setCellValueFactory(new PropertyValueFactory<>("idEnfermeiro"));
        tSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        tDtNas.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tOpRX.setCellValueFactory(new PropertyValueFactory<>("treinadoOpRX"));
        tCargaHoraria.setCellValueFactory(new PropertyValueFactory<>("chSemanal"));
        tfRua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        tfBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tfCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tfEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tfCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        tfNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tfCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tfEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tfTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        if (SelectPersistencia.getMetodoPersistencia().equals("JSON")) {
            PJason.carregarEnfermeiros();
            listaEnfermeiros = PJason.getListaEnfermeiros();
            carregarEnfermeiros(listaEnfermeiros);
        }else{
            PXml.carregarEnfermeiros();
            listaEnfermeiros=PXml.getListaEnfermeiros();
            carregarEnfermeiros(listaEnfermeiros);          
           
        }

        

    }

    public void carregarEnfermeiros(ObservableList<Enfermeiro> lista) {
        listaEnfermeiros = lista;
        tConsultaEnf.setItems(listaEnfermeiros);
        System.out.println(listaEnfermeiros);

    }

    @FXML
    private void deletarEnfermeiro() {
        Enfermeiro enfermeiroSelecionado = tConsultaEnf.getSelectionModel().getSelectedItem();
        if (enfermeiroSelecionado != null) {
            listaEnfermeiros.remove(enfermeiroSelecionado);
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um enfermeiro para deletar.");
        }
        tConsultaEnf.refresh();
        
        PJason.salvarEnfermeiros(listaEnfermeiros);
        PXml.salvarEnfermeiros(listaEnfermeiros);
        mostrarAlerta("Sucesso", "Enfermeiro deletado com sucesso.");
    }

    @FXML
    private void editarEnfermeiro() {
        Enfermeiro enfermeiroSelecionado = tConsultaEnf.getSelectionModel().getSelectedItem();
        if (enfermeiroSelecionado != null) {
            TextInputDialog dialog = new TextInputDialog(enfermeiroSelecionado.getNomeCompleto());
            dialog.setTitle("Editar Enfermeiro");
            dialog.setHeaderText("Edite o nome do enfermeiro");
            dialog.setContentText("Nome:");

            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(novoNome -> {
                enfermeiroSelecionado.setNomeCompleto(novoNome);
                tConsultaEnf.refresh();  // Atualiza a tabela para refletir as alterações
                PJason.salvarEnfermeiros(listaEnfermeiros);
                PXml.salvarEnfermeiros(listaEnfermeiros);
                mostrarAlerta("Sucesso", "Enfermeiro atualizado.");
            });
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um enfermeiro para editar.");
        }
    }

    // Método para exibir alertas de erro ou informação
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    private void switchTelaInicial1() throws IOException {
        try {
            // Carregar a tela inicial
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaInicial.fxml"));
            Parent root = loader.load();

            // Obtenha a Stage atual da tela de consulta e feche-a
            Stage currentStage = (Stage) btTelaInicial.getScene().getWindow(); // Use um componente qualquer da tela de consulta
            currentStage.close(); // Fecha a Stage atual (tela de consulta)

            // Abra a nova cena em uma nova Stage ou na mesma Stage (reutilizando)
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
