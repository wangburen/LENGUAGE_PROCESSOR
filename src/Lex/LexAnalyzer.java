package Lex;

import Lex.Util.Action;
import Sym.SymbolTable;

public class LexAnalyzer {
	
	private static SymbolTable st = new SymbolTable(1); //TODO: ESTO HAY QUE HACER QUE LO LEA DE FUERA O ALGO ASI. TIENE Q BUSCAR LA TABLA ACTIVA, POR ESO ES PRIVATE STATIC. 
														   // EN RESUMEN: ESTA LINEA LA QUITAREMOS A FUTURO O ALGO ASI

	private FSM automata; 
	private Reader reader;
	private Writer writer;
	private char c;

	private final int MAX_STR = 64;
	private final int MAX_INT = 32767;
	private final String[] RES_WORDS= {"boolean", "do", "function", "get", "if", "int", "let", "put", "return", "string", "void", "while"};
	private final String[] LOGIC = {"true", "false"};

	private int line = 1;

	public LexAnalyzer(String sourceFileName, String destinyFileName) 
	{
		this.automata = new FSM();
		this.reader = new Reader(sourceFileName);
		this.writer = new Writer(destinyFileName);
		this.c = reader.read();
	}

	// TODO: DEFINIR ERORRES
	private void ErrorManager(String lex) throws Exception
	{
		//System.out.println(lex);
		//System.out.println(automata.getState());
		reader.close();
		writer.close();
		throw new Exception("ERROR ON LINE " + Integer.toString(line) + "!!!");
	}

	private boolean isFinalState(Util.State s)
	{
		if(s.equals(Util.State.F1) || s.equals(Util.State.F2) || s.equals(Util.State.F3) || s.equals(Util.State.F4) ||
		   s.equals(Util.State.F5) || s.equals(Util.State.F6) || s.equals(Util.State.F7) || s.equals(Util.State.F8) ||
		   s.equals(Util.State.F9) || s.equals(Util.State.F12)|| s.equals(Util.State.F13)|| s.equals(Util.State.F15)||
		   s.equals(Util.State.F16)|| s.equals(Util.State.F17)|| s.equals(Util.State.F20)|| s.equals(Util.State.F22)||
		   s.equals(Util.State.F24)|| s.equals(Util.State.F26))
		   {
			return true;
		   }
		else return false;
	}

	public Token getNextToken() throws Exception
	{
		Token tokenGenerated = null;
		String lex = "";
		int lexSize = 0;
		int num = 1;

		while (tokenGenerated == null && !isFinalState(automata.getState()))
		{
			Action[] actions = automata.transit(c);

			if (actions == null) { reader.close(); writer.close(); ErrorManager(""); } //Study cases...

			for (Action a : actions)
			{
				switch(a)
				{
					case L:
						c = reader.read();
						break;

					case Lp:
						this.line++;
						break;

					case C:
						lex = Character.toString(c);
						break;

					case Cp:
						lex = lex + Character.toString(c);
						lexSize++;
						break;

					case Cp2:
						//We dont do nothing because we did the initialization at the beggining of the method
						break;

					case N:
						num = Character.getNumericValue(c);
						break;

					case Np:
						num = num*10 + Character.getNumericValue(c);
						break;

					case G1:
						tokenGenerated = new Token("EOF", null);
						break;

					case G2:
						tokenGenerated = new Token("comma", null);
						break;

					case G3:
						tokenGenerated = new Token("semicolon", null);
						break;
					
					case G4:
						tokenGenerated = new Token("parenthOpen", null);
						break;
					
					case G5:
						tokenGenerated = new Token("parenthClose", null);
						break;

					case G6:
						tokenGenerated = new Token("bracketOpen", null);
						break;

					case G7:
						tokenGenerated = new Token("bracketClose", null);
						break;

					case G8:
						tokenGenerated = new Token("mul", null);
						break;

					case G9:
						tokenGenerated = new Token("sub", null);
						break;

					case G12:
						tokenGenerated = new Token("plusEq", null);
						break;

					case G13:
						tokenGenerated = new Token("assign", null);
						break;

					case G15:
						if (Util.contains(RES_WORDS, lex))
						{
							switch(lex)
							{
								case "boolean":
									tokenGenerated = new Token("boolean", null);
									break;
								case "do":
									tokenGenerated = new Token("do", null);
									break;
								case "function":
									tokenGenerated = new Token("function", null);
									break;
								case "get":
									tokenGenerated = new Token("get", null);
									break;
								case "if":
									tokenGenerated = new Token("if", null);
									break;
								case "int":
									tokenGenerated = new Token("int", null);
									break;
								case "let":
									tokenGenerated = new Token("let", null);
									break;
								case "put":
									tokenGenerated = new Token("put", null);
									break;
								case "return":
									tokenGenerated = new Token("return", null);
									break;
								case "string":
									tokenGenerated = new Token("string", null);
									break;
								case "void":
									tokenGenerated = new Token("void", null);
									break;
								case "while":
									tokenGenerated = new Token("while", null);
									break;
							}
						}
						else if (Util.contains(LOGIC, lex))
						{
							switch(lex)
							{
								case "true":
									tokenGenerated = new Token("true", null);
									break;
								case "false":
									tokenGenerated = new Token("false", null);
									break;
							}
						}
						else
						{
							//TODO: AÑADIR ZONA DE DECLARACION ?????? CUIDADITO COÑO
							Integer p =  st.getPos(lex);
							
							// If the lexeme is already on the Symbol Table, then call the Error Manager (TODO: ESTO SE CORRESPONDE A Q SE INTENTO DECLARAR LA VARIABLE CON OTRO TIPO, POR EJ)
							if (p != null) /*ErrorManager(lex);*/ tokenGenerated = new Token("ID", p); //TODO: SOLUCION AUXILIAR
							
							// Else, insert the lexeme and generate the token
							else {p = st.insertLex(lex); tokenGenerated = new Token("ID", p);}
						}
						break;

					case G16:
						if (num <= MAX_INT) tokenGenerated = new Token("INTEGER", num);
						else ErrorManager(Integer.toString(num)); //LLAMAR GESTOR ERRORES!!!!!!!
						break;

					case G17:
						if(lexSize <= MAX_STR) tokenGenerated = new Token("STRING", lex);
						else ErrorManager(lex); //LLAMAR GESTOR ERRORES!!!!!!!
						break;

					case G20:
						tokenGenerated = new Token("or", null);
						break;

					case G22:
						tokenGenerated = new Token("not", null);
						break;

					case G24:
						tokenGenerated = new Token("greaterThan", null);
						break;

					case G26:
						tokenGenerated = new Token("lessThan", null);
						break;
				}
			}
		}

		this.automata.reset();

		writer.write(tokenGenerated);
		
		return tokenGenerated;
	}

	public void closeReader() { this.reader.close();}

	public void closeWriter() { this.writer.close(); /* TODO: QUITAR ESTO??*/ }

	public static void main(String[] args){

		Writer writerST = new Writer("blupblup.txt");

		Token EOF = new Token("EOF", null);
		try{
			LexAnalyzer la = new LexAnalyzer("PIdG54.txt", "blipblip.txt");
			Token genTok = la.getNextToken();
			//System.out.println(genTok);
			while (!genTok.equals(EOF))
			{
				genTok = la.getNextToken();
				//System.out.println(genTok);
			}
			writerST.write(st);
			writerST.close();
			la.closeReader();
			la.closeWriter();
		}
		catch(Exception e) { e.printStackTrace(); }
	}
}