package model.data_structures;



import java.util.Iterator;
import java.util.NoSuchElementException;

import api.IList;

/**
 * Implementaciï¿½n de una lista doblemente encadenada con referencias a su primer y ï¿½ltimo elemento.
 * Se empiezan a contar los nodos desde 0.
 * Implementaciï¿½n basada en: http://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html
 * @author da.ramos, hd.castellanos
 * @param <T> Tipo de datos que va a manejar la lista.
 */
public class Bag<T> implements IList<T>, Iterable<T>{

	/**
	 * Primer nodo de la lista.
	 */
	private Node<T> first;

	/**
	 * ï¿½ltimo nodo de la lista.
	 */
	private Node<T> last;

	/**
	 * Tamaï¿½o de la lista.
	 */
	private int size;

	/**
	 * Crea una lista vacï¿½a.
	 */
	public Bag(){
		first = null;
		last = null;
		size = 0;
	}

	/**
	 * Retorna el tamaï¿½o de la lista.
	 * @return Tamaï¿½o de la lista.
	 */
	@Override
	public int getSize() {
		return size;
	}

	/**
	 * Indica si la lista estï¿½ vacï¿½a.
	 * @return true si la lista estï¿½ vacï¿½a, false de lo contrario.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Retorna el primer elemento de la lista.
	 * @return Primer elemento de la lista.
	 */
	public T getFirst(){
		return first.data;
	}

	/**
	 * Retorna el ï¿½ltimo elemento de la lista.
	 * @return ï¿½ltimo elemento de la lista.
	 */
	public T getLast(){
		return last.data;
	}


	/**
	 * Agrega un elemento en el ï¿½ltimo lugar de la lista encadenada.
	 * @param pToAdd informaciï¿½n que se va a agregar.
	 */
	@Override
	public void addAtEnd(T pToAdd) {
		insertAfter(last, pToAdd);
	}

	/**
	 * Agrega un elemento el el primer lugar de la lista encadenada.
	 * @param pToAdd Informaciï¿½n que se va a agregar.
	 */
	public void addAtBeginning( T pToAdd ){
		Node<T> toAdd = new Node<>();
		toAdd.data = pToAdd;

		if( isEmpty()){
			first = toAdd;
			last = toAdd;
		}
		else{
			first.previous = toAdd;
			toAdd.next = first;
			first = toAdd;
		}
	}

	/**
	 * Inserta un nuevo nodo despuï¿½s de otro nodo en la lista.
	 * @param pPrevious Nodo al que se le va a agregar despuï¿½s el nuevo nodo.
	 * @param pData Informaciï¿½n que se va a agregar.
	 */
	private void insertAfter( Node<T> pPrevious, T pData ){
		Node<T> toAdd = new Node<>();
		toAdd.data = pData;

		if( isEmpty()){
			first = toAdd;
			last = toAdd;
		}
		else if( last == pPrevious ){
			pPrevious.next = toAdd;
			toAdd.previous = pPrevious;
			last = toAdd;
		}
		else{
			toAdd.next = pPrevious.next;
			toAdd.previous = pPrevious;
			pPrevious.next.previous = toAdd;
			pPrevious.next = toAdd;
		}
		size++;
	}

	/**
	 * Agrega un nodo en una posiciï¿½n de la lista.
	 * @param pToAdd Informaciï¿½n que se va a agregar.
	 * @param pIndex ï¿½ndice de la lista donde se va a agregar el nodo.
	 */
	@Override
	public void addAtK(T pToAdd, int pIndex) {
		if( pIndex < size ){
			if( pIndex == 0){
				addAtBeginning(pToAdd);
			}
			else
				insertAfter(getNode(pIndex), pToAdd);
		}
		else if( pIndex == size)
			addAtEnd(pToAdd);
		else
			throw new NoSuchElementException( "El ï¿½ndice ingresado no existe" );
	}

	/**
	 * Reemplaza el valor del nodo con el ï¿½ndice ingresado por parï¿½metro por la informaciï¿½n ingresada por parï¿½metro.
	 * @param pData Informaciï¿½n que se va a reemplazar en el nodo.
	 * @param pIndex ï¿½ndice del nodo en el que se quiere ingresar la informaciï¿½n.
	 */
	public void replace( T pData, int pIndex ){
		Node<T> current = null;
		if( !isEmpty()){
			current = first;
			for( int i = 0; i < pIndex; i++){
				current = current.next;
			}
			current.data = pData;
		}
		else{
			addAtEnd(pData);
		}
	}


