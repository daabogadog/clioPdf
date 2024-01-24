package io.github.dabogadog;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVReader {

    /**
     * Busca frases en un archivo CSV.
     *
     * @param filePath      Ruta del archivo CSV.
     * @param phrasesString Cadena de frases separadas por coma.
     * @return true si todas las frases fueron encontradas, false de lo contrario.
     */
    public static boolean searchPhrasesInCSV(String filePath, String phrasesString) {
        /**
         * Convertir la cadena de frases separadas por coma a una lista
         */
        List<String> phrases = new ArrayList<>();
        String[] phrasesArray = phrasesString.split(",");
        for (String phrase : phrasesArray) {
            phrases.add(phrase.trim());
            /**
             * Agregar cada frase a la lista, eliminando espacios adicionales
             */
        }

        try (CSVParser parser = new CSVParser(new FileReader(filePath), CSVFormat.DEFAULT)) {
            for (CSVRecord record : parser) {
                Iterator<String> iterator = phrases.iterator();
                while (iterator.hasNext()) {
                    String phrase = iterator.next();
                    boolean phraseFound = false;
                    for (String value : record) {
                        if (value.contains(phrase)) {
                            phraseFound = true;
                            break;
                        }
                    }
                    if (phraseFound) {
                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return phrases.isEmpty();
    }



}


