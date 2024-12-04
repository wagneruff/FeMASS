
package Persistencia;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.security.AnyTypePermission;
import femass.clinicahospitalar.negocios.ConsultaMedica;
import femass.clinicahospitalar.negocios.Enfermeiro;
import femass.clinicahospitalar.negocios.Medico;
import femass.clinicahospitalar.negocios.Paciente;
import femass.clinicahospitalar.negocios.Responsavel;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

public class PXml {

    private static final String FILE_PATH_ENFERMEIROS = "enfermeiros.xml";
    private static final String FILE_PATH_MEDICOS = "medicos.xml";
    private static final String FILE_PATH_CONSULTASMED = "consultasmed.xml";
    private static final String FILE_PATH_PACIENTES = "pacientes.xml";

    private static ObservableList<Enfermeiro> listaEnfermeiros = FXCollections.observableArrayList();
    private static ObservableList<Medico> listaMedicos = FXCollections.observableArrayList();
    private static ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private static ObservableList<ConsultaMedica> listaConsultasMedicas = FXCollections.observableArrayList();

    private static XStream setupXStream() {

        XStream xstream = new XStream(new DomDriver());
        xstream.addPermission(AnyTypePermission.ANY);

        xstream.processAnnotations(Paciente.class);
        xstream.processAnnotations(Responsavel.class);
        xstream.processAnnotations(ConsultaMedica.class);
        xstream.processAnnotations(Enfermeiro.class);
        xstream.processAnnotations(Enfermeiro.class);
        return xstream;
    }

    // MÉTODOS PARA PACIENTES
    public static void salvarPacientes(ObservableList<Paciente> listaPacientes) {
        salvarDados(FILE_PATH_PACIENTES, listaPacientes, Paciente.class);
    }

    public static void carregarPacientes() {
        listaPacientes = carregarDados(FILE_PATH_PACIENTES, Paciente.class);
    }

    public static ObservableList<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    // MÉTODOS PARA CONSULTAS MÉDICAS
    public static void salvarConsultaMed(ObservableList<ConsultaMedica> listaConsultasMedicas) {
        salvarDados(FILE_PATH_CONSULTASMED, listaConsultasMedicas, ConsultaMedica.class);
    }

    public static void carregarConsultaMed() {
        listaConsultasMedicas = carregarDados(FILE_PATH_CONSULTASMED, ConsultaMedica.class);
    }

    public static ObservableList<ConsultaMedica> getListaConsultaMed() {
        return listaConsultasMedicas;
    }

    // MÉTODOS PARA ENFERMEIROS
    public static void salvarEnfermeiros(ObservableList<Enfermeiro> listaEnfermeiros) {
        salvarDados(FILE_PATH_ENFERMEIROS, listaEnfermeiros, Enfermeiro.class);
    }

    public static void carregarEnfermeiros() {
        listaEnfermeiros = carregarDados(FILE_PATH_ENFERMEIROS, Enfermeiro.class);
    }

    public static ObservableList<Enfermeiro> getListaEnfermeiros() {
        return listaEnfermeiros;
    }

    // MÉTODOS PARA MÉDICOS
    public static void salvarMedicos(ObservableList<Medico> listaMedicos) {
        salvarDados(FILE_PATH_MEDICOS, listaMedicos, Medico.class);
    }

    public static void carregarMedicos() {
        listaMedicos = carregarDados(FILE_PATH_MEDICOS, Medico.class);
    }

    public static ObservableList<Medico> getListaMedicos() {
        return listaMedicos;
    }

    // MÉTODO GENÉRICO PARA SALVAR DADOS EM XML
    private static <T> void salvarDados(String filePath, ObservableList<T> lista, Class<T> clazz) {
        List<T> listaParaSalvar = new ArrayList<>(lista); // Conversão para ArrayList
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(clazz); // Habilita anotações da classe

        try (OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream(new File(filePath)), StandardCharsets.UTF_8)) {
            xstream.toXML(listaParaSalvar, writer);
            System.out.println("Dados salvos com sucesso em " + filePath);
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // MÉTODO GENÉRICO PARA CARREGAR DADOS DE XML
    private static <T> ObservableList<T> carregarDados(String filePath, Class<T> clazz) {
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(clazz);
        XStream.setupDefaultSecurity(xstream); // Configuração de segurança
        xstream.allowTypes(new Class[]{clazz});

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Arquivo " + filePath + " não encontrado. Retornando uma lista vazia.");
            return FXCollections.observableArrayList();
        }

        try {
            List<T> listaCarregada = (List<T>) xstream.fromXML(file);
            return FXCollections.observableArrayList(listaCarregada);
        } catch (Exception e) {
            System.err.println("Erro ao carregar os dados de " + filePath + ": " + e.getMessage());
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

}
