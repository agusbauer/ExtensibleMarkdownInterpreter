package interpreter;

public class MainInterpreter {

	public static void main(String[] args) {
		
		if(args.length == 2){
			System.out.println(Interpreter.execute(args[0], args[1]));
		}
		
		if(args.length == 0){
			System.out.println("Ejecutar el frontend");
		}

	}

}
