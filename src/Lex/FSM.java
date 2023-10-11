package Lex;
import Lex.Util.State;
import Lex.Util.Action;
import java.util.regex.*;


public class FSM {

	private State state; //Internal state of the FSM

	public FSM()
	{
		this.state = State.S; //Initial state is always S
	}

	public Action[] transit(char ch)
	{
		String car = Character.toString(ch);

    	Pattern delimiters = Pattern.compile("[ \\t]"); 		//Delimiters
    	Pattern letters = Pattern.compile("[a-zA-Z]");        //Letters
    	Pattern digits = Pattern.compile("[0-9]");            //Digits
    	Pattern c = Pattern.compile("[^\"\\n\"\\\\]");        //Any character except EOL, "", \
    	Pattern cp = Pattern.compile(".");                    //Any character except EOL
    	Pattern eol = Pattern.compile("[\\n]");               //End of Line (EOL)   

		// *************************************** //

		Pattern cr = Pattern.compile("[\\r]");
		
		// *************************************** //

		Matcher matchDelims = delimiters.matcher(car);
		Matcher matchLetter = letters.matcher(car);
		Matcher matchDigit = digits.matcher(car);
		Matcher matchC = c.matcher(car);
		Matcher matchCp = cp.matcher(car);
		Matcher matchEol = eol.matcher(car);

		// *************************************** //

		Matcher matchCr = cr.matcher(car);
		
		// *************************************** //


		if (this.state.equals(State.S) && matchDelims.matches())                                                            { this.state = State.S; return new Action[] {Action.L }; }
		
		// ***************** PREGUNTAR A LOS PROFES ***************** //

		else if (this.state.equals(State.S) && matchCr.matches())                                                   		{ this.state = State.Sp; return new Action[] { Action.Lp, Action.L };}

		else if (this.state.equals(State.Sp) && matchEol.matches())                                                         { this.state = State.S; return new Action[] {Action.L}; }

		// ********************************************************** //

		else if (this.state.equals(State.S) && car.equals("\0"))                                                   { this.state = State.F1; return new Action[] { Action.G1 };}

		else if (this.state.equals(State.S) && car.equals(","))                                                    { this.state = State.F2; return new Action[] { Action.G2, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals(";"))                                                    { this.state = State.F3; return new Action[] { Action.G3, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("("))                                                    { this.state = State.F4; return new Action[] { Action.G4, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals(")"))                                                    { this.state = State.F5; return new Action[] { Action.G5, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("{"))                                                    { this.state = State.F6; return new Action[] { Action.G6, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("}"))                                                    { this.state = State.F7; return new Action[] { Action.G7, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("*"))                                                    { this.state = State.F8; return new Action[] { Action.G8, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("-"))                                                    { this.state = State.F9; return new Action[] { Action.G9, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("+"))                                                    { this.state = State.A; return new Action[] { Action.L }; }
		                                    
		else if (this.state.equals(State.A) && car.equals("="))                                                    { this.state = State.F12; return new Action[] { Action.G12, Action.L }; }
		                                    
		else if (this.state.equals(State.S) && car.equals("="))                                                    { this.state = State.F13; return new Action[] { Action.G13, Action.L }; }
		                        
		else if (this.state.equals(State.S) && (matchLetter.matches() || car.equals("_")))                         { this.state = State.C; return new Action[] { Action.C, Action.L }; }
		
		else if (this.state.equals(State.C) && (matchLetter.matches() || matchDigit.matches() || car.equals("_"))) { this.state = State.C; return new Action[] { Action.Cp, Action.L }; }

		else if (this.state.equals(State.C)) 																			    { this.state = State.F15; return new Action[] { Action.G15 }; }

		else if (this.state.equals(State.S) && matchDigit.matches()) 														{ this.state = State.D; return new Action[] { Action.N, Action.L }; }

		else if (this.state.equals(State.D) && matchDigit.matches()) 														{ this.state = State.D; return new Action[] { Action.Np, Action.L }; }

		else if (this.state.equals(State.D)) 																			    { this.state = State.F16; return new Action[] { Action.G16 }; }

		else if (this.state.equals(State.S) && car.equals("\"")) 													{ this.state = State.E; return new Action[] { Action.Cp2, Action.L }; }
		
		else if (this.state.equals(State.E) && matchC.matches()) 															{ this.state = State.E; return new Action[] { Action.Cp, Action.L }; }

		else if (this.state.equals(State.E) && car.equals("\\")) 													{ this.state = State.Ep; return new Action[] {Action.L }; }

		else if (this.state.equals(State.Ep) && matchCp.matches()) 															{ this.state = State.E; return new Action[] { Action.Cp, Action.L }; }

		else if (this.state.equals(State.E) && car.equals("\"")) 													{ this.state = State.F17; return new Action[] { Action.G17,Action.L }; }

		else if (this.state.equals(State.S) && car.equals("<")) 													{ this.state = State.F26; return new Action[] { Action.G26, Action.L }; }

		else if (this.state.equals(State.S) && car.equals(">")) 													{ this.state = State.F24; return new Action[] { Action.G24, Action.L }; }

		else if (this.state.equals(State.S) && car.equals("!")) 													{ this.state = State.F22; return new Action[] { Action.G22, Action.L }; }

		else if (this.state.equals(State.S) && car.equals("|")) 													{ this.state = State.H; return new Action[] { Action.L }; }

		else if (this.state.equals(State.H) && car.equals("|")) 													{ this.state = State.F20; return new Action[] { Action.G20, Action.L }; }

		else if (this.state.equals(State.S) && car.equals("|")) 													{ this.state = State.H; return new Action[] { Action.L }; }

		else if (this.state.equals(State.S) && car.equals("/")) 													{ this.state = State.F; return new Action[] { Action.L }; }

		else if (this.state.equals(State.F) && car.equals("/")) 													{ this.state = State.Fp; return new Action[] { Action.L }; }

		else if (this.state.equals(State.Fp) && matchCp.matches()) 															{ this.state = State.Fp; return new Action[] { Action.L }; }

		else if (this.state.equals(State.Fp) && matchEol.matches()) 														{ this.state = State.S; return new Action[] { Action.L }; }
	
		// ERRORS!
		else { return null; }

	}

	public State getState()
	{
		return this.state;
	}

	public void reset() { this.state = State.S; }
}
