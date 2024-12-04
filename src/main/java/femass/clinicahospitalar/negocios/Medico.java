package femass.clinicahospitalar.negocios;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Date;

@XStreamAlias("medico") 
public class Medico extends AtendenteHospitalar {

    @XStreamAlias("idMedico") // Mapeia o campo para o nó XML
    private Long idmedico;

    @XStreamAlias("crm") // Nome do nó para o campo CRM
    private int numeroCRM;

    @XStreamImplicit // Serializa os itens diretamente como nós XML sem um wrapper
    private ArrayList<String> areasEspecialidade;

    @XStreamAlias("cirurgiao") // Nome do nó XML
    private boolean cirurgiao;
    
     @XStreamOmitField // Ignora este campo durante a serialização
    private transient String informacaoTemporaria;
     
    public Medico(long idmedico, int numeroCRM, boolean cirurgiao, String nomeCompleto, String setor, Date dataNascimento, Genero genero, Endereco endereco, ContatoTelEmail contato, String setor1, int chSemanal) {
        super(nomeCompleto, dataNascimento, genero, endereco, contato, setor, chSemanal);
        this.idmedico = idmedico;
        this.numeroCRM = numeroCRM;
        this.cirurgiao = cirurgiao;
        this.areasEspecialidade = new ArrayList<>();
    }

    public void adicionarEspecialidade(String especialidade) {
        if (!areasEspecialidade.contains(especialidade)) { // Evita duplicatas
            areasEspecialidade.add(especialidade);
        }
    }

    public void removerEspecialidade(String especialidade) {
        areasEspecialidade.remove(especialidade);
    }

    public void exibirEspecialidades() {
        System.out.println("Especialidades do médico:");
        for (String especialidade : areasEspecialidade) {
            System.out.println("- " + especialidade);
        }
    }

    public ArrayList<String> getAreasEspecialidade() {
        return areasEspecialidade;
    }

    public boolean gegtCirurgiao() {
        return cirurgiao;
    }

    public Long getIdmedico() {
        return idmedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idmedico = idMedico;
    }

    public int getNumeroCRM() {
        return numeroCRM;
    }

    public void setNumeroCRM(int numeroCRM) {
        this.numeroCRM = numeroCRM;
    }

    public boolean getCirurgiao() {
        return cirurgiao;
    }

    public void setCirurgiao(boolean cirurgiao) {
        this.cirurgiao = cirurgiao;
    }
}
