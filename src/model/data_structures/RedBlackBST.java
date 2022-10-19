package model.data_structures;

import java.util.NoSuchElementException;

import api.IRedBlackBST;

/**
 * Implementación de un árbol rojo negro.
 * Tomada de: http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
 * @param <Key> Tipo de la las llaves del árbol
 * @param <Value> Tipo de los valores del árbol
 */
public class RedBlackBST< Key extends Comparable<Key>, Value > implements IRedBlackBST<Key, Value>{

	/**
	 * Constante para indicar que el nodo es rojo
	 */
	private static final boolean RED = true;

	/**
	 * Constante para indicar que el nodo es negro
	 */
	private static final boolean BLACK = false;

	/**
	 * Raiz del árbol.
	 */
	private Node root;

	/**
	 * Clase privada para los nodos del árbol
	 */
	private class Node {
		/**
		 * Llave del nodo
		 */
		private Key key;

		/**
		 * Valor del nodo
		 */
		private Value val;

		/**
		 * Links a los nodos derecho e izquierdo.
		 */
		private Node left, right;

		/**
		 * Color del link del padre
		 */
		private boolean color;

		/**
		 * Tamaño del subarbol
		 */
		private int size;

		public Node( Key pKey, Value pVal, boolean pColor, int pSize ){
			key = pKey;
			val = pVal;
			color = pColor;
			size = pSize;
		}
	}

	/**
	 * Inicializa un árbol vacío
	 */
	public RedBlackBST(){
	}

	/**
	 * @param pNode Nodo que se quiere saber si es rojo
	 * @return false el nodo no existe o si es negro, true de lo contrario.
	 */ 
	private boolean isRed( Node pNode ){
		if( pNode == null) return false;
		return pNode.color == RED;
	}

	/**
	 * @param pNode Raíz del sub-árbol del cual se quiere saber el tamaño.
	 * @return número de nodos en el sub-árbol, 0 si el nodo es null.
	 */
	private int size( Node pNode ){
		if( pNode == null) return 0;
		return pNode.size;
	}

	/**
	 * @return Número de parejas llave-valo del árbol.
	 */
	public int size(){
		return size(root);
	}

	/**
	 * @return true si el árbol está vacío, false de lo contrario.
	 */
	public boolean isEmpty(){
		return root == null;
	}

	/**
	 * @param pKey llave de la que se quiere ver el valor.
	 * @return valor asociado a la llave que entra por parámetro si está en el árbol o null si no está.
	 * @throws IllegalArgumentException si la llave es null
	 */
	public Value get( Key pKey ){
		if( pKey == null)
			throw new IllegalArgumentException( "El parámetro de get() es null" );
		return get( root, pKey);
	}

	/**
	 * @param pNode raíz del sub-árbol donde se va a buscar
	 * @param pKey llave asociada al valor que se retorna
	 * @return Valor asociado a una llave en el sub-árbol con raiz en pNode
	 */
	private Value get( Node pNode, Key pKey ){
		while( pNode != null){
			int cmp = pKey.compareTo(pNode.key);
			if( cmp < 0 )
				pNode = pNode.left;
			else if( cmp > 0)
				pNode = pNode.right;
			else
				return pNode.val;
		}
		return null;
	}

	/**
	 * @param pKey llave que se desea saber si está en el árbol
	 * @return true si el árbol contiene la llave, false de lo contrario
	 * @throws IllegalArgumentException si la llave es null
	 */
	public boolean contains( Key pKey ){
		return get(pKey) != null;
	}

	/**
	 * Inserta el par llave-valor en el arbol, sobreescribiendo el anterior 
	 * valor con el nuevo valor si el árbol ya contenía la llave especificada.
	 * Elimina la llave especificada si pVal es null.
	 * @param pKey Llave asociada al valor que se va a insertar
	 * @param pVal Valor que se va a insertar
	 */
	public void put( Key pKey, Value pVal){
		if( pKey == null )
			throw new IllegalArgumentException("Key in put() is null");
		if( pVal == null){
			delete(pKey);
			return;
		}

		root = put( root, pKey, pVal );
		root.color = BLACK;
		assert check();
	}

