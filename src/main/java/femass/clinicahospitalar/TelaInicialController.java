
package femass.clinicahospitalar;

import Persistencia.SelectPersistencia;
import femass.clinicahospitalar.negocios.Enfermeiro;
import femass.clinicahospitalar.negocios.Medico;
import femass.clinicahospitalar.negocios.Paciente;
import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

//public class TelaInicialController implements Initializable {
public class TelaInicialController {

    @FXML
    private MenuItem mConsEnfermeiro;
    @FXML
    private MenuItem mConsMedico;
    @FXML
    private MenuItem mConsPaciente;
    
    @FXML
    private MenuItem mCadatroEnfermeiro;
    @FXML
    private MenuItem mCadastroMedico;
    @FXML
    private MenuItem mCadastroPaciente;    
    @FXML
    private MenuItem tfCadastroConsultas;
    @FXML
    private MenuItem tfConsultaAgendamento;
    @FXML
    private RadioButton rbJason;
    @FXML
    private RadioButton rbXml;
    
    private ObservableList<Enfermeiro> listaEnfermeiros;
    private ObservableList<Medico> listaMedicos;
    private ObservableList<Paciente> listaPacientes;

   /* @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }*/
    @FXML
    public void initialize() { 
         rbJason.setSelected(true);
         setmetodopersistencia();
    }   
    
    public void setmetodopersistencia(){
        String metodo=" ";
        if (rbJason.isSelected()) {
            metodo="JSON";
        } else if (rbXml.isSelected()) {
            metodo="XML";
        }
        SelectPersistencia.setMetodoPersistencia(metodo);
    }
    
    
    public void switchCadastroEnfermeiro() throws IOException {
        try {
            // Carregar a tela de consulta de enfermeiros
            FXMLLoader loader = new FXMLLoader(getClass().getResource("enfermeiro.fxml"));
            Parent root = loader.load();

            // Obtenha a instância do controlador da tela de consulta de enfermeiros
            EnfermeiroController1 controller = loader.getController();           

            // Configurar a nova cena e exibi-la
            Stage stage = new Stage(); // ou use o stage atual se necessário
            stage.setTitle("CADSTRAR ENFERMEIROS");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) mCadatroEnfermeiro.getParentPopup().getOwnerWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchToConsultaEnfermeiro() throws IOException {
        try {
            // Carregar a tela de consulta de enfermeiros
            FXMLLoader loader = new FXMLLoader(getClass().getResource("consultas.fxml"));
            Parent root = loader.load();

            // Obtenha a instância do controlador da tela de consulta de enfermeiros
            EnfConsultaController controller = loader.getController();

            // Passe a lista carregada para a tela de consulta
            if (listaEnfermeiros != null) {
                controller.carregarEnfermeiros(listaEnfermeiros);
            }

            // Configurar a nova cena e exibi-la
            Stage stage = new Stage(); // ou use o stage atual se necessário
            stage.setTitle("CONSULTAR ENFERMEIROS");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) mConsEnfermeiro.getParentPopup().getOwnerWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    
    public void switchCadastroMedico() throws IOException {
       try {
        // Carregar a tela de consulta de médicos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("medicocad.fxml"));
        Parent root = loader.load();

        // Obtenha o controlador da nova cena de consulta de médicos
        MedController controller = loader.getController();
       
        // Configurar a nova cena e exibi-la
        Stage stage = new Stage();
        stage.setTitle("CADASTRAR MÉDICOS");
        stage.setScene(new Scene(root));
        stage.show();      
        
        Stage currentStage2 = (Stage) mCadastroMedico.getParentPopup().getOwnerWindow();
        currentStage2.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void switchToConsultaMedico() throws IOException {
         try {
        // Carregar a tela de consulta de médicos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("medConsulta.fxml"));
        Parent root = loader.load();

        // Obtenha o controlador da nova cena de consulta de médicos
        MedConsultaController controller = loader.getController();

        // Passe a lista carregada para a tela de consulta de médicos
        if (listaMedicos != null) {
            controller.carregarMedicos(listaMedicos);
        }

        // Configurar a nova cena e exibi-la
        Stage stage = new Stage();
        stage.setTitle("CONSULTAR MÉDICOS");
        stage.setScene(new Scene(root));
        stage.show();      
        
        Stage currentStage2 = (Stage) mConsMedico.getParentPopup().getOwnerWindow();
        currentStage2.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void switchToConsultaPaciente() throws IOException {
       try {
        // Carregar a tela de consulta de médicos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pacConsulta.fxml"));
        Parent root = loader.load();

        // Obtenha o controlador da nova cena de consulta de médicos
        PacConsultaController controller = loader.getController();

        // Passe a lista carregada para a tela de consulta de médicos
        if (listaPacientes != null) {
            controller.carregarPacientes(listaPacientes);
        }

        // Configurar a nova cena e exibi-la
        Stage stage = new Stage();
        stage.setTitle("CONSULTAR PACIENTES");
        stage.setScene(new Scene(root));
        stage.show();      
        
        Stage currentStage2 = (Stage) mConsMedico.getParentPopup().getOwnerWindow();
        currentStage2.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
     public void switchCadastroPaciente() throws IOException {
       try {
        // Carregar a tela de consulta de médicos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pacientecad.fxml"));
        Parent root = loader.load();

        // Obtenha o controlador da nova cena de consulta de médicos
        PacienteController controller = loader.getController();
       
        // Configurar a nova cena e exibi-la
        Stage stage = new Stage();
        stage.setTitle("CADASTRAR PACIENTES");
        stage.setScene(new Scene(root));
        stage.show();      
        
        Stage currentStage2 = (Stage) mCadastroPaciente.getParentPopup().getOwnerWindow();
        currentStage2.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
    public void switchCadastroConsultaMedica() throws IOException {
       try {
        // Carregar a tela de consulta de médicos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("consultMedCad.fxml"));
        Parent root = loader.load();

        // Obtenha o controlador da nova cena de consulta de médicos
        ConsultMedCadController controller = loader.getController();
       
        // Configurar a nova cena e exibi-la
        Stage stage = new Stage();
        stage.setTitle("CADASTRO CONSULTA R MÉDICA");
        stage.setScene(new Scene(root));
        stage.show();      
        
        Stage currentStage2 = (Stage) tfCadastroConsultas.getParentPopup().getOwnerWindow();
        currentStage2.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    public void switchConsultaMedica() throws IOException {
       try {
        // Carregar a tela de consulta de médicos
        FXMLLoader loader = new FXMLLoader(getClass().getResource("consultaMedica.fxml"));
        Parent root = loader.load();

        // Obtenha o controlador da nova cena de consulta de médicos
        ConsultaMedicaController controller = loader.getController();
       
        // Configurar a nova cena e exibi-la
        Stage stage = new Stage();
        stage.setTitle("HISTÓRICO DE CONSUTAS MÉDICAS");
        stage.setScene(new Scene(root));
        stage.show();      
        
        Stage currentStage2 = (Stage) tfConsultaAgendamento.getParentPopup().getOwnerWindow();
        currentStage2.close();        
      

    } catch (IOException e) {
        e.printStackTrace();
    }
    }
    
}
