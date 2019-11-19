package bdd;
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
		int j = 0;
		for (int i = pos; i < pos+buff.length; i++) {
			byte value = (byte) this.values.get(j);
			buff[i] = value;
			j++;
		}
		//revoir cette méthode pour l'écriture des bytes avec ByteBuffer
	}
	
	/**
	 * Lit les valeurs du Record depuis le buffer, l’une après l’autre, à partir de position.
	 * @param buff
	 * @param pos
	 */
	public void readFromBuffer(byte[] buff, int pos) {
		for (int i = pos; i < pos+buff.length; i++) {
			System.out.println(buff[i]);
		}
		//revoir cette méthode pour la lecture des bytes avec ByteBuffer
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
