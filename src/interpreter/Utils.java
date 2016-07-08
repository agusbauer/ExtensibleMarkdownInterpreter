package interpreter;

public class Utils {
	
	public static final String SPECIAL_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
	
	public static String addEscapeCharacters(String expression){
        String result = expression;
        String[] charactersToEscape = {"*","+","{","}"};
        for (String character : charactersToEscape) {
            result = result.replace(character, "\\" + character);
        }
        return result;
    }
	
    public static String removeTokens(String text, String tokens){
    	String result = text;
    	/*for (char ch: tokens.toCharArray()) {
    		result = result.replace(Character.toString(ch) ,""); 
    	}*/
    	return result.replace(tokens ,"");
    }

}