	/**
	 * Inserta el par llave-valor en el sub-árbol con raíz en pNode
	 * @param pNode raíz del sub-árbol
	 * @param pKey Llave asociada al valor que se va a insertar
	 * @param pVal Valor que se va a insertar
	 * @return Nueva raíz del sub-árbol
	 */
	private Node put( Node pNode, Key pKey, Value pVal ){
		if( pNode == null )
			return new Node( pKey, pVal, RED, 1);

		int cmp = pKey.compareTo(pNode.key);
		if( cmp < 0 )
			pNode.left = put( pNode.left, pKey, pVal );
		else if ( cmp > 0)
			pNode.right = put(pNode.right, pKey, pVal);
		else
			pNode.val = pVal;

		if( isRed( pNode.right ) && !isRed( pNode.left ) )
			pNode = rotateLeft( pNode );
		if( isRed( pNode.left ) && isRed(pNode.left.left) )
			pNode = rotateRight( pNode );
		if( isRed( pNode.left ) && isRed(pNode.right) )
			flipColors( pNode );
		pNode.size = size(pNode.left) + size(pNode.right) + 1;

		return pNode;
	}

	/**
	 * Elimina la llave más pequeña y su valor asociado del árbol.
	 * @throws NoSuchElementException si el árbo está vacío
	 */
	public void deleteMin(){
		if( isEmpty() ) throw new NoSuchElementException( "Underflow del árbol" );

		if( !isRed(root.left) && !isRed(root.right) )
			root.color = RED;

		root = deleteMin( root );
		if( !isEmpty() ) root.color = BLACK;
		assert check();
	}

	/**
	 * Elimina la llave más pequeña y su valor asociado del sub-árbol con raíz en pNode.
	 * @param pNode raíz del sub-árbol
	 * @return nuevo sub-árbol después de eliminar el par.
	 */
	private Node deleteMin( Node pNode ){
		if( pNode.left == null )
			return null;

		if( !isRed(pNode.left) && !isRed(pNode.left.left) )
			pNode = moveRedLeft( pNode );

		pNode.left = deleteMin(pNode.left);
		return balance( pNode );
	}

	/**
	 * Elimina la llave más grande y su valor asociado del árbol.
	 * @throws NosuchElementException si el árbol está vacío.
	 */
	public void deleteMax( ){
		if( isEmpty() ) throw new NoSuchElementException( "Underflow del árbol" );

		if( !isRed( root.left ) && !isRed(root.right) )
			root.color = RED;

		root = deleteMax( root );
		if( !isEmpty() ) root.color = BLACK;
		assert check();
	}

	/**
	 * Elimina el par llave-valor más grande del sub-árbol con raíz en pNode
	 * @param pNode raíz del sub-arbol
	 * @return Nuevo subárbol después de eliminar el par.
	 */
	private Node deleteMax( Node pNode ){
		if( isRed(pNode) )
			pNode = rotateRight(pNode);

		if( pNode.right == null )
			return null;

		if( !isRed(pNode.right) && !isRed(pNode.right.left))
			pNode = moveRedRight( pNode );

		pNode.right = deleteMax(pNode.right);

		return balance(pNode);
	}

	/**
	 * Elimina la llave especificada y su valor asociado del árbol si está en él
	 * @param pKey llave a eliminar
	 * @throws IllegalARgumentException si pKey es null
	 */
	public void delete( Key pKey ){
		if( pKey == null )
			throw new IllegalArgumentException("El argumento de delete() es null");
		if( !isRed(root.left) && !isRed(root.right))
			root.color = RED;

		root = delete( root, pKey );
		if( !isEmpty()) root.color = BLACK;
		assert check();
	}

	/**
	 * Elimina un par llave-valor del sub-árbol con raíz en pNode
	 * @param pNode raíz del sub-árbol
	 * @param pKey llave a eliminar
	 * @return Raíz del nuevo sub-árbol después de eliminar.
	 */
	private Node delete( Node pNode, Key pKey ){
		if( pKey.compareTo(pNode.key) < 0){
			if( !isRed(pNode.left) && !isRed(pNode.left.left) )
				pNode = moveRedLeft( pNode );
			pNode.left = delete( pNode.left, pKey);
		}
		else{
			if( isRed(pNode.left) )
				pNode = rotateRight(pNode);
			if( pKey.compareTo(pNode.key) == 0 && (pNode.right == null))
				return null;
			if( pKey.compareTo(pNode.key) == 0){
				Node x = min( pNode.right );
				pNode.key = x.key;
				pNode.val = x.val;
				pNode.right = deleteMin(pNode.right);
			}
			else
				pNode.right = delete(pNode.right, pKey);
		}
		return balance(pNode);
	}

	/**
	 * Rota el sub-árbol con raíz en pNode a la derecha
	 * @param pNode raíz del sub-árbol
	 * @return Raíz del nuevo árbol tras la rotación.
	 */
	private Node rotateRight( Node pNode ){
		Node x = pNode.left;
		pNode.left = x.right;
		x.right = pNode;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = pNode.size;
		pNode.size = size(pNode.left) + size(pNode.right) + 1;
		return x;
	}

