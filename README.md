# Ejercicios 1_5 I - Personas
## DM2 DEIN 2024-2025
### Alesandro Quirós Gobbato

La estructura del proyecto es la siguiente:
- `src > main`:
    - `java > com.alesandro.ejercicio15i`:
        - `PersonasApplication.java`: Clase que lanza la aplicación
        - `controller`:
            - `PersonasController.java`: Clase que controla los eventos de la ventana principal de la aplicación
        - `dao`:
            - `DaoPersona.java`: Clase que hace las operaciones con la base de datos del modelo Persona
        - `db`:
            - `DBConnect.java`: Clase que se conecta a la base de datos
        - `model`:
            - `Persona.java`: Clase que define el objeto Persona
    - `resources`:
      - `css`: Estilos de la aplicación
      - `fxml`:
        - `Personas.fxml`: Ventana principal de la aplicación
      - `idiomas`: Carpeta conteniendo el texto de cada idioma
      - `images`: Imágenes de la aplicación