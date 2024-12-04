package femass.clinicahospitalar;

import Persistencia.PJason;
import Persistencia.PXml;
import Persistencia.SelectPersistencia;
import femass.clinicahospitalar.negocios.Genero;
import femass.clinicahospitalar.negocios.Paciente;
import femass.clinicahospitalar.negocios.Responsavel;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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

public class PacConsultaController {

    // inclusão da tabela consulta 
    @FXML
    private TableView<Paciente> tvConsultaPac;
    @FXML
    private TableColumn<Paciente, String> tNome;
    @FXML
    private TableColumn<Paciente, Long> tID;
    @FXML
    private TableColumn<Paciente, Date> tDtNas;
    @FXML
    private TableColumn<Paciente, Genero> tGenero;
    @FXML
    private TableColumn<Paciente, String> tfRua;
    @FXML
    private TableColumn<Paciente, String> tfBairro;
    @FXML
    private TableColumn<Paciente, Integer> tfCep;
    @FXML
    private TableColumn<Paciente, String> tfCidade;
    @FXML
    private TableColumn<Paciente, String> tfEstado;
    @FXML
    private TableColumn<Paciente, Integer> tfNumero;
    @FXML
    private TableColumn<Paciente, String> tfTelefone;
    @FXML
    private TableColumn<Paciente, String> tfCelular;
    @FXML
    private TableColumn<Paciente, String> tfEmail;
    @FXML
    private TableColumn<Paciente, String> tcResponsavel;

    @FXML
    private TableView<Responsavel> tvResponsaveis;
    @FXML
    private TableColumn<Responsavel, String> tfNomeResp;
    @FXML
    private TableColumn<Responsavel, String> tfContatoResp;

    @FXML
    private Button btAdicionarResponsavel;
    @FXML
    private Button btDeletarE;
    @FXML
    private Button btEdtitarE;
    @FXML
    private Button btTelaInical;

    private ObservableList<Paciente> listaPacientes;

