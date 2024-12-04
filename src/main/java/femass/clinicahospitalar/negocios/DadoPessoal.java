package femass.clinicahospitalar.negocios;

import java.util.Date;

public class DadoPessoal {

    private String nomeCompleto;
    private Date dataNascimento;   
    private Genero genero;
    private Endereco endereco;
    private ContatoTelEmail contato;

    // Constructor
    public DadoPessoal(String nomeCompleto, Date dataNascimento,  Genero genero, Endereco endereco, ContatoTelEmail contato) {
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;       
        this.genero = genero;
        this.endereco=endereco;
        this.contato=contato;
    }

    public DadoPessoal() {
    }

    // Getters and Setters
    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
  

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
    
    //condicao ? valorSeVerdadeiro : valorSeFalso;
    public String getRua() {
        return endereco != null ? endereco.getRua() : null;
    }
    public String getBairro() {
        return endereco != null ? endereco.getBairro() : null;
    }
    public String getCidade() {
        return endereco != null ? endereco.getCidade() : null;
    }
    public String getEstado() {
        return endereco != null ? endereco.getEstado() : null;
    }
    public int getNumero() {
        return endereco.getNumero();
    }
    public int getCep() {
        return endereco.getCep();
    }
    
   public String getTelefone(){
       return contato.getTelefone();
   }
   public String getEmail(){
       return contato.getEmail();
   }
   public String getCelular(){
       return contato.getCelular();
   }
    

}
