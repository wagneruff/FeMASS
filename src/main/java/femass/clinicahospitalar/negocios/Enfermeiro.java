package femass.clinicahospitalar.negocios;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.Date;

@XStreamAlias("Enfermeiro")
public class Enfermeiro extends AtendenteHospitalar {
    
    @XStreamAsAttribute
    private long idEnfermeiro;
    
    @XStreamAsAttribute
    private boolean treinadoOpRX;
    // private static ArrayList<Enfermeiro> listaEnfermeiros = new ArrayList<>();

    public Enfermeiro(String nomeCompleto, Date dataNascimento, Genero genero, Endereco endereco, ContatoTelEmail contato, String setor, int chSemanal, long idEnfermeiro, boolean treinadoOpRX) {
        super(nomeCompleto, dataNascimento, genero, endereco, contato, setor, chSemanal);
        this.idEnfermeiro = idEnfermeiro;
        this.treinadoOpRX = treinadoOpRX;
        // listaEnfermeiros.add(this);
    }

    public Enfermeiro() {
    }

    @Override
    public String getNomeCompleto() {
        return super.getNomeCompleto(); // ou outro processamento específico
    }

    @Override
    public String getSetor() {
        return super.getSetor();
    }

    @Override
    public Genero getGenero() {
        return super.getGenero();
    }

    public Date getDateNascimento() {
        return super.getDataNascimento();
    }

    public long getIdEnfermeiro() {
        return idEnfermeiro;
    }

    public boolean getTreinadoOpRX() {
        return treinadoOpRX;
    }

    @Override
    public int getChSemanal() {
        return super.getChSemanal();
    }

    @Override
    public String getRua() {
        return super.getRua();
    }

    @Override
    public String getCidade() {
        return super.getCidade();
    }

    @Override
    public String getEstado() {
        return super.getEstado();
    }

    @Override
    public String getBairro() {
        return super.getBairro();
    }

    @Override
    public int getNumero() {
        return super.getNumero();
    }

    @Override
    public int getCep() {
        return super.getCep();
    }

    @Override
    public String getTelefone() {
        return super.getTelefone();
    }

    @Override
    public String getCelular() {
        return super.getCelular();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

}
