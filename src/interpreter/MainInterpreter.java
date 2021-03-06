package interpreter;

import java.io.IOException;

import ui.UIApp;

public class MainInterpreter {

	public static void main(String[] args) throws IOException {
		
		if(args.length >= 2){
			System.out.println("Compilando archivo de reglas...");
			String compileResult = Interpreter.compileRules(args[0]);
			if(compileResult.isEmpty()){
				System.out.println("Compilacion exitosa.");
			}
			else{
				System.out.println(compileResult);
			}
			String result = Interpreter.execute(args[1]);
			if(args.length == 3){
				Interpreter.generateHtmlFile(result, args[2]);
			    
			}else{
				Interpreter.generateHtmlFile(result, "result");
			}
		}
		
		if(args.length == 0){
			System.out.println("Ejecutar el frontend");
			UIApp ui = new UIApp();
			ui.main(args);
		}

	}

}
