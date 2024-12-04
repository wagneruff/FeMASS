
package femass.clinicahospitalar.negocios;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("consultaMedica")
public class ConsultaMedica {
    
    @XStreamAlias("idConsulta") // Define o nome do nó para o campo
    private Long idConsulta;

    @XStreamAlias("idPaciente") // Define o nome do nó para o campo (referência ao paciente)
    private Long idPaciente;

    @XStreamAlias("idMedico") // Define o nome do nó para o campo (referência ao médico)
    private Long idMedico;

    @XStreamAlias("exameQueixa") // Define o nome do nó para o campo
    private String exameQueixa;

    @XStreamAlias("diagnostico") // Define o nome do nó para o campo
    private String diagnostico;

    @XStreamAlias("prescricao") // Define o nome do nó para o campo
    private String prescricao;

    @XStreamAlias("indicacaoCirurgica") // Define o nome do nó para o campo
    private boolean indicacaoCirurgica;

    public ConsultaMedica(Long idConsulta, Long idPaciente, Long idMedico, String exameQueixa, String diagnostico, String prescricao, boolean indicacaoCirurgica) {
        this.idConsulta = idConsulta;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.exameQueixa = exameQueixa;
        this.diagnostico = diagnostico;
        this.prescricao = prescricao;
        this.indicacaoCirurgica = indicacaoCirurgica;
    }

    // Construtores, getters e setters
    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getExameQueixa() {
        return exameQueixa;
    }

    public void setExameQueixa(String exameQueixa) {
        this.exameQueixa = exameQueixa;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getPrescricao() {
        return prescricao;
    }

    public void setPrescricao(String prescricao) {
        this.prescricao = prescricao;
    }

    public boolean isIndicacaoCirurgica() {
        return indicacaoCirurgica;
    }

    public void setIndicacaoCirurgica(boolean indicacaoCirurgica) {
        this.indicacaoCirurgica = indicacaoCirurgica;
    }    
   
    /*
    @Override
    public String toString() {
        return "ConsultaMedica{" +
                "idConsulta=" + idConsulta +
                ", idPaciente=" + idPaciente +
                ", idMedico=" + idMedico +
                ", exameQueixa='" + exameQueixa + '\'' +
                ", diagnostico='" + diagnostico + '\'' +
                ", prescricao='" + prescricao + '\'' +
                ", indicacaoCirurgica=" + indicacaoCirurgica +
                '}';
    }
    */
}
