package femass.clinicahospitalar.negocios;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.List;

@XStreamAlias("responsavel")
public class Responsavel extends ContatoTelEmail {

    @XStreamAlias("idResponsavel")
    private Long idResponsavel;

    @XStreamAlias("nomeResponsavel")
    private String nomeResponsavel;

    @XStreamImplicit // Serializa os itens da lista diretamente como nós sem wrapper
    private List<Paciente> pacientes = new ArrayList<>();

    @XStreamOmitField // Ignora este campo durante a serialização
    private transient String dadosTemporarios;

    public Responsavel(Long idResponsavel, String nomeResponsavel, String telefone,
            String celular, String email) {
        super(telefone, celular, email);
        this.idResponsavel = idResponsavel;
        this.nomeResponsavel = nomeResponsavel;
    }

    /*
    public void adicionarPaciente(Paciente paciente) {
        if (paciente != null && !pacientes.contains(paciente)) {
            pacientes.add(paciente);
            paciente.adicionarResponsavel(this);
        }
    }
     */

    public void adicionarPaciente(Paciente paciente) {
        if (paciente != null && !pacientes.contains(paciente)) {
            pacientes.add(paciente);
            if (!paciente.getContatoResponsavel().contains(this)) {
                paciente.adicionarResponsavel(this);
            }
        }
    }

    public void removerPaciente(Paciente paciente) {
        if (pacientes.contains(paciente)) {
            pacientes.remove(paciente);
            paciente.removerResponsavel(this);
        }
    }

    public String getTelefone() {

        return super.getTelefone();
    }

    public Long getIdResponsavel() {
        return idResponsavel;
    }

    public void setIdResponsavel(Long idResponsavel) {
        this.idResponsavel = idResponsavel;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

}
