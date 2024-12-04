package femass.clinicahospitalar;

import Persistencia.PJason;
import Persistencia.PXml;
import Persistencia.SelectPersistencia;
import femass.clinicahospitalar.negocios.ConsultaMedica;
import femass.clinicahospitalar.negocios.Medico;
import femass.clinicahospitalar.negocios.Paciente;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ConsultaMedicaController {

    @FXML
    private TableView<ConsultaMedica> tvConsultaMed;
    @FXML
    private TableColumn<ConsultaMedica, Long> tfIDConsulta;
    @FXML
    private TableColumn<ConsultaMedica, String> tfNomePaciente;
    @FXML
    private TableColumn<ConsultaMedica, String> tfNomeMedico;
    @FXML
    private TableColumn<ConsultaMedica, Boolean> tfIndCirurgica;
    @FXML
    private TableColumn<ConsultaMedica, String> tfExameQueixa;
    @FXML
    private TableColumn<ConsultaMedica, String> tfDiagnostico;
    @FXML
    private TableColumn<ConsultaMedica, String> tfPrescricao;
    @FXML
    private Button btEditar;
    @FXML
    private Button btTelaInicial;
    @FXML
    private Button btDeletar;

    public ObservableList<ConsultaMedica> listaConsultasMedicas;
    private ObservableList<Medico> listaMedicos;
    private ObservableList<Paciente> listaPacientes;

    @FXML
    public void initialize() {
        tfIDConsulta.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        tfNomePaciente.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        tfNomeMedico.setCellValueFactory(new PropertyValueFactory<>("idMedico"));
        tfIndCirurgica.setCellValueFactory(new PropertyValueFactory<>("indicacaoCirurgica"));
        tfExameQueixa.setCellValueFactory(new PropertyValueFactory<>("exameQueixa"));
        tfDiagnostico.setCellValueFactory(new PropertyValueFactory<>("diagnostico"));
        tfPrescricao.setCellValueFactory(new PropertyValueFactory<>("prescricao"));

        listaMedicos = PJason.getListaMedicos();
        listaPacientes = PJason.getListaPacientes();// Configuração das colunas de nomes
        tfNomePaciente.setCellValueFactory(cellData -> {
            Long idPaciente = cellData.getValue().getIdPaciente();
            String nomePaciente = listaPacientes.stream()
                    .filter(p -> p.getIdPaciente().equals(idPaciente))
                    .map(Paciente::getNomeCompleto)
                    .findFirst()
                    .orElse("Desconhecido");
            return new SimpleStringProperty(nomePaciente);
        });

        tfNomeMedico.setCellValueFactory(cellData -> {
            Long idMedico = cellData.getValue().getIdMedico();
            String nomeMedico = listaMedicos.stream()
                    .filter(m -> m.getIdmedico().equals(idMedico))
                    .map(Medico::getNomeCompleto)
                    .findFirst()
                    .orElse("Desconhecido");
            return new SimpleStringProperty(nomeMedico);
        });

        if (SelectPersistencia.getMetodoPersistencia().equals("JSON")) {
            PJason.carregarConsultaMed();
            listaConsultasMedicas = PJason.getListaConsultaMed();
            carregarConsultaMed(listaConsultasMedicas);
        } else {
            PXml.carregarConsultaMed();
            listaConsultasMedicas = PXml.getListaConsultaMed();
            carregarConsultaMed(listaConsultasMedicas);

        }
       
    }

    public void carregarConsultaMed(ObservableList<ConsultaMedica> lista) {
        listaConsultasMedicas = lista;
        tvConsultaMed.setItems(listaConsultasMedicas);
        System.out.println(listaConsultasMedicas);

    }

    @FXML
    private void switchTelaInicial(ActionEvent event) {
        try {
            // Carregar a tela inicial
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaInicial.fxml"));
            Parent root = loader.load();

            // Obter a Stage atual e substituir a Scene
            Stage currentStage = (Stage) tvConsultaMed.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show(); // Certifique-se de que a Stage seja exibida

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ConsultaMedica> getListaConsultasMedicas() {
        return listaConsultasMedicas;
    }

    public void setListaConsultasMedicas(ObservableList<ConsultaMedica> listaConsultasMedicas) {
        listaConsultasMedicas = listaConsultasMedicas;
    }

    @FXML
    private void deletarConsultaMed() {
        ConsultaMedica csel = tvConsultaMed.getSelectionModel().getSelectedItem();
        if (csel != null) {
            listaConsultasMedicas.remove(csel);
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione uma consulta para deletar.");
        }
        tvConsultaMed.refresh();
        PJason.salvarConsultaMed(listaConsultasMedicas);
        PXml.salvarConsultaMed(listaConsultasMedicas);
        
        mostrarAlerta("Sucesso", "Consulta deletada com sucesso.");
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

}
