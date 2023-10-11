package Lex;

public class Util {

	public static enum Action {
		G1,G2,G3,G4,G5,G6,G7,G8,G9,G12,G13,
		G15,G16,G17,G26,G24,G22,G20,C,Cp,Cp2,N,Np,L,Lp;
	}
	
	public static enum State {
		S,Sp,A,B,C,D,E,Ep,F,Fp,G,H,I,J,K,
		L,F1,F2,F3,F4,F5,F6,F7,F8,F9,F12,F13,
		F15,F16,F17,F20,F22,F24,F26
	}

	public static boolean contains(String[] list, String elem) {
		for (String s : list) {
			if (s.equals(elem)) return true;
		}
		return false;
	}

}
