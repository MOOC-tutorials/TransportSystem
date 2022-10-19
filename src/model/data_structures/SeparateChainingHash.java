package model.data_structures;

import api.IHashTable;

/**
 * Implementación de una tabla de hash con solución de conflictos por chaining hash
 * Basada en: http://algs4.cs.princeton.edu/34hash/SeparateChainingHashST.java.html
 * @param <Key> Tipo de key que se va a usar
 * @param <Value> Tipo de value que contiene la tabla
 */
public  class SeparateChainingHash <Key, Value> implements IHashTable<Key, Value> {

	private static final int INIT_CAPACITY = 4;

	/**
	 * número de parejas Key.-value   
	 */
	private int pairs;                           
	/**
	 * tamaño de la tabla    
	 */
	private int size;                                
	/**
	 * Arreglo de listas encadenadas de tablas de simbolos   
	 */
	private SequentialSearch<Key, Value>[] st; 

	/**
	 * Inicializa una tabla vacia
	 */
	public SeparateChainingHash() {
		this(INIT_CAPACITY);
	} 

	/**
	 * Inicializa una nueva tabla vacia con pSize número de cadenas 
	 * * @param pSize Número inicial de parejas.
	 */
	public SeparateChainingHash(int pSize) {
		this.size = pSize;
		st = (SequentialSearch<Key, Value>[]) new SequentialSearch[pSize];
		for (int i = 0; i < pSize; i++)
			st[i] = new SequentialSearch<Key, Value>();
	} 

	/**
	 * aumenta el tamaño de la tabla al numero dado de cadenas
	 * @param pChains numero nuevo de cadenas 
	 */
	private void resize(int pChains) {
		SeparateChainingHash<Key, Value> temp = new SeparateChainingHash<Key, Value>(pChains);
		for (int i = 0; i < size; i++) {
			for (Key key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.size  = temp.size;
		this.pairs  = temp.pairs;
		this.st = temp.st;
	}
	
	/**
	 * @param key key del elemento
	 * @return un entero positivo menor que el tamaño de la tabla según el hashCode del key 
	 */
	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % size;
	} 
	/**
	 * @return el numero de parejas key-Valor de la tabla
	 */
	public int size() {
		return pairs;
	} 

	/**
	 * @return true si la tabla esta vacia , false de lo contrario  
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * 
	 * @param  key la key 
	 * @return  true si la tabla contiene la key ingersada por parametro
	 *         code false de lo contrario
	 * @throws IllegalArgumentException si la key es null
	 */
	public boolean contains(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	} 

	/**
	 * @param  key la key
	 * @return el valor asociado a la key ingresada por parametro en esta tabla 
	 *         null si no existe tal valor 
	 * @throws IllegalArgumentException si la key ingresada es null
	 */
	public Value get(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		int i = hash(key);
		return st[i].get(key);
	} 
	
	public Queue<Value> getAll( Key key ){
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		int i = hash(key);
		return st[i].getAll();
	}

	/**
	 *  inserta el key-value especificado en la tabla, agregando el nuevo valor 
	 *  a una sequential search encabezada por él si la tabla ya contenia la key especificada
	 *  borra el key-value especificado si el value ingresado es null 
	 *
	 * @param  key
	 * @param  val 
	 * @throws IllegalArgumentException si la llave es null
	 */
	public void put(Key key, Value val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null) {
			delete(key);
			return;
		}

		if (pairs >= 10*size) resize(2*size);

		int i = hash(key);
		if( !st[i].contains(key)){
			pairs++;
		}
		st[i].put(key, val);
	} 
	
	/**
	 * remueve la key dada por parametro y su valor si la key esta en esta tabla 
	 * @param  key la key que se desea eliminar
	 * @throws IllegalArgumentException si la llave es null 
	 */
	public void delete(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");

		int i = hash(key);
		if (st[i].contains(key)) pairs--;
		st[i].delete(key);
		if (size > INIT_CAPACITY && pairs <= 2*size) resize(size/2);
	} 


	/** 
	 * @return las llaves de la tabla como un iterable
	 */
	public Queue<Key> keys() {
		Queue<Key> queue = new Queue<Key>();
		for (int i = 0; i < size; i++) {
			for (Key key : st[i].keys())
				queue.enqueue(key);
		}
		return queue;
	} 
}

