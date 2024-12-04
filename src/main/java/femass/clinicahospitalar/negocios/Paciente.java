package femass.clinicahospitalar.negocios;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@XStreamAlias("paciente")
public class Paciente extends DadoPessoal {
    
    @XStreamAlias("idPaciente") // Define o nome do nó para o campo
    private Long idPaciente;
    
    @XStreamAlias("idade") // Define o nome do nó para o campo
    private int idade;
    
    @XStreamAlias("dataCadastro") // Define o nome do nó para o campo
    private Date dataCadastro;
    
    @XStreamAlias("obsGeral") // Define o nome do nó para o campo
    private String obsGeral;
    
    @XStreamImplicit(itemFieldName = "consultaMedica")  // Serializa os itens da lista diretamente como nós sem wrapper
    private List<Responsavel> contatoResponsavel;
    
    @XStreamImplicit // Serializa os itens da lista diretamente como nós sem wrapper
    private static List<ConsultaMedica> historicoConsultasMedicas = new ArrayList<>();
    
    @XStreamOmitField // Ignora este campo durante a serialização
    private transient String dadosTemporarios;
    
    public Paciente(Long idPaciente, int idade, Date dataCadastro, String obsGeral,
            String nomeCompleto, Date dataNascimento, Genero genero, Endereco endereco,
            ContatoTelEmail contato) {
        super(nomeCompleto, dataNascimento, genero, endereco, contato);
        this.idPaciente = idPaciente;
        this.idade = idade;
        this.dataCadastro = dataCadastro;
        this.obsGeral = obsGeral;
        this.contatoResponsavel = new ArrayList<>();
    }
    
    public static void setHistoricoConsultasMedicas(List<ConsultaMedica> historicoConsultasMedicas) {
        Paciente.historicoConsultasMedicas = historicoConsultasMedicas;
    }
    
    public void adicionarResponsavel(Responsavel responsavel) {
        if (responsavel != null && !contatoResponsavel.contains(responsavel)) {
            contatoResponsavel.add(responsavel);
            
        }
    }

  
    public void removerResponsavel(Responsavel responsavel) {
        if (contatoResponsavel.contains(responsavel)) {
            contatoResponsavel.remove(responsavel);
            responsavel.removerPaciente(this);
        }
    }

    public Long getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public Date getDataCadastro() {
        return dataCadastro;
    }
    
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    public String getObsGeral() {
        return obsGeral;
    }
    
    public void setObsGeral(String obsGeral) {
        this.obsGeral = obsGeral;
    }
    
    public List<Responsavel> getContatoResponsavel() {
        return contatoResponsavel;
    }
    
    public void setContatoResponsavel(List<Responsavel> contatoResponsavel) {
        this.contatoResponsavel = contatoResponsavel;
    }

    // Método para adicionar uma consulta ao histórico
    public void adicionarConsulta(ConsultaMedica consulta) {
        if (consulta != null) {
            historicoConsultasMedicas.add(consulta);
        }
    }

    // Método para obter o histórico de consultas
    public List<ConsultaMedica> getHistoricoConsultasMedicas() {
        return new ArrayList<>(historicoConsultasMedicas); // Retorna uma cópia para segurança
    }
    
    public void setRua(String rua) {
        this.setRua(rua);
    }
    public void setBairro(String bairro) {
        this.setBairro(bairro);
    }
    public void setCidade(String cidade) {
        this.setCidade(cidade);
    }
    public void setEstado(String estado) {
        this.setEstado(estado);
    }
    public void setCelular(String cel) {
        this.setCelular(cel);
    }
    public void setEmail(String email) {
        this.setEmail(email);
    }
    public void setTelefone(String tel) {
        this.setTelefone(tel);
    }
    
    
}
