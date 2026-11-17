import java.time.Year;
import java.util.ArrayList;


public class CatalogoPeliculas {

    public static final String[] GENEROS = {
            "Ciencia ficción", "Drama", "Aventura", "Comedia",
            "Animación", "Suspenso", "Documental"
    };

    private ArrayList<Pelicula> peliculas;


    public CatalogoPeliculas() {
        peliculas = DatosIniciales.cargar();
        ordenarPorCodigoBurbuja();
    }

    public int getTotal() {
        return peliculas.size();
    }

    public ArrayList<Pelicula> getPeliculas() {
        return new ArrayList<>(peliculas);
    }

    public void ordenarPorCodigoBurbuja() {
        Pelicula aux;
        for (int i = 0; i < peliculas.size() - 1; i++) {
            for (int j = 0; j < peliculas.size() - 1 - i; j++) {
                if (peliculas.get(j).getCodigo() > peliculas.get(j + 1).getCodigo()) {
                    aux = peliculas.get(j);
                    peliculas.set(j, peliculas.get(j + 1));
                    peliculas.set(j + 1, aux);
                }
            }
        }
    }

    public int buscarIndicePorCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código debe ser un número positivo.");
        }
        int inicio = 0;
        int fin = peliculas.size() - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            int codigoMedio = peliculas.get(medio).getCodigo();

            if (codigoMedio == codigo) {
                return medio;
            } else if (codigoMedio < codigo) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }


    public Pelicula obtenerPorCodigo(int codigo) {
        int indice = buscarIndicePorCodigo(codigo);
        if (indice == -1) {
            return null;
        }
        return peliculas.get(indice);
    }


    public ArrayList<Pelicula> buscarCoincidencias(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Escriba un texto para buscar.");
        }
        String consulta = texto.trim().toLowerCase();
        ArrayList<Pelicula> resultados = new ArrayList<>();

        for (int i = 0; i < peliculas.size(); i++) {
            Pelicula p = peliculas.get(i);
            boolean enTitulo = p.getTitulo().toLowerCase().contains(consulta);
            boolean enDirector = p.getDirector().toLowerCase().contains(consulta);

            if (enTitulo || enDirector) {
                resultados.add(p);
            }
        }
        return resultados;
    }

    private void validarDatos(String titulo, String director, String genero, int anio) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        if (director == null || director.trim().isEmpty()) {
            throw new IllegalArgumentException("El director es obligatorio.");
        }
        if (genero == null || !generoValido(genero)) {
            throw new IllegalArgumentException("Seleccione un género válido.");
        }
        int anioActual = Year.now().getValue();
        if (anio < 1 || anio > anioActual) {
            throw new IllegalArgumentException("El año debe estar entre 1 y " + anioActual + ".");
        }
    }

    private boolean generoValido(String genero) {
        for (int i = 0; i < GENEROS.length; i++) {
            if (GENEROS[i].equals(genero)) {
                return true;
            }
        }
        return false;
    }

    public void registrar(Pelicula pelicula) {
        if (pelicula == null) {
            throw new IllegalArgumentException("La película no puede ser nula.");
        }
        if (pelicula.getCodigo() <= 0) {
            throw new IllegalArgumentException("El código debe ser un número positivo.");
        }
        if (buscarIndicePorCodigo(pelicula.getCodigo()) != -1) {
            throw new IllegalArgumentException("Ya existe una película con el código "
                    + pelicula.getCodigo() + ".");
        }
        if (pelicula.getMeGusta() != 0) {
            throw new IllegalArgumentException("Una película nueva debe iniciar con 0 me gusta.");
        }

        validarDatos(pelicula.getTitulo(), pelicula.getDirector(),
                pelicula.getGenero(), pelicula.getAnio());

        peliculas.add(pelicula);
        ordenarPorCodigoBurbuja();
    }

    public void editarPelicula(int codigo, String titulo, String director,
                               String genero, int anio) {
        int indice = buscarIndicePorCodigo(codigo);
        if (indice == -1) {
            throw new IllegalArgumentException("No existe una película con el código " + codigo + ".");
        }

        validarDatos(titulo, director, genero, anio);

        Pelicula p = peliculas.get(indice);
        p.setTitulo(titulo.trim());
        p.setDirector(director.trim());
        p.setGenero(genero);
        p.setAnio(anio);
    }
}

