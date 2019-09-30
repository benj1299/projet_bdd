import java.util.*;

public class RelDef {
	
	private String name;
	private int nbColumn;
	private static Vector<String> typeColumn;
	
	// Getters / Setters
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the nbColumn
	 */
	public int getNbColumn() {
		return nbColumn;
	}
	
	/**
	 * @param nbColumn the nbColumn to set
	 */
	public void setNbColumn(int nbColumn) {
		this.nbColumn = nbColumn;
	}
	
	/**
	 * @return the typeColumn
	 */
	public static Vector<String> getTypeColumn() {
		return typeColumn;
	}
	
	/**
	 * @param typeColumn the typeColumn to set
	 */
	public static void setTypeColumn(Vector<String> typeColumn) {
		RelDef.typeColumn = typeColumn;
	}
	
}
