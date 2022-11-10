package model.data_structures;

import java.util.NoSuchElementException;

import api.IRedBlackBST;

/**
 * Implementaciï¿½n de un ï¿½rbol rojo negro.
 * Tomada de: http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
 * @param <Key> Tipo de la las llaves del ï¿½rbol
 * @param <Value> Tipo de los valores del ï¿½rbol
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
	 * Raiz del ï¿½rbol.
	 */
	private Node root;

	/**
	 * Clase privada para los nodos del ï¿½rbol
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
		 * Tamaï¿½o del subarbol
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
	 * Inicializa un ï¿½rbol vacï¿½o
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
	 * @param pNode Raï¿½z del sub-ï¿½rbol del cual se quiere saber el tamaï¿½o.
	 * @return nï¿½mero de nodos en el sub-ï¿½rbol, 0 si el nodo es null.
	 */
	private int size( Node pNode ){
		if( pNode == null) return 0;
		return pNode.size;
	}

	/**
	 * @return Nï¿½mero de parejas llave-valo del ï¿½rbol.
	 */
	public int size(){
		return size(root);
	}

	/**
	 * @return true si el ï¿½rbol estï¿½ vacï¿½o, false de lo contrario.
	 */
	public boolean isEmpty(){
		return root == null;
	}

	/**
	 * @param pKey llave de la que se quiere ver el valor.
	 * @return valor asociado a la llave que entra por parï¿½metro si estï¿½ en el ï¿½rbol o null si no estï¿½.
	 * @throws IllegalArgumentException si la llave es null
	 */
	public Value get( Key pKey ){
		if( pKey == null)
			throw new IllegalArgumentException( "El parï¿½metro de get() es null" );
		return get( root, pKey);
	}

	/**
	 * @param pNode raï¿½z del sub-ï¿½rbol donde se va a buscar
	 * @param pKey llave asociada al valor que se retorna
	 * @return Valor asociado a una llave en el sub-ï¿½rbol con raiz en pNode
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
	 * @param pKey llave que se desea saber si estï¿½ en el ï¿½rbol
	 * @return true si el ï¿½rbol contiene la llave, false de lo contrario
	 * @throws IllegalArgumentException si la llave es null
	 */
	public boolean contains( Key pKey ){
		return get(pKey) != null;
	}

	/**
	 * Inserta el par llave-valor en el arbol, sobreescribiendo el anterior 
	 * valor con el nuevo valor si el ï¿½rbol ya contenï¿½a la llave especificada.
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
	 * Inserta el par llave-valor en el sub-ï¿½rbol con raï¿½z en pNode
	 * @param pNode raï¿½z del sub-ï¿½rbol
	 * @param pKey Llave asociada al valor que se va a insertar
	 * @param pVal Valor que se va a insertar
	 * @return Nueva raï¿½z del sub-ï¿½rbol
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
	 * Elimina la llave mï¿½s pequeï¿½a y su valor asociado del ï¿½rbol.
	 * @throws NoSuchElementException si el ï¿½rbo estï¿½ vacï¿½o
	 */
	public void deleteMin(){
		if( isEmpty() ) throw new NoSuchElementException( "Underflow del ï¿½rbol" );

		if( !isRed(root.left) && !isRed(root.right) )
			root.color = RED;

		root = deleteMin( root );
		if( !isEmpty() ) root.color = BLACK;
		assert check();
	}

	/**
	 * Elimina la llave mï¿½s pequeï¿½a y su valor asociado del sub-ï¿½rbol con raï¿½z en pNode.
	 * @param pNode raï¿½z del sub-ï¿½rbol
	 * @return nuevo sub-ï¿½rbol despuï¿½s de eliminar el par.
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
	 * Elimina la llave mï¿½s grande y su valor asociado del ï¿½rbol.
	 * @throws NosuchElementException si el ï¿½rbol estï¿½ vacï¿½o.
	 */
	public void deleteMax( ){
		if( isEmpty() ) throw new NoSuchElementException( "Underflow del ï¿½rbol" );

		if( !isRed( root.left ) && !isRed(root.right) )
			root.color = RED;

		root = deleteMax( root );
		if( !isEmpty() ) root.color = BLACK;
		assert check();
	}

	/**
	 * Elimina el par llave-valor mï¿½s grande del sub-ï¿½rbol con raï¿½z en pNode
	 * @param pNode raï¿½z del sub-arbol
	 * @return Nuevo subï¿½rbol despuï¿½s de eliminar el par.
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
	 * Elimina la llave especificada y su valor asociado del ï¿½rbol si estï¿½ en ï¿½l
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
	 * Elimina un par llave-valor del sub-ï¿½rbol con raï¿½z en pNode
	 * @param pNode raï¿½z del sub-ï¿½rbol
	 * @param pKey llave a eliminar
	 * @return Raï¿½z del nuevo sub-ï¿½rbol despuï¿½s de eliminar.
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
	 * Rota el sub-ï¿½rbol con raï¿½z en pNode a la derecha
	 * @param pNode raï¿½z del sub-ï¿½rbol
	 * @return Raï¿½z del nuevo ï¿½rbol tras la rotaciï¿½n.
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
	 * Rota el sub-ï¿½rbol con raï¿½z en pNode a la izquierda
	 * @param pNode raï¿½z del sub-ï¿½rbol
	 * @return Raï¿½z del nuevo ï¿½rbol tras la rotaciï¿½n.
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
	 * @param pNode Raï¿½z del nodo que se quiere cambiar el rojo a la izquierda.
	 * @return raï¿½z del nuevo sub-ï¿½rbol despuï¿½s de los cambios.
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
	 * @param pNode Raï¿½z del nodo que se quiere cambiar el rojo a la izquierda.
	 * @return raï¿½z del nuevo sub-ï¿½rbol despuï¿½s de los cambios.
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
	 * Balancea el ï¿½rbol con raï¿½z en pNode
	 * @param pNode raï¿½z del ï¿½rbol que se va a balancear
	 * @return Raï¿½z del ï¿½rbol balanceado
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
	 * @return altura del ï¿½rbol
	 */
	public int height(){
		return height(root);
	}

	/**
	 * @param pNode Raï¿½z del sub-ï¿½rbol del cual se quiere saber la altura.
	 * @return Altura del ï¿½rbol con raï¿½z en pNode.
	 */
	private int height( Node pNode ){
		if( pNode == null )
			return -1;
		return 1 + Math.max(height(pNode.left), height(pNode.right));
	}

	/**
	 * @return Llave mï¿½s pequeï¿½a del ï¿½rbol
	 * @throws NoSuchElementException si el ï¿½rbol estï¿½ vacï¿½o
	 */
	public Key min(){
		if( isEmpty() )
			throw new NoSuchElementException("Llamada a min() sobre un ï¿½rbol vacï¿½o");
		return min(root).key;
	}

	/**
	 * @param pNode Raï¿½z del sub-ï¿½rbol del cual se quiere encontrar el mï¿½nimo
	 * @return Nodo mï¿½s pequeï¿½o del sub-ï¿½rbol con raï¿½z en pNode
	 */
	private Node min( Node pNode ){
		if( pNode.left == null )
			return pNode;
		else
			return min( pNode.left );
	}

	/**
	 * @return Llave mï¿½s grande del ï¿½rbol
	 * @throws NoSuchElementException si el ï¿½rbol estï¿½ vacï¿½o
	 */
	public Key max(){
		if( isEmpty() )
			throw new NoSuchElementException("Llamada a max() sobre un ï¿½rbol vacï¿½o");
		else
			return max(root).key;
	}


	/**
	 * @param pNode Raï¿½z del sub-ï¿½rbol del cual se quiere encontrar el mï¿½ximo
	 * @return Nodo mï¿½s grande del sub-ï¿½rbol con raï¿½z en pNode
	 */
	private Node max( Node pNode ){
		if( pNode.right == null )
			return pNode;
		else
			return max( pNode.right );
	}

	/**
	 * @return Retorna todas las llaves del ï¿½rbol como un Iterable
	 */
	public Queue<Key> keys(){
		if( isEmpty() )
			return new Queue<Key>();
		return keys( min(), max() );
	}

	/**
	 * @param lo inicio del rango
	 * @param hi fin del rango
	 * @return Retorna todas las llaves del ï¿½rbol en el rango dado
	 */
	public Queue<Key> keys( Key lo, Key hi){
		if( lo == null ) throw new IllegalArgumentException( "La llave menor es null");
		if( hi == null ) throw new IllegalAccessError("La llave mayor es null");

		Queue<Key> queue = new Queue<Key>();

		keys( root, queue, lo, hi);
		return queue;
	}
	

	/**
	 * Agrega las llaves entre lo y hi del sub-ï¿½rbol con raï¿½z en pNode al queue.
	 * @param pNode Raï¿½z del ï¿½rbol
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
	 * @return todas las llaves en el ï¿½rbol en el rango dado
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
	 * Agrega las llaves del ï¿½rbol con raï¿½z en pNode que son mayores que init y menores que end al queue. 
	 * @param pNode raï¿½z del ï¿½rbol
	 * @param pQueue cola a la que se agregan las llaves
	 * @param init llave mï¿½nima
	 * @param end llave mï¿½xima
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
	 * @return true si el ï¿½rbol es un ï¿½rbol de bï¿½squeda binario y estï¿½ balanceado, false de lo contrario.
	 */
	private boolean check(){
		if(!isBST())
			System.out.println("No estï¿½ en un orden simï¿½trico.");
		if( !isBalanced() )
			System.out.println("No estï¿½ balanceado.");
		return isBST() && isBalanced();
	}
 
	/**
	 * @return true si el ï¿½rbol estï¿½ en un orden simï¿½trico.
	 */
	private boolean isBST(){
		return isBST(root, null, null);
	}
	
	/**
	 * @param pNode raï¿½z del ï¿½rbol
	 * @param min valor mï¿½ximo de las llaves del ï¿½rbol.
	 * @param max valor mï¿½nimo de las llaves del ï¿½rbol.
	 * @return true si el ï¿½rbol con raï¿½z en pNode tiene todas sus llaves entre min y max,
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
	 * @return true si todas las hojas desde la raï¿½z estï¿½n a la misma distancia, false de lo contrario. 
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
	 * @param pNode raï¿½z del ï¿½rbol
	 * @param black nï¿½mero de links negros
	 * @return true si todas las hojas del ï¿½rbol estï¿½n a la misma distancia de pNode, false de lo contrario.
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
