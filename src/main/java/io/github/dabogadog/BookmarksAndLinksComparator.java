package io.github.dabogadog;

import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase compara los marcadores y los enlaces internos de dos archivos PDF.
 */
public class BookmarksAndLinksComparator {

    private static final Logger logger = Logger.getLogger(BookmarksAndLinksComparator.class.getName());

    /**
     * Compara los marcadores y los enlaces internos de dos archivos PDF.
     *
     * @param filePath1 Ruta al primer archivo PDF.
     * @param filePath2 Ruta al segundo archivo PDF.
     * @return true si los marcadores y enlaces son iguales, false si son diferentes o si ocurre un error.
     */
    public static boolean compareBookmarksAndLinks(String filePath1, String filePath2) {
        try {
            PdfReader reader1 = new PdfReader(filePath1);
            PdfReader reader2 = new PdfReader(filePath2);

            boolean areBookmarksEqual = compareBookmarks(reader1, reader2);
            if (!areBookmarksEqual) {
                logger.log(Level.INFO, "Los marcadores de los archivos PDF son diferentes.");
            }

            boolean areLinksEqual = compareLinks(reader1, reader2);
            if (!areLinksEqual) {
                logger.log(Level.INFO, "Los enlaces internos de los archivos PDF son diferentes.");
            }

            reader1.close();
            reader2.close();

            return areBookmarksEqual && areLinksEqual;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ocurrió un error durante la comparación de marcadores y enlaces:", e);
            return false;
        }
    }

    /**
     * Compara los marcadores de dos archivos PDF.
     *
     * @param reader1 Lector del primer archivo PDF.
     * @param reader2 Lector del segundo archivo PDF.
     * @return true si los marcadores son iguales, false si son diferentes o uno de los archivos no tiene marcadores.
     */
    private static boolean compareBookmarks(PdfReader reader1, PdfReader reader2) {
        PdfDictionary bookmarks1 = reader1.getCatalog().getAsDict(PdfName.OUTLINES);
        PdfDictionary bookmarks2 = reader2.getCatalog().getAsDict(PdfName.OUTLINES);

        if (bookmarks1 == null || bookmarks2 == null) {
            logger.log(Level.INFO, "Uno de los archivos PDF no tiene marcadores.");
            return false;
        }

        return bookmarks1.toString().equals(bookmarks2.toString());
    }

    /**
     * Compara los enlaces internos de dos archivos PDF.
     *
     * @param reader1 Lector del primer archivo PDF.
     * @param reader2 Lector del segundo archivo PDF.
     * @return true si los enlaces son iguales, false si son diferentes o uno de los archivos no tiene enlaces en una página.
     */
    private static boolean compareLinks(PdfReader reader1, PdfReader reader2) {
        int numPages1 = reader1.getNumberOfPages();
        int numPages2 = reader2.getNumberOfPages();

        if (numPages1 != numPages2) {
            logger.log(Level.INFO, "Los archivos PDF tienen un número diferente de páginas.");
            return false;
        }

        for (int i = 1; i <= numPages1; i++) {
            PdfDictionary pageDict1 = reader1.getPageN(i);
            PdfDictionary pageDict2 = reader2.getPageN(i);

            PdfArray annotations1 = pageDict1.getAsArray(PdfName.ANNOTS);
            PdfArray annotations2 = pageDict2.getAsArray(PdfName.ANNOTS);

            if (annotations1 == null || annotations2 == null) {
                // Uno de los archivos PDF no tiene enlaces en esta página
                if (annotations1 == null && annotations2 == null) {
                    // Ambos archivos no tienen enlaces, por lo que son iguales en esta página
                    continue;
                } else {
                    // Solo uno de los archivos tiene enlaces, por lo que son diferentes en esta página
                    logger.log(Level.INFO, "Los enlaces internos de la página " + i + " son diferentes.");
                    return false;
                }
            }

            if (!annotations1.toString().equals(annotations2.toString())) {
                logger.log(Level.INFO, "Los enlaces internos de la página " + i + " son diferentes.");
                return false;
            }
        }

        return true;
    }
}




/**
*En este código, abrimos y leemos ambos archivos PDF utilizando PdfReader. Luego, comparamos los marcadores utilizando
* el método compareBookmarks, y los enlaces intrnos utilizando el método compareLinks.

En el método compareBookmarks, obtenemos los diccionarios de marcadores de cada archivo utilizando
* reader.getCatalog().getAsDict(PdfName.OUTLINES). Comparmos los diccionarios de marcadores convertidos a cadenas utilizando toString(). Si los diccionarios de marcadores son iguales, se devuelve true.

En el método compareLinks, iteramos sobre cada página de los archivos PDF y obtenemos los diccionarios
* de anotaciones de cada página utilizando reader.getPageN(i).getAsArray(PdfName.ANNOTS). Comparamos
* los diccionarios de anotaciones convertidos a cadenas utilizando toString(). Si los diccionarios
* de anotaciones de todas las páginas son iguales, se devuelve true.

*Si los marcadores y los enlaces internos son iguales, se imprimen mensajes indicando que los marcadores
 */