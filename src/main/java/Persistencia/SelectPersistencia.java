
package Persistencia;


public class SelectPersistencia {   

        private static String metodoPersistencia = "JSON"; // Valor padrão

        public static String getMetodoPersistencia() {
            return metodoPersistencia;
        }

        public static void setMetodoPersistencia(String metodo) {
            metodoPersistencia = metodo;
        }
    }


