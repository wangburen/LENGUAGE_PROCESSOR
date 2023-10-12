package Sym;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	
	private Map <String,Integer> posST; // Maps a Lexeme (String) to its position in the Symbol Table.
										// The position of the lexeme is the index of an array that contains the entries associated to every lexeme

	private ArrayList<Entry> ST; // List that contains the entries associated to every lexeme.
								 // First-come-first-served order
	
	private Integer lastPosST; // Last free position to insert the entry

	private int ID; // Identifier of the Table Symbol

	// Create a new Symbol Table
	public SymbolTable(int ID) {
		posST = new HashMap<String, Integer>();
		ST = new ArrayList<Entry>();
		lastPosST = 0;
		this.ID = ID;
	}
	
	
	// Destroy a Symbol Table
	// Since java performs automatic garbage collection, we nullify the attributes of this object (that are references)
	public void destroy() { posST = null; ST = null; lastPosST = null; }

	// Insert a new entry in the Symbol Table

	// Insertion without an entry (used by the LexA)
	// Returns the lexeme position in the Symbol Table if the lexeme was inserted. Else, returns null (it shouldnt, since its called by the LexA after verifiyng the lexeme is on the Symbol Table)
	public Integer insertLex(String lex) {
		Integer pos = lastPosST;
		if (!inSymbolTable(lex)) { posST.put(lex, pos); ST.add(pos, new Entry(null, null, null, null, null, null, null)); lastPosST++; return pos;}
		else return null;
	}
	
	// Insertion with an entry (could be used by the SemA)
	// Returns the lexeme position in the Symbol Table if the lexeme was inserted. Else, returns null (it shouldnt, since its called by the LexA after verifiyng the lexeme is on the Symbol Table)
	public Integer insertLex(String lex, Entry e)
	{
		Integer pos = lastPosST;
		if (!inSymbolTable(lex)) { posST.put(lex, pos); ST.add(pos, e); lastPosST++; return pos;}
		else return null;
	}

	// Assert that a lexeme is in the Symbol Table
	public boolean inSymbolTable(String lex)
	{
		return posST.get(lex) != null? true : false;
	}
	
	// Returns the position of a lexeme. If its not inserted, it returns null
	public Integer getPos(String lex) { return posST.get(lex); }

	// Get the entry associated to a lexeme
	public Entry getEntry(String lex) {
		if (!inSymbolTable(lex)) { return null; }
		else return ST.get(lastPosST);
	}

	@Override
	public String toString() 
	{	
		String result;
		String name;
		if (ID == 1) name = "Global";
		else name = "Local";
		
		String header = name + " # " + ID + " : " + "\r";
		result = header;

		String lexs = "";
		for(String lex : posST.keySet())
		{
			lexs += "	" + "* " + lex + " : " + "nombre" + "\r";
		}

		String type = "";
		for(Integer index : posST.values())
		{
			type += "		" + "+ " + "Tipo" + " : " +  ST.get(index).getType() + "\r";
		}

		String displ = "";
		for(Integer index : posST.values())
		{
			displ += "		" + "+ " + "Despl" + " : " +  ST.get(index).getDisplacement() + "\r";
		}

		String numParam = "";
		for(Integer index : posST.values())
		{
			numParam += "		" + "+ " + "NumParam" + " : " +  ST.get(index).getNumOfParameters() + "\r";
		}

		String typeParam = "";
		int paramCounter = 0;
		for(Integer index : posST.values())
		{
			if (ST.get(index).getTypeOfParameters() == null)
			{
				typeParam += "		" + "+ " + "TipoParam" + paramCounter+1 + " : " +  "null" + "\r";
			}
			else
			{
			typeParam += "		" + "+ " + "TipoParam" + paramCounter+1 + " : " +  ST.get(index).getTypeOfParameters()[paramCounter] + "\r";
			}
			paramCounter++;
		}

		String modeParam = "";
		paramCounter = 0;
		for(Integer index : posST.values())
		{
			if (ST.get(index).getModeOfParameters() == null)
			{
				modeParam += "		" + "+ " + "ModoParam" + paramCounter+1 + " : " +  "null" + "\r";
			}
			else
			{
				modeParam += "		" + "+ " + "ModoParam" + paramCounter+1 + " : " +  ST.get(index).getModeOfParameters()[paramCounter] + "\r";
			}
		}

		String typeRet = "";
		for(Integer index : posST.values())
		{
			typeRet += "		" + "+ " + "TipoRetorno" + " : " +  ST.get(index).getTypeOfReturn() + "\r";
		}

		String funcTag = "";
		for(Integer index : posST.values())
		{
			funcTag += "		" + "+ " + "EtiqFuncion" + " : " +  ST.get(index).getTag() + "\r";
		}

		//TODO: FALTA PARAM, PERO YA ESTOY MUY CANSADO PARA SEGUIR XD [VER ESPECIFICACION EN LA PAG DE PDL]

		String[] listLexs = lexs.split("\r");
		String[] listType = type.split("\r");
		String[] listDispl = displ.split("\r");
		String[] listNumParam = numParam.split("\r");
		String[] listTypeParam = typeParam.split("\r");
		String[] listModeParam = modeParam.split("\r");
		String[] listTypeRet = typeRet.split("\r");
		String[] listFuncTag = funcTag.split("\r");

		for(int i = 0; i < listLexs.length; i++)
		{
			result += listLexs[i] + "\r" + listType[i] + "\r" + listDispl[i] + "\r" + listNumParam[i] + "\r" + listTypeParam[i] + "\r" + listModeParam[i] + "\r" + listTypeRet[i] + "\r" + listFuncTag[i] + "\r";
			result += "----------------------------------------" + "\r";
		}
		
		return result;
	}
}
