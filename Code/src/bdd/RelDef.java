package bdd;
import java.util.*;

public class RelDef {
	private String name;
	private int nbColumn;
	private Vector<String> typeColumn;
	private int fileIdx;
	private int recordSize;
	private int slotCount;
	
	public RelDef(String name, int nbColumn, Vector<String> typeColumn, int recordSize, int fileIdx, int slotCount) {
		this.fileIdx = fileIdx;
		this.slotCount = slotCount;
		this.name = name;
		this.nbColumn = nbColumn;
		this.typeColumn = typeColumn;
		this.recordSize = recordSize;
	}
	
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
	public Vector<String> getTypeColumn() {
		return typeColumn;
	}
	
	/**
	 * @param typeColumn the typeColumn to set
	 */
	public void setTypeColumn(Vector<String> typeColumn) {
		this.typeColumn = typeColumn;
	}

	public int getFileIdx() {
		return this.fileIdx;
	}
	
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	
	/**
	 * @return the recordSize
	 */
	public int getRecordSize() {
		return recordSize;
	}

	/**
	 * @param recordSize the recordSize to set
	 */
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	/**
	 * @return the slotCount
	 */
	public int getSlotCount() {
		return slotCount;
	}

	/**
	 * @param slotCount the slotCount to set
	 */
	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}
}
