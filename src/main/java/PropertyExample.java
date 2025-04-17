import java.io.*;
import java.util.*;

public class PropertyExample {
    public static void main(String[] args) throws IOException {
        // Definir el archivo .properties
        File file = new File("mi_archivo.properties");

        // Leer el archivo con UTF-8
        FileInputStream fis = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

        Properties properties = new Properties();
        properties.load(reader);
        reader.close();

        // Escribir el archivo con UTF-8
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");
        properties.store(writer, "Comentarios");
        writer.close();
    }
}
