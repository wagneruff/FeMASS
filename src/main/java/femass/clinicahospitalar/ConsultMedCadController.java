package femass.clinicahospitalar;

import Persistencia.PJason;
import Persistencia.PXml;
import femass.clinicahospitalar.negocios.ConsultaMedica;
import femass.clinicahospitalar.negocios.Medico;
import femass.clinicahospitalar.negocios.Paciente;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConsultMedCadController {

    @FXML
    private Button btTelaInicial;
    @FXML
    private Button btDeletar;
    @FXML
    private Button btEdtitar;
    @FXML
    private Button btCadastrar;
    @FXML
    private ComboBox<String> cbIDMedico;
    @FXML
    private ComboBox<String> cbIDPaciente;
    @FXML
    private CheckBox checkNao;
    @FXML
    private CheckBox checkSim;
    @FXML
    private TextField tfDiagnostico;
    @FXML
    private TextField tfExameQueixa;
    @FXML
    private TextField tfIdConsulta;
    @FXML
    private TextField tfPescricao;

    public static ObservableList<ConsultaMedica> listaConsultasMedicas = FXCollections.observableArrayList();
    private ObservableList<Medico> listaMedicos;
    private ObservableList<Paciente> listaPacientes;
    

    @FXML
    public void initialize() {
        PJason.carregarMedicos();
        PJason.carregarPacientes();
        PJason.carregarConsultaMed();
        listaConsultasMedicas = PJason.getListaConsultaMed();

        listaMedicos = PJason.getListaMedicos();
        listaPacientes = PJason.getListaPacientes();

        System.out.println("Lista de Médicos: " + listaMedicos);
        System.out.println("Lista de Pacientes: " + listaPacientes);

        // lvPacientes.setItems(listaPacientes); 
        for (Paciente paciente : listaPacientes) {
            cbIDPaciente.getItems().add(paciente.getIdPaciente() + " - " + paciente.getNomeCompleto()); // Adiciona os IDs dos médicos

        }

        // Supondo que você tenha listas de objetos Paciente e Medico chamadas listaPacientes e listaMedicos
        for (Medico medico : listaMedicos) {
            cbIDMedico.getItems().add(medico.getIdmedico() + " - " + medico.getNomeCompleto()); // Adiciona os IDs dos médicos
        }

        long novoId = gerarNovoIdConsultaMed();
        tfIdConsulta.setText(String.valueOf(novoId));
        tfIdConsulta.setEditable(false);
    }

    @FXML
    public void cadastrarConsultaMedica() {

        try {

            String selectedPaciente = cbIDPaciente.getValue();
            String selectedMedico = cbIDMedico.getValue();

            if (selectedPaciente == null || selectedMedico == null) {
                System.out.println("Nenhum paciente ou médico selecionado!");
                return; // Pode exibir uma mensagem de erro ao usuário
            }

            //Extrai o ID da String selecionada (assumindo que a String está no formato "ID - Nome")          
            Long idPaciente = Long.valueOf(selectedPaciente.split(" - ")[0].trim());
            Long idMedico = Long.valueOf(selectedMedico.split(" - ")[0].trim());

            //Long idConsulta = Long.valueOf(tfIdConsulta.getText());
            Long idConsulta = gerarNovoIdConsultaMed();
            String exameQueixa = tfExameQueixa.getText();
            String diagnostico = tfDiagnostico.getText();
            String prescricao = tfPescricao.getText();
            boolean indicacaoCirurgica = checkSim.isSelected(); // CheckBox para indicar cirurgia

            // Verificações adicionais e validações podem ser implementadas aqui
            // Criar objeto de ConsultaMedica
            ConsultaMedica consultaMedica = new ConsultaMedica(
                    idConsulta, idPaciente, idMedico, exameQueixa, diagnostico, prescricao, indicacaoCirurgica
            );

            // Adicionar a consulta à lista ou repositório
            listaConsultasMedicas.add(consultaMedica); // listaConsultas deve ser uma lista que você mantém para exibir ou salvar consultas
            
            PJason.salvarConsultaMed(listaConsultasMedicas);
            PXml.salvarConsultaMed(listaConsultasMedicas);
            
            System.out.println("Consulta mèdica cadastrada com sucesso");
            /*
            // Procurar o paciente na lista de pacientes e adicionar a consulta ao histórico
            Paciente pacienteEncontrado = null;
            for (Paciente paciente : listaPacientes) {
                if (paciente.getIdPaciente().equals(idPaciente)) {
                    pacienteEncontrado = paciente;
                    break;
                }
            }*/

            //    limparCamposConsulta();
        } catch (NumberFormatException e) {
            System.out.println("Erro de formatação de número: " + e.getMessage());
            // Mostre uma mensagem de erro para o usuário (opcional)
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar consulta médica: " + e.getMessage());
            // Mostre uma mensagem de erro para o usuário (opcional)
        }
    }

    private long gerarNovoIdConsultaMed() {
        return listaConsultasMedicas.stream()
                .mapToLong(ConsultaMedica::getIdConsulta)
                .max()
                .orElse(0) + 1; // Retorna 1 se a lista estiver vazia
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
