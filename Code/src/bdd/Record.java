package bdd;
import java.nio.ByteBuffer;
import java.util.List;

public class Record {
	
	private RelDef relation;
	private List<Object> values; // Valeurs d'un record
	
	public Record(RelDef relation, List<Object> values) {
		this.setRelation(relation);
		this.setValues(values);
	}
	
	/**
	 * Ecrit les valeurs du Record dans le buffer, l’une après l’autre, à partir de position.
	 * @param buff
	 * @param pos
	 */
	public void writeToBuffer(byte[] buff, int pos) {
	    ByteBuffer bbuf = ByteBuffer.allocate(buff.length);
	    bbuf.position(pos);
	    bbuf.put(buff);
	}
	
	/**
	 * Lit les valeurs du Record depuis le buffer, l’une après l’autre, à partir de position.
	 * @param buff
	 * @param pos
	 */
	public void readFromBuffer(byte[] buff, int pos) {
	    ByteBuffer bbuf = ByteBuffer.allocate(buff.length);
	    bbuf.position(pos);
	    bbuf.get();
	}

	/**
	 * @return the relation
	 */
	public RelDef getRelation() {
		return relation;
	}

	/**
	 * @param relation the relation to set
	 */
	public void setRelation(RelDef relation) {
		this.relation = relation;
	}

	/**
	 * @return the values
	 */
	public List<Object> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<Object> values) {
		this.values = values;
	}
}
