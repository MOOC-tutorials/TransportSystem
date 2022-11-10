package model.data_structures;


/**
 * Implementación de una sequential search para la tabla de hash con separate chaining
 * basada en: http://algs4.cs.princeton.edu/34hash/SequentialSearchST.java.html
 * @param <Key> tipo de key de la lista
 * @param <Value> tipo de values de la lista
 */
public class SequentialSearch<Key , Value>  {

	/**
	 * numero de parejas Key-value
	 */
	private int pairs;          
	/**
	 * primer nodo de la lista encadenada    
	 */
	private Node first;      
	/**
	 *    Clase de ayuda de tipo lista encadenada
	 */
	private class Node {
		/**
		 * key del nodo    
		 */
		private Key key;
		/**
		 * Value del nodo    
		 */
		private Value val;
		/**
		 * Referencia al nodo siguiente    
		 */
		private Node next;

		/**
		 * Crea un nuevo nodo con el Key, Value y nodo siguiente ingresados por parametro    
		 * @param key Key que se le va a asignar al nodo
		 * @param val valor que se le va a asignar al nodo
		 * @param next nodo siguiente 
		 */
		public Node(Key key, Value val, Node next)  {
			this.key  = key;
			this.val  = val;
			this.next = next;
		}
	}
	/**
	 * Inicializa una tabla vacia 
	 */
	public SequentialSearch() {
	}

	/**
	 * @return el numero de parejas key-value 
	 */
	public int size() {
		return pairs;
	}

	/**
	 * @returns true si esta tabla de simbolos esta vacia, false en los demas casos
	 */
	public boolean isEmpty(){
		return size( )== 0;
	}

	/**
	 * * @return true si esta tabla contiene la Key dada por parametro , false de lo contrario 
	 * @param key la key que se quiere buscar
	 */
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/**
	 * @param key la key de la cual se quiere saber el value
	 * @return el Value asociado a la key dada por parametro
	 */
	public Value get(Key key) {
		for (Node current = first; current != null; current = current.next) {
			if (key.equals(current.key))
				return current.val;
		}
		return null;
	}
	
	public Queue<Value> getAll(){
		Queue<Value> ans = new Queue<>();
		for (Node current = first; current != null; current = current.next) {
			ans.enqueue(current.val);
		}
		return ans;
	}

	public Key getFirstKey (){
		return first.key;
	}
	
    /**
     * @return Todas las llaves de la tabla como un iterable 
     */
    public Iterable<Key> keys()  {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }

	/**
	 * inserta la pareja key-value en la tabla de simbolos 
	 * @param key el key que se va a insertar
	 * @param val el value que se va a insertar
	 */
	public void put(Key key, Value val) {
		if (val == null) {
			delete(key);
			pairs = 0;
			return;
		}

		first = new Node( key, val, first);
		pairs++;
	}
	/**
	 * remueve la key y su valor a sociado de la tabla 
	 * @param key la key que se desea elmiminar de la tabla
	 */
	public void delete(Key key) {
		first = delete(first, key);
	}


	/**
	 * elimina la llave en la lista encadenada en el nodo pNode
	 * @param pNode nodo en el que se va a eliminar la key 
	 * @param key la key que se desea eliminar
	 * @return el nodo que se eliminó
	 */
	private Node delete(Node pNode, Key key) {
		if (pNode == null) return null;
		if (key.equals(pNode.key)) {
			pairs--;
			return null;
		}
		pNode.next = delete(pNode.next, key);
		return null;
	}

	public String toString(){
		String ans = "{";
		for (Node current = first; current != null; current = current.next) {
			ans = ans + "(" + current.key + "," + current.val + ")";
		}
		ans = ans + "}";
		return ans;
	}

}