	/**
	 * Rota el sub-árbol con raíz en pNode a la izquierda
	 * @param pNode raíz del sub-árbol
	 * @return Raíz del nuevo árbol tras la rotación.
	 */
	private Node rotateLeft( Node pNode ){
		Node x = pNode.right;
		pNode.right = x.left;
		x.left = pNode;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = pNode.size;
		pNode.size = size(pNode.left) + size(pNode.right) + 1;
		return x;
	}

	/**
	 * Cambia los colores del nodo y sus hijos
	 * @param pNode Nodo cuyo color va a cambiar.
	 */
	private void flipColors( Node pNode ){
		pNode.color = !pNode.color;
		pNode.left.color = !pNode.left.color;
		pNode.right.color = !pNode.right.color;
	}

	/**
	 * Asumiendo que pNode es rojo y que pNode.left y pNode.left.right son negros,
	 * cambia pNode.left o alguno de sus hijos a rojo.
	 * @param pNode Raíz del nodo que se quiere cambiar el rojo a la izquierda.
	 * @return raíz del nuevo sub-árbol después de los cambios.
	 */
	private Node moveRedLeft( Node pNode ){
		flipColors(pNode);
		if( isRed(pNode.right.left)){
			pNode.right = rotateRight(pNode.right);
			pNode = rotateLeft(pNode);
			flipColors(pNode);
		}
		return pNode;
	}

	/**
	 * Asumiendo que pNode es red y pNode.right y pNode.right.left son negros,
	 * cambia pNode.right o alguno de sus hijos a rojo
	 * @param pNode Raíz del nodo que se quiere cambiar el rojo a la izquierda.
	 * @return raíz del nuevo sub-árbol después de los cambios.
	 */
	private Node moveRedRight( Node pNode ){
		flipColors(pNode);
		if( isRed(pNode.left.left)){
			pNode = rotateLeft(pNode);
			flipColors(pNode);
		}
		return pNode;
	}

	/**
	 * Balancea el árbol con raíz en pNode
	 * @param pNode raíz del árbol que se va a balancear
	 * @return Raíz del árbol balanceado
	 */
	private Node balance( Node pNode ){
		if( isRed(pNode.right) )
			pNode = rotateLeft(pNode);
		if( isRed(pNode.left) && isRed(pNode.left.left) )
			pNode = rotateRight(pNode);
		if( isRed(pNode.left) && isRed(pNode.right) )
			flipColors(pNode);

		pNode.size = size(pNode.left) + size(pNode.right) + 1;
		return pNode;
	}

	/**
	 * @return altura del árbol
	 */
	public int height(){
		return height(root);
	}

	/**
	 * @param pNode Raíz del sub-árbol del cual se quiere saber la altura.
	 * @return Altura del árbol con raíz en pNode.
	 */
	private int height( Node pNode ){
		if( pNode == null )
			return -1;
		return 1 + Math.max(height(pNode.left), height(pNode.right));
	}

	/**
	 * @return Llave más pequeña del árbol
	 * @throws NoSuchElementException si el árbol está vacío
	 */
	public Key min(){
		if( isEmpty() )
			throw new NoSuchElementException("Llamada a min() sobre un árbol vacío");
		return min(root).key;
	}

	/**
	 * @param pNode Raíz del sub-árbol del cual se quiere encontrar el mínimo
	 * @return Nodo más pequeño del sub-árbol con raíz en pNode
	 */
	private Node min( Node pNode ){
		if( pNode.left == null )
			return pNode;
		else
			return min( pNode.left );
	}

	/**
	 * @return Llave más grande del árbol
	 * @throws NoSuchElementException si el árbol está vacío
	 */
	public Key max(){
		if( isEmpty() )
			throw new NoSuchElementException("Llamada a max() sobre un árbol vacío");
		else
			return max(root).key;
	}


	/**
	 * @param pNode Raíz del sub-árbol del cual se quiere encontrar el máximo
	 * @return Nodo más grande del sub-árbol con raíz en pNode
	 */
	private Node max( Node pNode ){
		if( pNode.right == null )
			return pNode;
		else
			return max( pNode.right );
	}

	/**
	 * @return Retorna todas las llaves del árbol como un Iterable
	 */
	public Queue<Key> keys(){
		if( isEmpty() )
			return new Queue<Key>();
		return keys( min(), max() );
	}

