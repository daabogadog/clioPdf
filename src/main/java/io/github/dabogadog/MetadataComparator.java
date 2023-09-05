package io.github.dabogadog;

import com.itextpdf.text.pdf.PdfReader;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase compara los metadatos de dos archivos PDF.
 */
public class MetadataComparator {

    private static final Logger logger = Logger.getLogger(MetadataComparator.class.getName());

    /**
     * Compara los metadatos de dos archivos PDF.
     *
     * @param filePath1 Ruta al primer archivo PDF.
     * @param filePath2 Ruta al segundo archivo PDF.
     * @return true si los metadatos son iguales, false si son diferentes o si ocurre un error.
     */
    public static boolean compareMetadata(String filePath1, String filePath2) {
        try {
            PdfReader reader1 = new PdfReader(filePath1);
            PdfReader reader2 = new PdfReader(filePath2);

            Map<String, String> metadata1 = reader1.getInfo();
            Map<String, String> metadata2 = reader2.getInfo();

            boolean areEqual = true;

            for (String key : metadata1.keySet()) {
                if (!metadata2.containsKey(key) || !metadata1.get(key).equals(metadata2.get(key))) {
                    areEqual = false;
                    logger.log(Level.INFO, "La propiedad de metadatos '" + key + "' es diferente.");
                    logger.log(Level.INFO, "Archivo 1: " + metadata1.get(key));
                    logger.log(Level.INFO, "Archivo 2: " + metadata2.get(key));
                    logger.log(Level.INFO, "");
                }
            }

            reader1.close();
            reader2.close();
            return areEqual;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ocurrió un error durante la comparación de metadatos:", e);
            return false;
        }
    }
}


/**
* En este código, abrimos y leemos ambos archivos PDF utilizando PdfReader. Luego, obtenemos los metadatos de cada
* archivo PDF utilizando el método getInfo(), que devuelve un mapa de pares clave-valor de los metadatos.

* A continuación, iteramos sobre las claves del mapa de metadatos del primer archivo y verificamos si la clave también
*  está presente en el mapa de metadatos del segundo archivo. Luego, comparamos los valores correspondientes.
* Si encontramos alguna diferencia en los valores de metadatos para una clave determinada, imprimimos un mensaje
* indicando la clave y los valores correspondientes de ambos archivos.

Si todos los metadatos son iguales, se imprime un mensaje indicando que los metadatos de los archivos PDF son iguales.
* */