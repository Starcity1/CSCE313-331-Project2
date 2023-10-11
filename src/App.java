/**
 * The main entry point for the application.
 * <p>
 * This method launches the JavaFX application by invoking the {@link GUI#launch(Class, String[])}
 * method with the specified GUI class and command-line arguments.
 * </p>
 * <p>
 * To run this application, use the following command:
 * <pre>
 *     javac --module-path /lib --add-modules javafx.controls,javafx.fxml -d bin src/App.java
 * </pre>
 * </p>
 *
 * @param args The command-line arguments passed to the application.
 */

public class App {
     // run with javac --module-path /lib --add-modules javafx.controls,javafx.fxml -d bin src/App.java
    public static void main(String[] args) {
        GUI.launch(GUI.class, args);
    }
}