	/**
	 * @param lo inicio del rango
	 * @param hi fin del rango
	 * @return Retorna todas las llaves del árbol en el rango dado
	 */
	public Queue<Key> keys( Key lo, Key hi){
		if( lo == null ) throw new IllegalArgumentException( "La llave menor es null");
		if( hi == null ) throw new IllegalAccessError("La llave mayor es null");

		Queue<Key> queue = new Queue<Key>();

		keys( root, queue, lo, hi);
		return queue;
	}
	

	/**
	 * Agrega las llaves entre lo y hi del sub-árbol con raíz en pNode al queue.
	 * @param pNode Raíz del árbol
	 * @param pQueue Queue al que se agregan las llaves
	 * @param lo inicio del rango
	 * @param hi fin del rango
	 */
	private void keys( Node pNode, Queue<Key> pQueue, Key lo, Key hi ){
		if( pNode == null )
			return;
		int cmpLo = lo.compareTo(pNode.key);
		int cmpHi = hi.compareTo(pNode.key);
		if( cmpLo < 0 )
			keys( pNode.left, pQueue, lo, hi);
		if( cmpLo <= 0 && cmpHi >= 0) pQueue.enqueue(pNode.key);
		if( cmpHi > 0 )
			keys( pNode.right, pQueue, lo, hi );
	}
	
	/**
	 * @param init inicio del rango
	 * @param end final del rango
	 * @return todas las llaves en el árbol en el rango dado
	 */
	public Queue<Key> valuesInRange( Key init, Key end ){
		if( init == null)
			throw new IllegalArgumentException("La llave inicial es null");
		if( end == null )
			throw new IllegalArgumentException( "La llave final es null" );
		Queue<Key> queue = new Queue<Key>();
		valuesInRange( root, queue, init, end);
		return queue;
	}
	
	/**
	 * Agrega las llaves del árbol con raíz en pNode que son mayores que init y menores que end al queue. 
	 * @param pNode raíz del árbol
	 * @param pQueue cola a la que se agregan las llaves
	 * @param init llave mínima
	 * @param end llave máxima
	 */
	private void valuesInRange( Node pNode, Queue<Key> pQueue, Key init, Key end ){
		if( pNode == null )
			return;
		int cmpInit = init.compareTo(pNode.key);
		int cmpEnd = end.compareTo(pNode.key);
		if( cmpInit < 0)
			valuesInRange( pNode.left, pQueue, init, end );
		if( cmpInit <= 0 && cmpEnd >= 0)
			pQueue.enqueue(pNode.key);
		if( cmpEnd > 0)
			valuesInRange(pNode.right, pQueue, init, end);
	}
	
	/**
	 * @return true si el árbol es un árbol de búsqueda binario y está balanceado, false de lo contrario.
	 */
	private boolean check(){
		if(!isBST())
			System.out.println("No está en un orden simétrico.");
		if( !isBalanced() )
			System.out.println("No está balanceado.");
		return isBST() && isBalanced();
	}
 
	/**
	 * @return true si el árbol está en un orden simétrico.
	 */
	private boolean isBST(){
		return isBST(root, null, null);
	}
	
	/**
	 * @param pNode raíz del árbol
	 * @param min valor máximo de las llaves del árbol.
	 * @param max valor mínimo de las llaves del árbol.
	 * @return true si el árbol con raíz en pNode tiene todas sus llaves entre min y max,
	 * false de lo contrario.
	 */
	private boolean isBST( Node pNode, Key min, Key max ){
		if( pNode == null )
			return true;
		if( min != null && pNode.key.compareTo(min) <= 0)
			return false;
		if( max != null && pNode.key.compareTo(max) >= 0)
			return false;
		return isBST(pNode.left, min, pNode.key) && isBST(pNode.right, pNode.key, max);
	}

	/**
	 * @return true si todas las hojas desde la raíz están a la misma distancia, false de lo contrario. 
	 */
	private boolean isBalanced(){
		int black = 0;
		Node x = root;
		while( x != null){
			if(!isRed(x))
				black++;
			x = x.left;
		}
		return isBalanced(root, black);
	}

	/**
	 * @param pNode raíz del árbol
	 * @param black número de links negros
	 * @return true si todas las hojas del árbol están a la misma distancia de pNode, false de lo contrario.
	 */
	private boolean isBalanced( Node pNode, int black ){
		if( pNode == null )
			return black == 0;
		if( !isRed(pNode) )
			black--;
		return isBalanced( pNode.left, black ) && isBalanced(pNode.right, black);
	}
	
	public String toString(){
		String ans = "";
		for( Key key: keys() ){
			ans = ans + "[ " + key + ", " + get(key) + " ]";
		}
		return ans;
	}
}
