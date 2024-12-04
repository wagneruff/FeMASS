
package Persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import femass.clinicahospitalar.negocios.ConsultaMedica;
import femass.clinicahospitalar.negocios.Enfermeiro;
import femass.clinicahospitalar.negocios.Medico;
import femass.clinicahospitalar.negocios.Paciente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PJason {
    
    private static final String FILE_PATH_ENFERMEIROS = "enfermeiros.json";
    private static final String FILE_PATH_MEDICOS = "medicos.json";
    private static final String FILE_PATH_CONSULTASMED = "consultasmed.json";
    private static final String FILE_PATH = "pacientes.json";
    
    private static ObservableList<Enfermeiro> listaEnfermeiros = FXCollections.observableArrayList();
    private static ObservableList<Medico> listaMedicos = FXCollections.observableArrayList();
    private static ObservableList<Paciente> listaPacientes = FXCollections.observableArrayList();
    private static ObservableList<ConsultaMedica> listaConsultasMedicas = FXCollections.observableArrayList();
    
    
    public static void salvarPacientes(ObservableList<Paciente> listaPacientes) {
        salvarDados(FILE_PATH, listaPacientes);
    }
    
    public static void carregarPacientes() {
        listaPacientes = carregarDados(FILE_PATH, new TypeToken<List<Paciente>>() {}.getType());
    }
    
     public static ObservableList<Paciente> getListaPacientes() {
        return listaPacientes;
    }
     
        
    public static void salvarConsultaMed(ObservableList<ConsultaMedica> listaConsultasMedicas) {
        salvarDados(FILE_PATH_CONSULTASMED, listaConsultasMedicas);
    }
    
    public static void carregarConsultaMed() {
        listaConsultasMedicas = carregarDados(FILE_PATH_CONSULTASMED, new TypeToken<List<ConsultaMedica>>() {}.getType());
    }
    
     public static ObservableList<ConsultaMedica> getListaConsultaMed() {
        return listaConsultasMedicas;
    }
    
    public static void salvarEnfermeiros(ObservableList<Enfermeiro> listaEnfermeiros) {
        salvarDados(FILE_PATH_ENFERMEIROS, listaEnfermeiros);
    }

    // Método para carregar a lista de enfermeiros do arquivo JSON
    public static void carregarEnfermeiros() {
        listaEnfermeiros = carregarDados(FILE_PATH_ENFERMEIROS, new TypeToken<List<Enfermeiro>>() {}.getType());
    }

    public static ObservableList<Enfermeiro> getListaEnfermeiros() {
        return listaEnfermeiros;
    }

    // Método para salvar a lista de médicos no arquivo JSON
    public static void salvarMedicos(ObservableList<Medico> listaMedicos) {
        salvarDados(FILE_PATH_MEDICOS, listaMedicos);
    }

    // Método para carregar a lista de médicos do arquivo JSON
    public static void carregarMedicos() {
        listaMedicos = carregarDados(FILE_PATH_MEDICOS, new TypeToken<List<Medico>>() {}.getType());
    }

    public static ObservableList<Medico> getListaMedicos() {
        return listaMedicos;
    }

    // Método genérico para salvar dados em JSON
    private static <T> void salvarDados(String filePath, ObservableList<T> lista) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método genérico para carregar dados de JSON
    private static <T> ObservableList<T> carregarDados(String filePath, Type type) {
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<T> lista = gson.fromJson(reader, type);
            return lista != null ? FXCollections.observableArrayList(lista) : FXCollections.observableArrayList();
        } catch (IOException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }
}
  