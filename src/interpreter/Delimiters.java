package interpreter;

public class Delimiters {
	
	private String beginToken;
	private String endToken;
	private boolean composed = false; //dice si es compuesto, o sea si tiene algun dato mas, se usa para las img y los liks
	
	public Delimiters() {
		this.beginToken = "";
		this.endToken = "";
	}
	
	public Delimiters(String beginToken, String endToken) {
		this.beginToken = beginToken;
		this.endToken = endToken;
	}
	
	public boolean areEmpties(){
		return beginToken.isEmpty() && endToken.isEmpty();
	}
	
	public boolean areEquals(){
		return beginToken.equals(endToken);
	}
	
	public boolean hasEndToken(){
		return !endToken.isEmpty();
	}
	
	public boolean hasBeginToken(){
		return !beginToken.isEmpty();
	}
	
	public boolean isComposed() {
		return composed;
	}

	public void setComposed(boolean composed) {
		this.composed = composed;
	}

	public String getBeginToken() {
		return beginToken;
	}
	
	public void setBeginToken(String beginToken) {
		this.beginToken = beginToken;
	}
	
	public String getEndToken() {
		return endToken;
	}
	
	public void setEndToken(String endToken) {
		this.endToken = endToken;
	}

	@Override
	public String toString() {
		return "["+beginToken + "," + endToken +"]";
	}
	
	
	
}
