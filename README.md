# Estructura

JAVA_FX_EMERGENCYKIDS

EmergencyKids/
│
├── README.md                # Instrucciones de instalación y ejecución
├── javafx-sdk-25.0.1/       # Librerías JavaFX (si se incluyen en el ZIP)
│   ├── lib/
│   └── ...
│
├── src/                     # Código fuente del proyecto
│   ├── main/java/           # Clases principales
│   │   ├── Main.java        # Clase que inicia la aplicación JavaFX
│   │   ├── LogicaJuego.java # Lógica de negocio del juego
│   │   ├── estilos.css      # Estilos para la interfaz gráfica
│   │   └── img/             # Recursos gráficos (imágenes)
│   │
│   └── test/java/           # Pruebas unitarias
│       └── LogicaJuegoTest.java
│
├── out/                     # Clases compiladas (generadas por javac)
│   ├── main/
│   └── test/
│
└── .vscode/                 # Configuración para VS Code
    ├── launch.json
    └── settings.json


# compilar Junit

# Desde la raíz de tu proyecto (JAVAFX_EMERGENCYKIDS)
java -jar javafx-sdk-25.0.1/lib/junit-platform-console-standalone-1.10.2.jar --classpath out/ --scan-classpath --details=tree
# juego-Emergency-kids
