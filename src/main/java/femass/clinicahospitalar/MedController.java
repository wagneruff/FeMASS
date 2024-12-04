package femass.clinicahospitalar;

import Persistencia.PJason;
import Persistencia.PXml;
import Persistencia.SelectPersistencia;
import femass.clinicahospitalar.negocios.ContatoTelEmail;
import femass.clinicahospitalar.negocios.Endereco;
import femass.clinicahospitalar.negocios.Genero;
import femass.clinicahospitalar.negocios.Medico;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MedController {

    @FXML
    private TextField tbNome;
    @FXML
    private TextField tbDataNascimento;
    @FXML
    private TextField tbSetor;
    @FXML
    private TextField tbCargaHoraria;
    @FXML
    private ComboBox<String> cbGenero;
    @FXML
    private TextField tfBairro;
    @FXML
    private TextField tfCEP;
    @FXML
    private TextField tfCidade;
    @FXML
    private TextField tfEstado;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfRua;
    @FXML
    private TextField tfCelular;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfCirurgiao;
    @FXML
    private TextField tfEspecialidadae;
    @FXML
    private TextField tfCRM;
    @FXML
    private TextField tbIdMedico;

    @FXML
    private Button btConsultarEnf;
    @FXML
    private Button btGravar;
    @FXML
    private Button btCarregarList;
    @FXML
    private Button btTelaInicial;

    @FXML
    public void initialize() {
        // Adiciona os valores do enum Genero à ComboBox cbGenero
        for (Genero genero : Genero.values()) {
            cbGenero.getItems().add(genero.name());
        }
        
        if (SelectPersistencia.getMetodoPersistencia().equals("JSON")) {
             PJason.carregarMedicos();
        listaMedicos = PJason.getListaMedicos();
        } else {
            PXml.carregarMedicos();
            listaMedicos = PXml.getListaMedicos();         
        }
       

        long novoId = gerarNovoIdMedico();
        tbIdMedico.setText(String.valueOf(novoId));
        tbIdMedico.setEditable(false);
    }

    public ObservableList<Medico> listaMedicos = FXCollections.observableArrayList();
    //public ArrayList<Enfermeiro> listaEnfermeiros2 = new ArrayList<>();

    //@FXML;
    public void cadastrarMedico() throws ParseException {
        try {
            // Captura dos dados dos campos de texto
            String nomeCompleto = tbNome.getText();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String dataNascimentoStr = tbDataNascimento.getText();
            Date dataNascimento = formato.parse(dataNascimentoStr);
            String selectedGenero = cbGenero.getValue();
            Genero genero = Genero.valueOf(selectedGenero);
            String setor = tbSetor.getText();
            int cargaHorariaSemanal = parseInteger(tbCargaHoraria.getText(), "Carga Horaria");
            //long idMedico = parseLong(tbIdMedico.getText(), "ID Médico");
            long idMedico= gerarNovoIdMedico();
            boolean cirurgiao = parseBoolean(tfCirurgiao.getText(), "Cirurgião");
            int numeroCRM = parseInteger(tfCRM.getText(), "CRM");
            String areaEspecialidade = tfEspecialidadae.getText();

            //cadastro endereco
            String rua = tfRua.getText();
            String bairro = tfBairro.getText();
            String cidade = tfCidade.getText();
            String estado = tfEstado.getText();
            int cep = parseInteger(tfCEP.getText(), "CEP");
            int numero = parseInteger(tfNumero.getText(), "Numero");

            // cadatro Contato
            String telefone = tfTelefone.getText();
            String celular = tfCelular.getText();
            String email = tfEmail.getText();

            Endereco endereco = new Endereco(rua, bairro, cidade, estado, cep, numero);
            ContatoTelEmail contato = new ContatoTelEmail(telefone, celular, email);

            // Criação do objeto Medico
            Medico medico;
            medico = new Medico(idMedico, numeroCRM, cirurgiao, nomeCompleto,
                    setor, dataNascimento, genero, endereco, contato, setor, cargaHorariaSemanal);
            medico.adicionarEspecialidade(areaEspecialidade);

            // Adiciona o enfermeiro na lista para exibir na tabela
            listaMedicos.add(medico);

            PJason.salvarMedicos(listaMedicos);
            PXml.salvarMedicos(listaMedicos);
            
            limparCampos();

            // Simulação do cadastro (aqui você pode adicionar lógica para salvar em um banco de dados)
            mostrarMensagem("Médico cadastrado com sucesso!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException | ParseException e) {
            mostrarMensagem("Erro ao cadastrar Médico: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private long gerarNovoIdMedico() {
        return listaMedicos.stream()
                .mapToLong(Medico::getIdmedico)
                .max()
                .orElse(0) + 1; // Retorna 1 se a lista estiver vazia
    }

    @FXML
    private void switchToMedicoConsulta() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/femass/clinicahospitalar/medConsulta.fxml"));
        Parent root = loader.load();

        // Verifique se o controlador foi carregado corretamente
        MedConsultaController consultaController = loader.getController();
        if (consultaController != null) {
            consultaController.carregarMedicos(listaMedicos);
        } else {
            System.out.println("Erro: controlador EnfConsultaController não foi inicializado.");
        }

        // Exibe a nova janela com a tabela de consulta
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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

    private boolean parseBoolean(String text, String fieldName) {
        if (text.equalsIgnoreCase("true") || text.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(text);
        } else {
            mostrarMensagem("Campo " + fieldName + " deve ser 'true' ou 'false'.", Alert.AlertType.ERROR);
            throw new IllegalArgumentException(fieldName + " should be true or false");
        }
    }

    private Genero parseGenero(String text) {
        try {
            return Genero.valueOf(text.toUpperCase());
        } catch (IllegalArgumentException e) {
            mostrarMensagem("Genero inválido. Use 'MASCULINO' ou 'FEMININO'.", Alert.AlertType.ERROR);
            throw e;
        }
    }

    private int parseInteger(String text, String fieldName) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            mostrarMensagem("Campo " + fieldName + " deve ser um número válido.", Alert.AlertType.ERROR);
            throw e;
        }
    }

    private long parseLong(String text, String fieldName) {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException e) {
            mostrarMensagem("Campo " + fieldName + " deve ser um número válido.", Alert.AlertType.ERROR);
            throw e;
        }
    }

    private void limparCampos() {
        tbNome.clear();
        tbSetor.clear();
        tbCargaHoraria.clear();
        tfCRM.clear();
        tfCirurgiao.clear();
        tbDataNascimento.clear();
        tfBairro.clear();
        tfCEP.clear();
        tfCelular.clear();
        tfCidade.clear();
        tfEmail.clear();
        tfEstado.clear();
        tfNumero.clear();
        tfRua.clear();
        tfTelefone.clear();

    }

    private void mostrarMensagem(String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
}
