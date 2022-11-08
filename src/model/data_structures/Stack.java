package model.data_structures;


import java.util.Iterator;
import java.util.NoSuchElementException;

import api.IStack;

public class Stack<E> implements IStack<E>, Iterable<E>, Comparable<Stack<E>>{


	/**
	 * Atributo para el primer nodo de la pila
	 */
	private Node<E> firstE;

	/**
	 * Atributo para el numero de elementos en la pila.
	 */
	private int sizeS;

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
	 * Crea una nueva pila con su primer nodo nulo
	 * y su tamanio = 0
	 */
	public Stack (){
		firstE = null;
		sizeS = 0;
	}


	/**
	 * @return el tamanio de la pila
	 */
	public Integer getSize(){
		return sizeS;
	}

	/**
	 * @return retorna true si la pila esta vacia , false si tiene almenos un elemento 
	 */
	public boolean isEmpty(){
		return sizeS == 0;
	}

	/**
	 * Agrega un nuevo nodo a la pila con el elemento que llega por parametro,
	 * el nuevo nodo es el nuevo first, aumenta el tamano en 1
	 */
	public void push(E pElement) {
		Node <E> oldFirst = firstE ;
		firstE = new Node<E>(pElement);
		firstE.changeElemet(pElement);;
		firstE.changeNext(oldFirst);
		sizeS++;

	}

	/**
	 * Elimina y retorna el primer elemento de la pila
	 */
	public E pop() {
		if (isEmpty())
			throw new NoSuchElementException("La pila esta vacia");
		E element = firstE.getElement();
		firstE = firstE.getNext();
		sizeS --;
		return element;
	}

	/**
	 * @return retorna el elemento del primer nodo de la pila 
	 */
	public Node<E> peek(){
		if (isEmpty())
			throw new NoSuchElementException();
		return firstE;
	}

	public String toString(){
		String ans = "[";
		Node<E> currentNode = peek();
		while( currentNode != null){
			ans = ans + " " + currentNode.getElement();
			currentNode = currentNode.getNext();
		}
		ans = ans + "]";
		return ans;
	}


	@Override
	public Iterator<E> iterator() {
		return new ListIterator<>(firstE);
	}
	
	private class ListIterator<E> implements Iterator<E>{

		private Node<E> current;
		
		public ListIterator( Node<E> first) {
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
			E item = current.getElement();
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public int compareTo(Stack<E> stack) {
		if( sizeS > stack.sizeS )
			return 1;
		else if (sizeS < stack.sizeS)
			return -1;
		else
			return 0;
	}
}
