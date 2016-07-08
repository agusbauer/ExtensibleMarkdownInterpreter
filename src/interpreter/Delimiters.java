package interpreter;

public class Delimiters {
	
	private String beginToken;
	private String endToken;
	
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
		return beginToken + endToken;
	}
	
	
	
}