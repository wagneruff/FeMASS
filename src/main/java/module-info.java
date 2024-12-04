module femass.clinicahospitalar {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.base;
    requires java.xml; // Necessário para classes de XML
    requires xstream; // Adiciona a dependência do XStream
    
    opens femass.clinicahospitalar.negocios to xstream, com.google.gson, javafx.base;
   // opens femass.clinicahospitalar.negocios to com.google.gson;    
   // opens femass.clinicahospitalar.negocios to xstream, javafx.base;
    opens femass.clinicahospitalar to javafx.fxml;
   
  //  opens femass.clinicahospitalar.negocios to javafx.base;
    exports femass.clinicahospitalar;
    exports femass.clinicahospitalar.negocios; 
  
   
  

}
