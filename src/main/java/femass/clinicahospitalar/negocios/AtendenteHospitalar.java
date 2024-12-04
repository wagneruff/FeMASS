package femass.clinicahospitalar.negocios;

import java.util.Date;

public class AtendenteHospitalar extends DadoPessoal {   
  
    private String setor;
    private int chSemanal;

    public AtendenteHospitalar(String nomeCompleto, Date dataNascimento, Genero genero, Endereco endereco,
            ContatoTelEmail contato, String setor, int chSemanal) {
        super(nomeCompleto, dataNascimento,genero, endereco,contato);
        this.setor = setor;
        this.chSemanal = chSemanal;
    }
    
    public AtendenteHospitalar(){
        
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public int getChSemanal() {
        return chSemanal;
    }

    public void setChSemanal(int chSemanal) {
        this.chSemanal = chSemanal;
    }

    
    

}
