package Lex;

public class Token {
	private String cod;
	private Object attr;
	
	public Token(String cod,Object attr) {
		
		this.cod = cod;
		this.attr = attr;	
	}
	
	public String getCode() { return this.cod; }

	public Object getAttr() { return this.attr; }

	@Override
	public String toString() {
		if( attr == null) {
			return "< " + cod + " , " +" >";
		}
		else if( attr instanceof Integer) {
			int num = (int) attr;
			return "< " + cod + " , " + num +" >";
		}
		else{
			String cad = (String) attr;
			return "< " + cod + " , " + cad +" >";
		}
	}

	public boolean equals(Token t) {
		boolean eqCode = t.getCode().equals(this.cod);
		boolean eqAttr = false;
		
		if(attr == null && t.getAttr() == null) 
		{
			eqAttr = true;
		}
		else if(attr instanceof Integer && t.getAttr() instanceof Integer) 
		{
			eqAttr = (Integer)attr == (Integer)(t.getAttr());
		}
		
		else if(attr instanceof String && t.getAttr() instanceof String)
		{
			eqAttr = ((String)attr).equals( (String)(t.getAttr()) );
		}
		else return false;
		return eqCode && eqAttr;
	}
}