package model.data_structures;


import java.util.Iterator;
import java.util.NoSuchElementException;

import api.IQueue;


/**
 * Clase que modela una fila
 * @author hd.castellanos, da.ramos
 * Implementaci�n basada en: http://algs4.cs.princeton.edu/13stacks/Queue.java.html
 * @param <E>
 */
public class Queue <E> implements IQueue<E>  {


	/**
	 * Atributo para el primer nodo de la fila
	 */
	private Node<E> first;

	/**
	 * Atributo para le ultimo nodo de la fila
	 */
	private Node<E > last;

	/**
	 * Atributo para el numero de nodos en la lista
	 */
	private int size;


	/**
	 * Clase que modela un nodo de la pila o de la fila
	 * @author hd.castellanos,da.ramons
	 *
	 * @param <E> un elemento
	 */
	public  static class Node<E> {
		/**
		 * Atributo para el elemento del nodo
		 */
		private E element;
		/**
		 * Atributo para el siguiente nodo
		 */
		private Node<E> next;


		/**
		 * Crea un nuevo nodo con el elemento que llega por parametro 
		 * y su nodo siguiente es nulo
		 * @param pElement elemento del nuevo nodo
		 */
		public Node (E pElement){
			element = pElement;
			next = null;
		}

		/**
		 * Cambia el nodo siguiente por el nodo que llega por parametro
		 * @param pNode nuevo nodo siguiente
		 */
		public void changeNext(Node<E> pNode){
			next = pNode;
		}

		/**
		 * @return el nodo siguiente
		 */
		public Node<E> getNext(){
			return next;
		}

		/**
		 * @return el elemento del nodo actual
		 */
		public E getElement(){
			return element;
		}

		/**
		 * Cambia el elemento del nodo por el elemento que llega por parametro
		 * @param pElement Nuevo elemento que tendra el nodo
		 */
		public void changeElemet (E pElement){
			element = pElement;
		}
		
		public String toString(){
			return element + "";
		}
	}



	/**
	 * Iniciliza la fila vacia, first = null, last = null, size = 0
	 */
	public Queue(){
		first = null;
		last = null;
		size = 0;
	}


	/**
	 * @return true si la fila est� vac�a, false si no est� vac�a
	 */

	public boolean isEmpty()
	{
		return size == 0;	
	}

    /**   
     * @return el ultimo nodo de la fila
     */
	public Node <E> getlast(){
		return last;
	}

	/**
	 * @return El tama�o de la fila 
	 */
	public int getSize(){
		return size;
	}
	/**
	 * Agrega un  nuevo nodo  con el elemento dado por parametro  a la fila, modifica el tama�o sumandole 1 
	 * @param pElement element to add
	 */
	public void enqueue(E pElement) {

		Node <E> OldLast = last;
		last = new Node<E>(pElement);
		last.changeNext(null);
		if (isEmpty())
			first = last;
		else OldLast.changeNext(last);
		size ++;
	}

	/**
	 * Remueve y retorna el elemento en esta fila que esta en la primera posici�n
	 * @returns El elemento que se elimin�
	 * @throws NoSuchElementException Si la fila esta vacia 
	 */
	public E dequeue() {
		if (isEmpty())
			throw new NoSuchElementException("la fila esta vacia");
		E element = first.getElement();
		first = first.getNext();
		size --;
		if (isEmpty())
			last = null;
		return element;
	}

	/**
	 * @return el primer nodo de la fila
	 * @throws NoSuchElementException si la fila esta vacia 
	 */
	public E peek(){
		if (isEmpty())
			throw new NoSuchElementException("La fila esta vacia");
		return first.element;
	}
	
	public String toString(){
		String ans = "[";
		for( E current : this){
			ans = ans + current + ",";
		}
		ans = ans + "]";
		return ans;
	}


	@Override
	public Iterator<E> iterator() {
		return new ListIterator<E>(first);
	}
	
	private class ListIterator<E> implements Iterator<E>{
		private Node<E> current;
		
		public ListIterator(Node<E> first) {
			current = first;
		}
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public E next() {
			if( !hasNext() )
				throw new NoSuchElementException();
			E element = current.element;
			current = current.next;
			return element;
		}
		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}



	
}


