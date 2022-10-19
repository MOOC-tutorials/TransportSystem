package model.data_structures;

public class Vertex<K, V> implements Comparable<Vertex<K, V>>{
	private K key;

	private V value;

	public Vertex( K key, V value ){
		this.key = key;
		this.value = value;
	}

	public K getKey(){
		return key;
	}

	public V getValue() {
		return value;
	}
	
	public void setValue( V value ){
		this.value = value;
	}

	public String toString(){
		return "( " + key + "; " + value + " ) ";
	}

	@Override
	public int compareTo(Vertex<K, V> o) {
	
		return 0;
	}
}
