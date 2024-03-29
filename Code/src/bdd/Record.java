package bdd;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Record {
	private RelDef relation;
	private Vector<Object> values;
	private Rid rid;

	public Record(RelDef relation) {
		this.relation = relation;
	}

	/**
	 * Ecrit les valeurs du Record dans le buffer, l’une après l’autre, à partir de position.
	 * @param buff
	 * @param pos
	 */
	public void writeToBuffer(byte[] buff, int pos) {
		ByteBuffer bbuf = ByteBuffer.wrap(buff);
		if(pos < buff.length) {
			bbuf.position(pos);
			try{
				for(int i = 0; i < (this.relation.getRecordSize() - 1); i++){
					String v = (String) this.relation.getTypeColumn().get(i);
					switch (v.toLowerCase()) {
					case "int":
						int val = Integer.parseInt((String) this.values.get(i));
						bbuf.putInt(val);
						break;

					case "float":
						float b = Float.parseFloat((String) this.values.get(i));
						bbuf.putFloat(b);

					default :
						if(v instanceof String && v.toLowerCase().substring(0, 5).equals("string")) 
						{
							int tall = Character.getNumericValue(v.charAt(6));
							String mot = (String) values.get(i);
							for (int j = 0; j < tall || j < mot.length(); j++)
							{
								char charVar = mot.charAt(j);
								bbuf.putChar(charVar);
							}
						}	
					}
					buff = new byte[bbuf.remaining()];
				}
			}
			catch (BufferOverflowException e) { 
				e.printStackTrace();
				e.getMessage();
			} 
		}
		else {
			System.out.println("Une erreur s'est produite : la position d'écriture du buffer est supérieur à sa taille");
		}
	}
	/**
	 * Lit les valeurs du Record depuis le buffer, l’une après l’autre, à partir de position.
	 * @param buff
	 * @param pos
	 */	
	public void readFromBuffer(byte[] buff, int pos) {
		ByteBuffer bbuf = ByteBuffer.wrap(buff);
		if(pos < buff.length) {
			bbuf.position(pos);
			try {
				
				for(int i = 0; i < (this.relation.getRecordSize() - 1); i++){
					String v = (String) this.relation.getTypeColumn().get(i);
					switch (v.toLowerCase()) {
					case "int":
						int a = bbuf.getInt(i);
						this.values.add(a);
						break;

					case "float":
						float b = bbuf.getFloat(i);
						this.values.add(b);
						break;

					default :
						if(v instanceof String && v.toLowerCase().substring(0, 5).equals("string")) 
						{
							int tall = Character.getNumericValue(v.charAt(6));
							String mot = (String) values.get(i);
							for (int j = 0; j < tall || j < mot.length(); j++)
							{
								char charVar = bbuf.getChar(j);
								this.values.add(charVar);
							}
						}	
					}
					buff = new byte[bbuf.remaining()];
				}
			} catch(Exception e) {
				e.getMessage();
			}
		}else {
			System.out.println("Une erreur s'est produite : la position de lecture du buffer est supérieur à sa taille");
		}

		
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
	public Vector<Object> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(Vector<Object> values) {
		this.values = values;
	}	

	public void setValue(Object value) {
		this.values.add(value);
	}

	/**
	 * @return the rid
	 */
	public Rid getRid() {
		return rid;
	}

	/**
	 * @param rid the rid to set
	 */
	public void setRid(Rid rid) {
		this.rid = rid;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.values.size(); i++) {
			sb.append(this.values.get(i) + " ;");
		}
		return sb.toString();
	}

}
