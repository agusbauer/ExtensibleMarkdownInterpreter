package parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import jflex.*;
import static jflex.Main.generate; 

public class Main {

	public static void main(String[] args) throws Exception {
		
		//File file=new File("src/tesis/Lexer.flex");
       // generate(file);
		String[] lexFile = { "src/parser/Lexer.flex" };
		String[] asintactico = {"-parser", "Parser", "src/parser/Cup.cup"};
		jflex.Main.main(lexFile);
        try {
            java_cup.Main.main(asintactico);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean mvAS = moverArch("Parser.java");
        boolean mvSym= moverArch("sym.java");
      
        String[] archivoPrueba = {"src/tesis/rules.txt"};

	}
	
	public static boolean moverArch(String archNombre) {
        boolean efectuado = false;
        File arch = new File(archNombre);
        if (arch.exists()) {
            //System.out.println("\n*** Moviendo " + arch + " \n***");
            Path currentRelativePath = Paths.get("");
            String nuevoDir = currentRelativePath.toAbsolutePath().toString()
                    + File.separator + "src" + File.separator
                    + "parser" + File.separator + arch.getName();
            File archViejo = new File(nuevoDir);
            archViejo.delete();
            if (arch.renameTo(new File(nuevoDir))) {
                //System.out.println("\n*** Generado " + archNombre + "***\n");
                efectuado = true;
            } else {
                System.out.println("\n*** No movido " + archNombre + " ***\n");
            }

        } else {
            System.out.println("\n*** Codigo no existente ***\n");
        }
        return efectuado;
    }

}