    @FXML
    public void initialize() {
        // Configura as colunas para usar os getters da classe Person
        tID.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        tNome.setCellValueFactory(new PropertyValueFactory<>("nomeCompleto"));
        tDtNas.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        tGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tfRua.setCellValueFactory(new PropertyValueFactory<>("rua"));
        tfBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tfCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));
        tfEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tfCep.setCellValueFactory(new PropertyValueFactory<>("cep"));
        tfNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tfCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tfEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tfTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tfNomeResp.setCellValueFactory(new PropertyValueFactory<>("nomeResponsavel"));
        tfContatoResp.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        tvConsultaPac.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            carregarResponsaveis(newValue); // Carrega os responsáveis do paciente selecionado
        });

        tcResponsavel.setCellValueFactory(cellData -> {
            Paciente paciente = cellData.getValue();
            List<Responsavel> responsaveis = paciente.getContatoResponsavel();

            if (responsaveis == null || responsaveis.isEmpty()) {
                return new SimpleStringProperty("Sem responsável");
            }

            // Transforma a lista de responsáveis em uma string de nomes concatenados
            String nomesResponsaveis = responsaveis.stream()
                    .map(Responsavel::getNomeResponsavel) // Supondo que `Responsavel` tenha o método `getNomeCompleto()`
                    .collect(Collectors.joining(", "));

            // Retorna o valor como uma SimpleStringProperty para a coluna
            return new SimpleStringProperty(nomesResponsaveis);
        });

        if (SelectPersistencia.getMetodoPersistencia().equals("JSON")) {
            PJason.carregarPacientes();
            listaPacientes = PJason.getListaPacientes();
            carregarPacientes(listaPacientes);
        } else {
            PXml.carregarPacientes();
            listaPacientes = PXml.getListaPacientes();
            carregarPacientes(listaPacientes);
        }
       
    }
    //tfEspecialidade.setCellValueFactory(new PropertyValueFactory<>("Especialidade"));

    public void carregarPacientes(ObservableList<Paciente> lista) {
        listaPacientes = lista;
        tvConsultaPac.setItems(listaPacientes);
        System.out.println(listaPacientes);

    }

    @FXML
    private void carregarResponsaveis(Paciente pacienteSelecionado) {
        if (pacienteSelecionado != null) {
            ObservableList<Responsavel> responsaveis = FXCollections.observableArrayList(pacienteSelecionado.getContatoResponsavel());
            tvResponsaveis.setItems(responsaveis);
        } else {
            tvResponsaveis.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void mostrarResponsaveisDoPaciente() {
        Paciente pacienteSelecionado = tvConsultaPac.getSelectionModel().getSelectedItem();

        if (pacienteSelecionado != null) {
            // Obtém a lista de responsáveis do paciente selecionado
            ObservableList<Responsavel> responsaveis = FXCollections.observableArrayList(pacienteSelecionado.getContatoResponsavel());

            // Atualiza a TableView
            tvResponsaveis.setItems(responsaveis);
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um paciente para ver os responsáveis.");
        }
    }

    @FXML
    private void adicionarResponsavelAoPaciente() {
        // Obter o paciente selecionado na TableView
        Paciente pacienteSelecionado = tvConsultaPac.getSelectionModel().getSelectedItem();

        if (pacienteSelecionado != null) {
            // Capturar os dados do novo responsável (você pode substituir isso por um formulário)
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Adicionar Responsável");
            dialog.setHeaderText("Informe o nome do responsável");
            dialog.setContentText("Nome:");
            Optional<String> nomeResponsavelOpt = dialog.showAndWait();

            if (nomeResponsavelOpt.isPresent() && !nomeResponsavelOpt.get().isEmpty()) {
                String nomeResponsavel = nomeResponsavelOpt.get();

                // Capturar outras informações do responsável (telefone, e-mail, etc.)
                TextInputDialog telefoneDialog = new TextInputDialog();
                telefoneDialog.setTitle("Adicionar Responsável");
                telefoneDialog.setHeaderText("Informe o telefone do responsável");
                telefoneDialog.setContentText("Telefone:");
                Optional<String> telefoneResponsavelOpt = telefoneDialog.showAndWait();

                if (telefoneResponsavelOpt.isPresent() && !telefoneResponsavelOpt.get().isEmpty()) {
                    String telefoneResponsavel = telefoneResponsavelOpt.get();

                    // Criar um novo responsável
                    Responsavel novoResponsavel = new Responsavel(
                            System.currentTimeMillis(), // Gerar um ID único
                            nomeResponsavel,
                            telefoneResponsavel,
                            null, // Você pode capturar o celular em outro diálogo, se necessário
                            null // Você pode capturar o e-mail em outro diálogo, se necessário
                    );

                    // Adicionar o responsável ao paciente
                    pacienteSelecionado.adicionarResponsavel(novoResponsavel);

                    // Atualizar a TableView
                    tvConsultaPac.refresh();

                    // Salvar as alterações nos arquivos
                    PJason.salvarPacientes(listaPacientes);
                    PXml.salvarPacientes(listaPacientes);

                    mostrarAlerta("Sucesso", "Responsável adicionado com sucesso.");
                } else {
                    mostrarAlerta("Erro", "Telefone do responsável não foi informado.");
                }
            } else {
                mostrarAlerta("Erro", "Nome do responsável não foi informado.");
            }
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um paciente para adicionar um responsável.");
        }
    }

    @FXML
    private void deletarPaciente() {
        Paciente pacienteSelecionado = tvConsultaPac.getSelectionModel().getSelectedItem();
        if (pacienteSelecionado != null) {
            listaPacientes.remove(pacienteSelecionado);
        } else {
            mostrarAlerta("Seleção Inválida", "Por favor, selecione um enfermeiro para deletar.");
        }
        tvConsultaPac.refresh();
        PJason.salvarPacientes(listaPacientes);
        PXml.salvarPacientes(listaPacientes);
        mostrarAlerta("Sucesso", "Paciente deletado com sucesso.");
    }
  
    
    @FXML
    private void editarPaciente() {
        Paciente pacienteSelecionado = tvConsultaPac.getSelectionModel().getSelectedItem();
        if (pacienteSelecionado != null) {
            TextInputDialog dialog = new TextInputDialog(pacienteSelecionado.getNomeCompleto());
            dialog.setTitle("Editar Paciente");
            dialog.setHeaderText("Edite o nome do Paciente");
            dialog.setContentText("Nome:");

            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(novoNome -> {
                pacienteSelecionado.setNomeCompleto(novoNome);
                tvConsultaPac.refresh();  // Atualiza a tabela para refletir as alterações
                PJason.salvarPacientes(listaPacientes);
                PXml.salvarPacientes(listaPacientes);
                mostrarAlerta("Sucesso", "Paciente atualizado.");

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
    private void switchTelaInicial() throws IOException {
        try {
            // Carregar a tela inicial
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaInicial.fxml"));
            Parent root = loader.load();

            // Obtenha a Stage atual da tela de consulta e feche-a
            Stage currentStage = (Stage) btTelaInical.getScene().getWindow(); // Use um componente qualquer da tela de consulta
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
