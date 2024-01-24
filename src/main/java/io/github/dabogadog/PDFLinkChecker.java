package io.github.dabogadog;

import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase verifica los enlaces (URLs) presentes en un archivo PDF.
 */
public class PDFLinkChecker {
    private static final Logger logger = Logger.getLogger(PDFLinkChecker.class.getName());

    /**
     * Verifica los enlaces (URLs) presentes en un archivo PDF.
     *
     * @param filePath Ruta al archivo PDF a verificar.
     */
    public static void checkLinksInPDF(String filePath) {
        try {
            PdfReader reader = new PdfReader(filePath);
            int numPages = reader.getNumberOfPages();

            for (int i = 1; i <= numPages; i++) {
                PdfDictionary pageDict = reader.getPageN(i);
                PdfArray annotsArray = pageDict.getAsArray(PdfName.ANNOTS);

                if (annotsArray != null) {
                    for (int j = 0; j < annotsArray.size(); j++) {
                        PdfDictionary annotDict = annotsArray.getAsDict(j);
                        PdfName subType = annotDict.getAsName(PdfName.SUBTYPE);

                        if (PdfName.LINK.equals(subType)) {
                            PdfDictionary actionDict = annotDict.getAsDict(PdfName.A);
                            if (actionDict != null && PdfName.URI.equals(actionDict.getAsName(PdfName.S))) {
                                PdfString uri = actionDict.getAsString(PdfName.URI);
                                if (uri != null) {
                                    String urlString = uri.toUnicodeString();
                                    validateLink(urlString);
                                }
                            }
                        }
                    }
                }
            }

            reader.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ocurrió un error durante la verificación de enlaces:", e);
        }
    }

    /**
     * Valida un enlace (URL) verificando su disponibilidad.
     *
     * @param urlString URL a validar.
     */
    private static void validateLink(String urlString) {
        try {
            URL url = new URL(urlString);

            if ("http".equals(url.getProtocol()) || "https".equals(url.getProtocol())) {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();

            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Enlace válido: " + urlString);
            } else {
                System.out.println("Enlace roto: " + urlString);
                System.out.println("Código de respuesta: " + responseCode);
                System.out.println("Mensaje de respuesta: " + responseMessage);
            }

            connection.disconnect();
            } else {
                System.out.println("Enlace no válido debido al protocolo: " + urlString);
            }
        } catch (UnknownHostException e) {
            System.out.println("Nombre de host no válido en el enlace: " + urlString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

