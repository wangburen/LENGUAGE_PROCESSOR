package Sym;

import Lex.Util.Type;
import Lex.Util.Mode;

/*
 * This class represents an entry for the symbol table.
 * It contains:
 *      - Type
 *      - Displacement
 *      - Number of Parameters
 *      - Type of Parameters
 *      - Mode of Parameters (Copy or Reference)
 *      - Type of return
 *      - Tag
 */

public class Entry {

    private Type type;
    private Integer displacement;
    private Integer numberOfParameters;
    private Type[] typeOfParameters;
    private Mode[] modeOfParameters; 
    private Type typeOfReturn;
    private String tag;

    public Entry(Type type, Integer displacement, Integer numberOfParameters, Type[] typeOfParameters, Mode[] modeOfParameters, Type typeOfReturn, String tag)
    {
        this.type = type;
        this.displacement = displacement; 
        this.numberOfParameters = numberOfParameters;
        this.typeOfParameters = typeOfParameters;
        this.modeOfParameters = modeOfParameters;
        this.typeOfReturn = typeOfReturn;
        this.tag = tag;   
    }

    public Type getType() { return this.type; }
    public Integer getDisplacement() { return this.displacement; }
    public Integer getNumOfParameters() { return this.numberOfParameters; }
    public Type[] getTypeOfParameters() { return this.typeOfParameters; }
    public Mode[] getModeOfParameters() { return this.modeOfParameters; }
    public Type getTypeOfReturn() { return this.typeOfReturn; }
    public String getTag() { return this.tag; }
}
