import java.time.Year;
import java.util.ArrayList;

/**
 * Clase con la lógica del catálogo de películas.
 * No usa nada de Swing: la Ventana solo llama a estos métodos
 * y muestra los mensajes cuando se lanza una excepción.
 */
public class CatalogoPeliculas {

    // Géneros permitidos (los mismos del JComboBox)
    public static final String[] GENEROS = {
            "Ciencia ficción", "Drama", "Aventura", "Comedia",
            "Animación", "Suspenso", "Documental"
    };

    // Lista principal: SIEMPRE ordenada por código ascendente
    private ArrayList<Pelicula> peliculas;

    // ---------------------------------------------------------------
    // Constructor: se carga una sola vez y se ordena por código
    // ---------------------------------------------------------------
    public CatalogoPeliculas() {
        peliculas = DatosIniciales.cargar();
        ordenarPorCodigoBurbuja();
    }

    // ---------------------------------------------------------------
    // Consultas básicas
    // ---------------------------------------------------------------
    public int getTotal() {
        return peliculas.size();
    }

    /** Devuelve una lista nueva con los MISMOS objetos (no copia las películas). */
    public ArrayList<Pelicula> getPeliculas() {
        return new ArrayList<>(peliculas);
    }

    // ---------------------------------------------------------------
    // Ordenamiento burbuja por código (ascendente)
    // ---------------------------------------------------------------
    public void ordenarPorCodigoBurbuja() {
        int n = peliculas.size();
        for (int i = 0; i < n - 1; i++) {
            boolean huboCambio = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (peliculas.get(j).getCodigo() > peliculas.get(j + 1).getCodigo()) {
                    Pelicula auxiliar = peliculas.get(j);
                    peliculas.set(j, peliculas.get(j + 1));
                    peliculas.set(j + 1, auxiliar);
                    huboCambio = true;
                }
            }
            if (!huboCambio) {
                break; // ya está ordenada
            }
        }
    }

}