	/**
	 * Retorna el elemento en el ï¿½ndice ingresado por parï¿½metro.
	 * Tomado de "A practical guide to data structures and algorithms using Java"
	 * @param pIndex ï¿½ndice del elemento.
	 * @return Elemento ubicado en el ï¿½ndice que entra por parï¿½metro.
	 */
	@Override
	public T getElement(int pIndex) {
		Node<T> current = null;
		if( !isEmpty() && pIndex < size && pIndex >= 0){
			current = first;
			for( int i = 0; i < pIndex; i++){
				current = current.next;
			}
			return current.data;
		}
		return null;
	}

	/**
	 * Retorna el nodo en el ï¿½ndice ingresado por parï¿½metro.
	 * @param pIndex ï¿½ndice de la posiciï¿½n donde se va a agregar el elemento.
	 * @return Nodo ubicado en el ï¿½ndice que entra por parï¿½metro.
	 */
	private Node<T> getNode(int pIndex) {
		Node<T> current = null;
		if( !isEmpty() && pIndex < size && pIndex >= 0){
			current = first;
			for( int i = 0; i < pIndex; i++){
				current = current.next;
			}
			return current;
		}
		return null;
	}

	/**
	 * Elimina un nodo de la lista.
	 * @param pNode elimina el nodo que se le pasa por parï¿½metro.
	 */
	@Override
	public void delete( T pNode ) {
		int position = positionOf( pNode );
		Node<T> toDelete = getNode( position );
		if( toDelete == null ){
			throw new NoSuchElementException( "El nodo no existe." );
		}
		else if( toDelete == first ){
			Node<T> next = getNode( positionOf ( toDelete.data ) + 1 );
			if(next != null){
				next.previous = null;
			}
			first = next;
			toDelete.next = null;
		}
		else if( toDelete == last ){
			Node<T> previous = getNode( positionOf ( toDelete.data ) - 1 );
			previous.next = null;
			last = previous;
			toDelete.previous = null;
		}
		else{
			Node<T> next = getNode(positionOf(toDelete.data) + 1 );
			Node<T> previous = getNode(positionOf(toDelete.data) - 1 );
			previous.next = next;
			next.previous = previous;
			toDelete.next = null;
			toDelete.previous = null;
		}
		size--;
	}

	/**
	 * Elimina un nodo de la posiciï¿½n que entra por parï¿½metro.
	 */
	@Override
	public void deleteAtK(int pIndex) {
		delete(getElement(pIndex));
	}

	/**
	 * Retorna la posiciï¿½n del elemento ingresado por parï¿½metro.
	 * Mï¿½todo tomado de "A practical guide to algorithms and data structures using java"
	 * @return Posiciï¿½n del elemento ingresado por parï¿½metro.
	 */
	@Override
	public int positionOf(T pData) {
		Node<T> current;
		Integer pos;
		for( pos = 0, current = first; pos < size; pos++, current = current.next)
			if( current.data.equals(pData) )
				return pos;

		throw new NoSuchElementException();
	}


	public boolean contains( T pData ){
		for( T element : this ){
			if( element.equals(pData))
				return true;
		}
		return false;
	}

	/**
	 * Imprime en consola la informacion de los nodos de la lista
	 */
	public String toString(){
		String ans = "[";
		for( T element : this ){
			ans = ans + element + " ";
		}
		ans = ans + "]";
		return ans;
	}

	/**
	 * Retorna el iterador de la lista.
	 */
	@Override
	public Iterator<T> iterator() {
		return new DLLIterator();
	} 

	/**
	 * Cada nodo contiene un elemento.
	 * Clase tomada de 
	 * @param <T> Tipo de elemento que tendrï¿½ el nodo
	 */
	private class Node<T>{

		/**
		 * Informaciï¿½n contenida por el nodo.
		 */
		private T data;

		/**
		 * Apuntador al siguiente nodo.
		 */
		private Node<T> next;

		/**
		 * Apuntador al nodo anterior.
		 */
		private Node<T> previous;

		/**
		 * Imprime la informaciï¿½n del nodo en la consola.
		 */
		public String toString(){
			return data + ",";
		}
	}

	/**
	 * Iterador de la lista.
	 */
	private class DLLIterator implements Iterator<T>{
		/**
		 * Elemento en el que se encuentra el iterador.
		 * Inicialmente es el primero.
		 */
		private Node<T> current = first;

		/**
		 * ï¿½ltimo elemento en el que estuvo el iterador.
		 */
		private Node<T> lastAccessed;

		/**
		 * ï¿½ndice del elemento en el que se encuentra el iterador.
		 */
		private int index = 0;


		/**
		 * @return true si el elemento actual tiene un elemento siguiente, false de lo contrario.
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * @return elemento siguiente al actual, si existe.
		 */
		@Override
		public T next() {
			if( !hasNext())
				throw new NoSuchElementException();
			else{
				lastAccessed = current;
				T item = current.data;
				current = current.next;
				index++;
				return item;
			}
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
	}
}
