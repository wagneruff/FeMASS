
package femass.clinicahospitalar;

import femass.clinicahospitalar.negocios.Responsavel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ResponsavelCadController  {

    @FXML
    private Button btCadastrar;
    @FXML
    private Button btTelaInicial;
    @FXML
    private TextField tbNome;
    @FXML
    private TextField tfCelularResp;
    @FXML
    private TextField tfEmailRespons;
    @FXML
    private TextField tfIDResponsavel;
    @FXML
    private TextField tfTelResponsavel;
    @FXML
    void switchTelaInicial(ActionEvent event) {

    }   
    
    public void initialize() {
        // TODO
    }    
    
    public void cadastrarResponsavel(){
        Long idResponsavel=Long.parseLong(tfIDResponsavel.getText());
        String nomeResponsavel= tbNome.getText();
        String telefone= tfTelResponsavel.getText();
        String celular= tfCelularResp.getText();
        String email=tfEmailRespons.getText();
         
        Responsavel resp = new Responsavel(idResponsavel,nomeResponsavel,telefone,celular,email);
        
        
        
        
    
    
    }
}
