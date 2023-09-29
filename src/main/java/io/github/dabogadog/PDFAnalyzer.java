package io.github.dabogadog;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase analiza un archivo PDF en busca de frases específicas.
 */
public class PDFAnalyzer {

    private PDFAnalyzer() {
        // Constructor privado vacío para evitar instanciación
    }

    private static Logger logger = Logger.getLogger(PDFAnalyzer.class.getName());

    /**
     * Verifica si las frases objetivo se encuentran en el PDF.
     *
     * @param filePath     Ruta al archivo PDF.
     * @param targetPhrase Frases objetivo separadas por comas.
     * @return true si todas las frases se encuentran en el PDF, false si alguna no se encuentra o si ocurre un error.
     */
    public static boolean checkPhrasesInPDF(String filePath, String targetPhrase) {
        boolean allPhrasesFound = true;

        try {
            PdfReader reader = new PdfReader(filePath);
            List<String> phrasesToCheck = Arrays.asList(targetPhrase.split(","));
            List<String> notFoundPhrases = new ArrayList<>();

            for (String phrase : phrasesToCheck) {
                boolean found = false;
                for (int pageNumber = 1; pageNumber <= reader.getNumberOfPages(); pageNumber++) {
                    String pageText = PdfTextExtractor.getTextFromPage(reader, pageNumber);
                    if (pageText.contains(phrase)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    notFoundPhrases.add(phrase);
                    allPhrasesFound = false;
                }
            }

            reader.close();

            if (!notFoundPhrases.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder("Las siguientes frases no se encontraron en el PDF: ");
                for (String phrase : notFoundPhrases) {
                    errorMessage.append(phrase).append(", ");
                }
                errorMessage.delete(errorMessage.length() - 2, errorMessage.length());
                logger.log(Level.WARNING, String.format("Frases no encontradas: %s", errorMessage.toString()));
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, String.format("Error leyendo el PDF y buscando la cadena:", e));
            allPhrasesFound=false;
        }

        return allPhrasesFound;
    }
}

