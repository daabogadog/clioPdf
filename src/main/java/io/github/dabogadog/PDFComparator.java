package io.github.dabogadog;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase compara dos archivos PDF página por página y muestra las diferencias de texto.
 */
public class PDFComparator {

    private static Logger logger = Logger.getLogger(PDFComparator.class.getName());

    /**
     * Compara dos archivos PDF en busca de diferencias en el texto de sus páginas.
     *
     * @param filePath1 Ruta al primer archivo PDF.
     * @param filePath2 Ruta al segundo archivo PDF.
     * @return true si los archivos son iguales en contenido de texto, false si hay diferencias o si ocurre un error.
     */
    public static boolean comparePDFs(String filePath1, String filePath2) {
        try {
            PdfReader reader1 = new PdfReader(filePath1);
            PdfReader reader2 = new PdfReader(filePath2);

            int numPages1 = reader1.getNumberOfPages();
            int numPages2 = reader2.getNumberOfPages();

            if (numPages1 != numPages2) {
                logger.log(Level.INFO, "Los archivos PDF tienen un número diferente de páginas.");
                reader1.close();
                reader2.close();
                return false;
            }

            boolean areEqual = true;

            for (int i = 1; i <= numPages1; i++) {
                String pageText1 = PdfTextExtractor.getTextFromPage(reader1, i);
                String pageText2 = PdfTextExtractor.getTextFromPage(reader2, i);

                if (!pageText1.equals(pageText2)) {
                    areEqual = false;
                    logger.log(Level.INFO, String.format("Diferencias en la página %s:", i));
                    printTextDifferences(pageText1, pageText2);
                }
            }

            reader1.close();
            reader2.close();
            return areEqual;
        } catch (Exception e) {
            logger.log(Level.WARNING, String.format("Ocurrió un error durante la comparación de archivos PDF: %s", e));
            return false;
        }
    }

    /**
     * Muestra las diferencias línea por línea entre dos bloques de texto.
     *
     * @param text1 Texto del primer archivo.
     * @param text2 Texto del segundo archivo.
     */
    private static void printTextDifferences(String text1, String text2) {
        String[] lines1 = text1.split("\\r?\\n");
        String[] lines2 = text2.split("\\r?\\n");

        for (int i = 0; i < Math.min(lines1.length, lines2.length); i++) {
            if (!lines1[i].equals(lines2[i])) {
                logger.log(Level.INFO, String.format("Línea %s:", i + 1));
                logger.log(Level.INFO, String.format("Archivo 1: %s", lines1[i]));
                logger.log(Level.INFO, String.format("Archivo 2: %s", lines2[i]));
            }
        }
    }
}



/**
* En este código, abrimos y leemos ambos archivos PDF utilizando PdfReader. Luego, comparamos el número de páginas de
* ambos archivos. Si el número de páginas es diferente, concluimos que los archivos no son iguales.

Si el número de páginas es el mismo, recorremos cada página de los archivos y extraemos el texto utilizando
* PdfTextExtractor.getTextFromPage. Comparamos el texto de cada página entre los dos archivos. Si encontramos
* alguna diferencia en el texto de alguna página, concluimos que los archivos no son iguales.

Si no se encontraron diferencias en ninguna página, concluimos que los archivos PDF son iguales.
* */