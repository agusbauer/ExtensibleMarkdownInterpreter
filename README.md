# ExtensibleMarkdownInterpreter

## Guia de instalación

Para poder ejecutar la herramienta es necesario tener java en el equipo, se puede descargar desde [aqui](https://www.java.com/es/download/). 
Para descargar ExMark debemos hacer click en "clone or download" y luego "download zip" como se puede ver en la imagen:

![exMark descarga](https://k60.kn3.net/F/D/5/C/D/C/A56.png)

Luego de descargar el zip debemos extraelo en cualquier carpeta.
	
### Ejecución de la interfaz gráfica

Para abrir la interfaz gráfica simplemente debemos ejecutar el archivo *ExMark.jar* haciendo doble click en el
	
### Ejecución mediante consola
Para ejectuar la herramienta mediante la linea de comandos debemos ejectuar la siguiente linea estando posicionados en la carpeta donde se encuentra el archivo
`java -jar ExMark.jar archivoReglas.em archivoTexto.txt`
Tener en cuenta que archivoReglas.em y archivoTexto.txt deben estar ubicados en la misma carpeta que ExMark.jar

### Manual de usuario
El manual de usuario está escrito utilizando las reglas por defecto de ExMark.
Para acceder al manual debemos ejecutar lo siguiente en la consola:
`java -jar ExMark.jar defaultHtmlRules.em manualDeUsuario.txt`
Esto producirá como resultado el archivo **manualDeUsuario.html**, al abrirlo con cualquier explorador se deberia poder visualizar el manual de usuario.

