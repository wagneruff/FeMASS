package femass.clinicahospitalar;

import Persistencia.PJason;
import Persistencia.PXml;
import Persistencia.SelectPersistencia;
import femass.clinicahospitalar.negocios.Medico;
import femass.clinicahospitalar.negocios.Genero;
import java.io.IOException;
import java.util.ArrayList;
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

public class MedConsultaController {

    // inclusão da tabela consulta 
    @FXML
    private TableView<Medico> tvConsultaMed;
    @FXML
    private TableColumn<Medico, String> tNome;
    @FXML
    private TableColumn<Medico, Long> tID;
    @FXML
    private TableColumn<Medico, String> tSetor;
    @FXML
    private TableColumn<Medico, Date> tDtNas;
    @FXML
    private TableColumn<Medico, Genero> tGenero;
    @FXML
    private TableColumn<Medico, Integer> tCargaHoraria;
    @FXML
    private TableColumn<Medico, String> tfRua;
    @FXML
    private TableColumn<Medico, String> tfBairro;
    @FXML
    private TableColumn<Medico, Integer> tfCep;
    @FXML
    private TableColumn<Medico, String> tfCidade;
    @FXML
    private TableColumn<Medico, String> tfEstado;
    @FXML
    private TableColumn<Medico, Integer> tfNumero;
    @FXML
    private TableColumn<Medico, String> tfTelefone;
    @FXML
    private TableColumn<Medico, String> tfCelular;
    @FXML
    private TableColumn<Medico, String> tfEmail;
    @FXML
    private TableColumn<Medico, String> tfCRM;
    @FXML
    private TableColumn<Medico, ArrayList> tfEspecialidade;
    @FXML
    private Button btDeletarE;
    @FXML
    private Button btEdtitarE;
    @FXML
    private Button btTelaInicial;

    private ObservableList<Medico> listaMedicos;

    // inicializa tabela
    @FXML
    public void initialize() {
        // Configura as colunas para usar os getters da classe Person
        tID.setCellValueFactory(new PropertyValueFactory<>("idmedico"));
        tfEspecialidade.setCellValueFactory(new PropertyValueFactory<>("areasEspecialidade"));
        tfCRM.setCellValueFactory(new PropertyValueFactory<>("numeroCRM"));
        tNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        tSetor.setCellValueFactory(new PropertyValueFactory<>("setor"));
        tDtNas.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
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
            PJason.carregarMedicos();
            listaMedicos = PJason.getListaMedicos();
            carregarMedicos(listaMedicos);
        } else {
            PXml.carregarMedicos();
            listaMedicos = PXml.getListaMedicos();
            carregarMedicos(listaMedicos);
        }

    }

    //tfEspecialidade.setCellValueFactory(new PropertyValueFactory<>("Especialidade"));
    public void carregarMedicos(ObservableList<Medico> lista) {
        listaMedicos = lista;
        tvConsultaMed.setItems(listaMedicos);
        System.out.println(listaMedicos);

    }

    @FXML
    private void deletarMedico() {
        Medico medicoSelecionado = tvConsultaMed.getSelectionModel().getSelectedItem();
        if (medicoSelecionado != null) {
            listaMedicos.remove(medicoSelecionado);
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um enfermeiro para deletar.");
        }
        tvConsultaMed.refresh();

        // Salva a lista atualizada em JSON
        PJason.salvarMedicos(listaMedicos);
        PXml.salvarMedicos(listaMedicos);
        mostrarAlerta("Sucesso", "Médico deletado com sucesso.");

    }

    @FXML
    private void editarMedico() {
        Medico medicoSelecionado = tvConsultaMed.getSelectionModel().getSelectedItem();
        if (medicoSelecionado != null) {
            TextInputDialog dialog = new TextInputDialog(medicoSelecionado.getNomeCompleto());
            dialog.setTitle("Editar Enfermeiro");
            dialog.setHeaderText("Edite o nome do enfermeiro");
            dialog.setContentText("Nome:");

            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(novoNome -> {
                medicoSelecionado.setNomeCompleto(novoNome);
                tvConsultaMed.refresh();  // Atualiza a tabela para refletir as alterações
                PJason.salvarMedicos(listaMedicos);
                PXml.salvarMedicos(listaMedicos);
            });
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um enfermeiro para editar.");
        }
    }

    @FXML
    private void cadastrarEspecialidade() {
        Medico medicoSelecionado = tvConsultaMed.getSelectionModel().getSelectedItem();
        if (medicoSelecionado != null) {
            // Cria o diálogo para inserir a especialidade
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Cadastrar Especialidade Médica");
            dialog.setHeaderText("Cadastrar uma nova especialidade");
            dialog.setContentText("Especialidade:");

            // Captura o resultado do diálogo
            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(especialidade -> {
                // Adiciona a especialidade ao médico selecionado
                medicoSelecionado.adicionarEspecialidade(especialidade);
                
                tvConsultaMed.refresh();  // Atualiza a tabela para refletir as alterações  
                
                PJason.salvarMedicos(listaMedicos);
                PXml.salvarMedicos(listaMedicos);
            });
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um médico para cadastrar a especialidade.");
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
    private void switchTelaInicial() throws IOException {
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
