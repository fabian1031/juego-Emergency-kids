import main.java.LogicaJuego; // Importamos tu lógica (el cerebro)

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    private Label lblPuntosGlobal;
    
    // INSTANCIA DE LA LÓGICA (Separada del diseño)
    private LogicaJuego logica = new LogicaJuego();
    
    private int nivelActual = 0;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        lblPuntosGlobal = new Label("Puntos: 0");
        lblPuntosGlobal.setFont(new Font("Arial", 18));
        lblPuntosGlobal.setStyle("-fx-font-weight: bold; -fx-text-fill: #333;");
        
        mostrarPantallaInicio();
    }

    // ---------------------------------------------------------
    // PANTALLA INICIO
    // ---------------------------------------------------------
    private void mostrarPantallaInicio() {
        logica.reiniciarJuego();
        actualizarLabelPuntos();

        Label titulo = new Label("¡Bienvenido a Emergency Kids!");
        titulo.setFont(new Font("Comic Sans MS", 32));
        titulo.setStyle("-fx-text-fill: #2E8B57; -fx-font-weight: bold;");

        // Imagen de portada
        ImageView portada = new ImageView(cargarImagen("portada.png"));
        portada.setFitWidth(240); 
        portada.setFitHeight(240); 
        portada.setPreserveRatio(true);

        Button btn = new Button("Ver Instrucciones");
        btn.setStyle(estiloBoton());
        btn.setOnAction(e -> mostrarInstrucciones());

        VBox layout = new VBox(25, titulo, portada, btn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(#FFF7E6, #FCEFE3);");
        
        window.setScene(new Scene(layout, 800, 600));
        window.setTitle("Emergency Kids - Inicio");
        window.show();
    }

    // ---------------------------------------------------------
    // INSTRUCCIONES (Diseño Mejorado)
    // ---------------------------------------------------------
    private void mostrarInstrucciones() {
        Label titulo = new Label("¿Qué haremos?");
        titulo.setFont(new Font("Comic Sans MS", 32));
        titulo.setStyle("-fx-text-fill: #2E8B57; -fx-font-weight: bold;");

        VBox listaInstrucciones = new VBox(15); // Espacio entre tarjetas
        listaInstrucciones.setAlignment(Pos.CENTER);
        
        // Creamos las tarjetas de instrucciones
        HBox fila1 = crearFilaInstruccion("casco.png", "Nivel 1: Bicicleta.\nElige los objetos de protección.");
        HBox fila2 = crearFilaInstruccion("vendas.png", "Nivel 2: Botiquín.\nArrastra lo necesario para curar.");
        HBox fila3 = crearFilaInstruccion("semaforo_rojo.png", "Nivel 3: Semáforo.\nCruza la calle solo cuando sea seguro.");
        // Si no tienes imagen de teléfono, usamos la portada o una genérica
        HBox fila4 = crearFilaInstruccion("portada.png", "Nivel 4: Emergencia.\nMarca el número 123 en el teléfono.");

        listaInstrucciones.getChildren().addAll(fila1, fila2, fila3, fila4);

        Button btn = new Button("¡Entendido, a jugar!");
        btn.setStyle(estiloBoton());
        btn.setFont(new Font(18));
        btn.setOnAction(e -> {
            nivelActual = 1;
            mostrarNivelBicicleta();
        });

        VBox layout = new VBox(25, titulo, listaInstrucciones, btn);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: linear-gradient(#E8F7F2, #FFFFFF);");

        window.setScene(new Scene(layout, 850, 700)); // Ventana un poco más alta
    }

    // Helper para crear tarjeta bonita de instrucción
    private HBox crearFilaInstruccion(String imgName, String texto) {
        ImageView iv = new ImageView(cargarImagen(imgName));
        iv.setFitWidth(70); iv.setFitHeight(70); iv.setPreserveRatio(true);

        Label lbl = new Label(texto);
        lbl.setFont(new Font("Arial", 18));
        lbl.setWrapText(true);
        lbl.setMaxWidth(500);

        HBox fila = new HBox(20, iv, lbl);
        fila.setAlignment(Pos.CENTER_LEFT);
        fila.setPadding(new Insets(15));
        fila.setMaxWidth(700);
        
        // Estilo CSS estilo tarjeta
        fila.setStyle(
            "-fx-background-color: rgba(255, 255, 255, 0.9);" + 
            "-fx-background-radius: 15; -fx-border-radius: 15;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 5);"
        );
        return fila;
    }

    // ---------------------------------------------------------
    // NIVEL 1: BICICLETA
    // ---------------------------------------------------------
    private void mostrarNivelBicicleta() {
        Label titulo = new Label("Nivel 1: Bicicleta");
        titulo.setFont(new Font("Comic Sans MS", 24));
        Label instruccion = new Label("Selecciona los 2 objetos de protección (+15 pts)");
        instruccion.setFont(new Font(16));
        
        GridPane grid = new GridPane();
        grid.setHgap(20); grid.setVgap(20); grid.setAlignment(Pos.CENTER);

        // Control local de aciertos
        final int[] aciertos = {0};
        Runnable checkMeta = () -> {
            aciertos[0]++;
            // Preguntamos a la lógica si pasamos (Meta: 2)
            if (logica.esMetaCumplida(aciertos[0], 2)) siguienteNivel();
        };

        grid.add(crearBotonJuego("casco.png", true, checkMeta, 15, 5), 0, 0);
        grid.add(crearBotonJuego("helado.png", false, () -> {}, 15, 5), 1, 0);
        grid.add(crearBotonJuego("rodilleras.png", true, checkMeta, 15, 5), 0, 1);
        grid.add(crearBotonJuego("pelota.png", false, () -> {}, 15, 5), 1, 1);

        VBox layout = new VBox(20, titulo, instruccion, grid, lblPuntosGlobal);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #FFFBF0;");
        window.setScene(new Scene(layout, 800, 600));
    }

    // ---------------------------------------------------------
    // NIVEL 2: BOTIQUÍN (Drag & Drop)
    // ---------------------------------------------------------
    private void mostrarNivelBotiquin() {
        Label titulo = new Label("Nivel 2: Botiquín");
        titulo.setFont(new Font("Comic Sans MS", 24));
        Label instruccion = new Label("Arrastra al botiquín lo que sirve para curar (+10 pts)");
        instruccion.setFont(new Font(16));

        // El Botiquín (Destino)
        StackPane botiquin = new StackPane();
        botiquin.setPrefSize(160, 160);
        botiquin.setStyle("-fx-background-color: #FF4D4D; -fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 5; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");
        botiquin.getChildren().add(new Label("+") {{ setFont(new Font("Arial", 80)); setTextFill(javafx.scene.paint.Color.WHITE); }});

        // Los Objetos (Origen)
        TilePane objetosPane = new TilePane();
        objetosPane.setHgap(20); objetosPane.setVgap(20); objetosPane.setAlignment(Pos.CENTER);
        objetosPane.setPrefColumns(4);

        final int[] colocados = {0};

        // Helper para añadir objetos arrastrables
        java.util.function.BiConsumer<String, Boolean> addObj = (imgName, esCorrecto) -> {
            ImageView iv = new ImageView(cargarImagen(imgName));
            iv.setFitWidth(80); iv.setFitHeight(80); iv.setPreserveRatio(true);
            
            iv.setOnDragDetected(e -> {
                var db = iv.startDragAndDrop(TransferMode.MOVE);
                var cc = new ClipboardContent();
                cc.putString(esCorrecto ? "SI" : "NO");
                db.setContent(cc);
                e.consume();
            });
            objetosPane.getChildren().add(iv);
        };

        // Añadimos tus imágenes
        addObj.accept("vendas.png", true);
        addObj.accept("alcohol.png", true);
        addObj.accept("curitas.png", true);
        addObj.accept("termometro.png", true);
        addObj.accept("lapiz.png", false);
        addObj.accept("galleta.png", false);
        addObj.accept("pelota.png", false);

        // Lógica del Drop
        botiquin.setOnDragOver(e -> {
            if (e.getGestureSource() != botiquin && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
            e.consume();
        });

        botiquin.setOnDragDropped(e -> {
            boolean correcto = "SI".equals(e.getDragboard().getString());
            
            // LLAMADA A LA LÓGICA
            logica.procesarAccion(correcto, 10, 5);
            actualizarLabelPuntos();

            if (correcto) {
                colocados[0]++;
                ((ImageView)e.getGestureSource()).setVisible(false); // Desaparece el objeto
                // Meta Nivel 2: 4 objetos
                if (logica.esMetaCumplida(colocados[0], 4)) siguienteNivel();
            } else {
                mostrarAlerta("¡Ese objeto no va en el botiquín!");
            }
            e.setDropCompleted(true);
            e.consume();
        });

        VBox layout = new VBox(20, titulo, instruccion, lblPuntosGlobal, botiquin, objetosPane);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F0FFFF;");
        window.setScene(new Scene(layout, 900, 650));
    }

    // ---------------------------------------------------------
    // NIVEL 3: SEMÁFORO
    // ---------------------------------------------------------
    private void mostrarNivelSemaforo() {
        Label titulo = new Label("Nivel 3: Semáforo");
        titulo.setFont(new Font("Comic Sans MS", 24));
        Label instruccion = new Label("¿En qué color es seguro cruzar la calle? (+15 pts)");
        instruccion.setFont(new Font(16));

        // Rojo = Cruzar (Simulación de semáforo peatonal o coches parados)
        Button btnRojo = crearBotonJuego("semaforo_rojo.png", true, this::siguienteNivel, 15, 5);
        
        // Verde = Peligro (Coches avanzando)
        Button btnVerde = crearBotonJuego("semaforo_verde.png", false, () -> {}, 15, 5);

        HBox box = new HBox(50, btnRojo, btnVerde);
        box.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, titulo, instruccion, box, lblPuntosGlobal);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #FFFDFD;");
        window.setScene(new Scene(layout, 800, 600));
    }

    // ---------------------------------------------------------
    // NIVEL 4: TELÉFONO
    // ---------------------------------------------------------
    private void mostrarNivelTelefono() {
        Label titulo = new Label("Nivel 4: Llamada de Emergencia");
        titulo.setFont(new Font("Comic Sans MS", 24));
        Label instruccion = new Label("Marca el 123 y llama (+20 pts)");
        instruccion.setFont(new Font(16));
        
        Label pantalla = new Label("");
        pantalla.setStyle("-fx-border-color: #555; -fx-background-color: white; -fx-padding: 10; -fx-font-size: 28; -fx-min-width: 200; -fx-alignment: center; -fx-background-radius: 5;");
        
        GridPane teclado = new GridPane();
        teclado.setAlignment(Pos.CENTER); 
        teclado.setHgap(10); 
        teclado.setVgap(10);
        
        StringBuilder input = new StringBuilder();

        // Botones 1-9
        for (int i = 1; i <= 9; i++) {
            String n = String.valueOf(i);
            Button b = new Button(n);
            b.setPrefSize(60, 60);
            b.setStyle("-fx-font-size: 20; -fx-background-radius: 30;");
            b.setOnAction(e -> { input.append(n); pantalla.setText(input.toString()); });
            teclado.add(b, (i-1)%3, (i-1)/3);
        }
        // Botones *, 0, #
        Button ast = new Button("*"); ast.setPrefSize(60, 60); ast.setStyle("-fx-font-size: 20; -fx-background-radius: 30;");
        ast.setOnAction(e -> { input.append("*"); pantalla.setText(input.toString()); });
        
        Button cero = new Button("0"); cero.setPrefSize(60, 60); cero.setStyle("-fx-font-size: 20; -fx-background-radius: 30;");
        cero.setOnAction(e -> { input.append("0"); pantalla.setText(input.toString()); });
        
        Button hash = new Button("#"); hash.setPrefSize(60, 60); hash.setStyle("-fx-font-size: 20; -fx-background-radius: 30;");
        hash.setOnAction(e -> { input.append("#"); pantalla.setText(input.toString()); });

        teclado.add(ast, 0, 3);
        teclado.add(cero, 1, 3);
        teclado.add(hash, 2, 3);

        Button btnLlamar = new Button("LLAMAR");
        btnLlamar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 20;");
        btnLlamar.setOnAction(e -> {
            // VALIDACIÓN CON LA CLASE LÓGICA
            boolean valido = logica.esNumeroEmergenciaValido(input.toString());
            logica.procesarAccion(valido, 20, 5);
            actualizarLabelPuntos();

            if (valido) {
                siguienteNivel();
            } else {
                mostrarAlerta("Número incorrecto. Intenta de nuevo.");
                input.setLength(0);
                pantalla.setText("");
            }
        });

        Button btnBorrar = new Button("Borrar");
        btnBorrar.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 20;");
        btnBorrar.setOnAction(e -> { input.setLength(0); pantalla.setText(""); });

        HBox acciones = new HBox(20, btnBorrar, btnLlamar);
        acciones.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, titulo, instruccion, pantalla, teclado, acciones, lblPuntosGlobal);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #F5F5F5;");
        window.setScene(new Scene(layout, 600, 700));
    }

    // ---------------------------------------------------------
    // PANTALLA FINAL
    // ---------------------------------------------------------
    private void mostrarPantallaFinal() {
        Label titulo = new Label("¡Juego Completado!");
        titulo.setFont(new Font("Comic Sans MS", 36));
        
        Label pts = new Label("Puntos Finales: " + logica.getPuntos());
        pts.setFont(new Font("Arial", 24));
        
        // OBTENEMOS MEDALLA DE LA LÓGICA
        String nombreMedalla = logica.determinarMedalla();
        Label medal = new Label("Ganaste medalla de: " + nombreMedalla);
        medal.setFont(new Font("Arial Black", 20));
        
        // Color según medalla
        if (nombreMedalla.equals("ORO")) medal.setStyle("-fx-text-fill: #FFD700;");
        else if (nombreMedalla.equals("PLATA")) medal.setStyle("-fx-text-fill: #C0C0C0;");
        else if (nombreMedalla.equals("BRONCE")) medal.setStyle("-fx-text-fill: #CD7F32;");
        else medal.setStyle("-fx-text-fill: #333;");

        Button btnReiniciar = new Button("Volver al Inicio");
        btnReiniciar.setStyle(estiloBoton());
        btnReiniciar.setOnAction(e -> mostrarPantallaInicio());

        Button btnSalir = new Button("Salir");
        btnSalir.setStyle("-fx-background-color: #FF6B6B; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 20; -fx-background-radius: 10;");
        btnSalir.setOnAction(e -> window.close());

        HBox boxBtns = new HBox(20, btnReiniciar, btnSalir);
        boxBtns.setAlignment(Pos.CENTER);

        VBox layout = new VBox(30, titulo, pts, medal, boxBtns);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(#FFFCE6, #EAF7FF);");
        window.setScene(new Scene(layout, 700, 500));
    }

    // ---------------------------------------------------------
    // UTILIDADES Y HELPERS
    // ---------------------------------------------------------

    private void siguienteNivel() {
        nivelActual++;
        actualizarLabelPuntos();
        switch (nivelActual) {
            case 1: mostrarNivelBicicleta(); break;
            case 2: mostrarNivelBotiquin(); break;
            case 3: mostrarNivelSemaforo(); break;
            case 4: mostrarNivelTelefono(); break;
            default: mostrarPantallaFinal(); break;
        }
    }

    // Botón genérico que conecta con la lógica
    private Button crearBotonJuego(String imgName, boolean correcto, Runnable onSuccess, int premio, int castigo) {
        ImageView iv = new ImageView(cargarImagen(imgName));
        iv.setFitWidth(100); iv.setFitHeight(100); iv.setPreserveRatio(true);
        
        Button btn = new Button();
        btn.setGraphic(iv);
        btn.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        
        btn.setOnAction(e -> {
            btn.setDisable(true);
            logica.procesarAccion(correcto, premio, castigo);
            actualizarLabelPuntos();
            
            if (correcto) {
                onSuccess.run();
            } else {
                mostrarAlerta("¡Ups! Opción incorrecta.");
            }
        });
        return btn;
    }

    // Carga de imágenes segura
    private Image cargarImagen(String nombreArchivo) {
        try {
            return new Image("file:src/img/" + nombreArchivo);
        } catch (Exception e) {
            System.out.println("No se encontró la imagen: " + nombreArchivo);
            return null; 
        }
    }

    private void actualizarLabelPuntos() {
        if (lblPuntosGlobal != null) {
            lblPuntosGlobal.setText("Puntos: " + logica.getPuntos());
        }
    }

    private void mostrarAlerta(String msg) {
        Stage s = new Stage();
        s.setTitle("Aviso");
        Label l = new Label(msg);
        l.setFont(new Font(16));
        Button b = new Button("OK");
        b.setOnAction(e -> s.close());
        
        VBox v = new VBox(20, l, b);
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(20));
        s.setScene(new Scene(v, 300, 150));
        s.show();
    }

    private String estiloBoton() {
        return "-fx-background-color: #FFA500; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16; -fx-padding: 10 20; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);";
    }

    public static void main(String[] args) {
        launch(args);
    }
}