package io.github.dabogadog;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase compara las propiedades de las páginas de dos archivos PDF.
 */
public class PagePropertiesComparator {

    private static final Logger logger = Logger.getLogger(PagePropertiesComparator.class.getName());

    /**
     * Compara las propiedades de las páginas de dos archivos PDF.
     *
     * @param filePath1 Ruta al primer archivo PDF.
     * @param filePath2 Ruta al segundo archivo PDF.
     * @return true si las propiedades de las páginas son iguales, false si son diferentes o si ocurre un error.
     */
    public static boolean comparePageProperties(String filePath1, String filePath2) {
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
            } else {
                boolean areEqual = true;

                for (int i = 1; i <= numPages1; i++) {
                    Rectangle pageSize1 = reader1.getPageSize(i);
                    Rectangle pageSize2 = reader2.getPageSize(i);

                    if (!areRectanglesEqual(pageSize1, pageSize2)) {
                        areEqual = false;
                        logger.log(Level.INFO, "Las propiedades de la página " + i + " son diferentes.");
                        logger.log(Level.INFO, "Archivo 1: " + pageSize1);
                        logger.log(Level.INFO, "Archivo 2: " + pageSize2);
                        logger.log(Level.INFO, "");
                    }
                }

                reader1.close();
                reader2.close();
                return areEqual;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Ocurrió un error durante la comparación de propiedades de páginas:", e);
            return false;
        }
    }

    private static boolean areRectanglesEqual(Rectangle rect1, Rectangle rect2) {
        float epsilon = 0.001f; // Valor de tolerancia para la comparación de dimensiones
        return Math.abs(rect1.getWidth() - rect2.getWidth()) <= epsilon &&
                Math.abs(rect1.getHeight() - rect2.getHeight()) <= epsilon;
    }
}



/**
*En este código, he cread un método auxiliar llamado areRectanglesEqual que compara las dimensiones de tamaño de página
*  de dos rectángulos (en este caso, las propiedades de página). Utilizo un valor de tolerancia (epsilon) para la
* comparación, lo que significa que las diferencias en dimensiones dentro de ese valor se consideran iguales.

Si las dimensiones de tamaño de página difieren más allá del valor de tolerancia, se consideran diferentes y
* se imprime un mensaje con las propiedades de página de ambos archivos.
* */