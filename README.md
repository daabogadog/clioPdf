# clioPdf - Biblioteca de Comparación y Análisis de Archivos PDF

clioPdf es una biblioteca de Java que permite realizar comparaciones y análisis avanzados en archivos PDF. Esta biblioteca es útil para tareas como verificar la igualdad entre dos archivos PDF, comparar metadatos, explorar marcadores y enlaces, y más.

## Características clave

- **Comparación de PDF:** Compara dos archivos PDF y detecta diferencias en su contenido.
- **Comparación de Metadatos:** Verifica la igualdad de metadatos en archivos PDF.
- **Exploración de Marcadores:** Comprueba si los marcadores (bookmarks) de dos PDF son idénticos.
- **Análisis de Enlaces:** Detecta enlaces internos en las páginas de un PDF y verifica su igualdad.

## Uso básico

Para usar clioPdf en tu proyecto, simplemente agrégalo como una dependencia en tu administrador de dependencias de construcción (por ejemplo, Gradle o Maven). Luego, puedes utilizar las clases proporcionadas para realizar tareas de comparación y análisis en tus archivos PDF.

## Ejemplos

Aquí hay un ejemplo básico de cómo comparar dos archivos PDF:

```java
if (PDFComparator.comparePDFs("archivo1.pdf", "archivo2.pdf")) {
    System.out.println("Los archivos PDF son iguales.");
} else {
    System.out.println("Los archivos PDF son diferentes.");
}
