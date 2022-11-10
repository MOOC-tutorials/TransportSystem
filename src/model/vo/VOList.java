package model.vo;

import model.data_structures.Bag;

/**
 * Clase para agilizar las búsquedas de varios elementos en las tablas de hash.
 * @param <T>
 */
public class VOList<T extends Comparable<T>> {
	
	/**
	 * Id de los elementos que contiene la lista
	 */
	private int Id;
	
	/**
	 * Nombre de los elementos que contiene la lista.
	 */
	private String name;
	
	/**
	 * Lista de elementos
	 */
	private Bag<T> voList;
	
	public VOList( int pId ){
		Id = pId;
		voList = new Bag<>();
	}
	
	public VOList(String pName){
		name = pName;
		voList = new Bag<>();
	}
	
	public int getId() {
		return Id;
	}
	
	public String getName() {
		return name;
	}
	
	public Bag<T> getVOList() {
		return voList;
	}
	
	public void addVO( T pToAdd ){
		voList.addAtEnd(pToAdd);
	}
	
	public boolean contains( T pVO ){
		return voList.contains(pVO);
	}
	
	public String toString(){
		String ans = "[";
		for( T element : voList ){
			ans = ans + element + " " + ",";
		}
		ans = ans + "]";
		return ans;
	}
}
