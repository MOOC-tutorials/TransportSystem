package model.data_structures;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Implementación de una cola de prioridad.
 * Tomada de http://algs4.cs.princeton.edu/24pq/MaxPQ.java.html
 * @param <T> Tipo de elementos de la cola de prioridad.
 */
public class MaxPriorityQueue<T extends Comparable<T>> implements Iterable<T> {

	/**
	 * Arreglo sobre el que se implementa la cola de prioridad
	 */
	private  T[]pq;
	
	/**
	 * Tamaño de la cola; 
	 */
	private int size;
	/**
	 * Comparador de los elementos
	 */
	private Comparator<T> comparator;

	/**
	 * Crea una nueva cola de prioridad vacía.
	 * @param initCapacity Capacidad inicial de la cola de prioridad
	 */
	@SuppressWarnings("unchecked")
	public MaxPriorityQueue(int initCapacity) {
		pq = (T[]) new Comparable[initCapacity + 1];
		size = 0;
	}

	/**
	 * @return true si la cola de prioridad está vacía, false de lo contrario.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * @return Elemento con más prioridad de la lista.
	 */
	public T max() {
		if (isEmpty()) throw new NoSuchElementException("cola de prioridad underflow");
		return pq[1];
	}
	/**
	 * Copia todos los elementos a un nuevo arreglo del tamaño ingresado por parámetro.
	 * @param capacity nuevo tamaño del arreglo. Debe ser mayor que ele actual.
	 */
	private void resize(int capacity) {
		assert capacity > size;
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) new Comparable[capacity];
		for (int i = 1; i <= size; i++) {
			temp[i] = pq[i];
		}
		pq = temp;
	}

	/**
	 * Inserta un elemento al final y lo sube hasta su lugar según su prioridad.
	 * @param pToadd Elemento que se va a insertar.
	 */
	public void insert(T pToadd) {
		if (size == pq.length - 1) 
			resize(2 * pq.length);
		pq[++size] = pToadd;
		swim(size);
		assert isMaxHeap();
	}

	/**
	 * Retorna el elemento de mayor prioridad y lo elimina.
	 * @return Elemento de mayor prioridad.
	 */
	public T delMax() {
		if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
		T max = pq[1];
		exch(1, size--);
		sink(1);
		pq[size+1] = null;     
		if ((size > 0) && (size == (pq.length - 1) / 4)) resize(pq.length / 2);
		assert isMaxHeap();
		return max;
	}

	/**
	 * Recorre toda la cola verificando que los elementos estén en orden,
	 * Si encuentra un elemento que no está en su lugar, lo intercambia hasta que esté bien.
	 * @param k Número de veces que se desea hacer el swim.
	 */
	private void swim(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k, k/2);
			k = k/2;
		}
	}

	/**
	 * Intercambia dos elementos.
	 * @param i Posición del primer elemento.
	 * @param j Posición del segundo elemento
	 */
	private void exch(int i, int j) {
		T swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	/**
	 * Recorre la lista intercambiando los elementos con su siguiente si encuentra que no están ordenados por prioridad.
	 * @param k Número de elementos en los que se desea hacer el sink.
	 */
	private void sink(int k) {
		while (2*k <= size) {
			int j = 2*k;
			if (j < size && less(j, j+1))
				j++;
			if (!less(k, j)) 
				break;
			exch(k, j);
			k = j;
		}
	}

	/**
	 * Comprueba si el elemento i es menor que el elemento j.
	 * @param i Posición de elemento 1.
	 * @param j Posición del elemento 2.
	 * @return true si el elemento 1 es menor que el 2, false de lo contrario.
	 */
	private boolean less(int i, int j) {
		if (comparator == null) {
			return ((Comparable<T>) pq[i]).compareTo(pq[j]) < 0;
		}
		else {
			return comparator.compare(pq[i], pq[j]) < 0;
		}
	}

	/**
	 * Revisa si el priority queue es un heap con el máximo como raiz.
	 * @return true si es un heap, false de lo contrario.
	 */
	private boolean isMaxHeap() {
		return isMaxHeap(1);
	}

	/**
	 * Revisa si el sub heap con raiz en K es un heap.
	 * @param k Posición de la raiz del sub heap.
	 * @return true si el sub heap es un heap, false de lo contrario.
	 */
	private boolean isMaxHeap(int k) {
		if (k > size) 
			return true;
		int left = 2*k;
		int right = 2*k + 1;
		if (left  <= size && less(k, left))  
			return false;
		if (right <= size && less(k, right)) 
			return false;
		return isMaxHeap(left) && isMaxHeap(right);
	}

	/**
	 * @return tamaño del priority queue.
	 */
	public int size() {
		return size;
	}

	public String toString(){
		String ans = "[";
		for( T element : this ){
			ans = ans + element + "\n ";
		}
		ans = ans + "]";
		return ans;
	}
	/**
	 * Iterador de la cola de prioridad.
	 */
	private class PqIterator implements Iterator<T> {

		/**
		 * Copia de la cola de prioridad.
		 */
		private MaxPriorityQueue<T> copy;

		/**
		 * Copia todos los elementos de la cola de prioridad.
		 */
		public PqIterator() {
			if (comparator == null) 
				copy = new MaxPriorityQueue<T>(size());

			for (int i = 1; i <= size; i++)
				copy.insert(pq[i]);
		}

		/**
		 * @return true si la copia no está vacía, false de lo contrario. 
		 */
		public boolean hasNext(){ 
			return !copy.isEmpty();
		}
		
		/**
		 * 
		 */
		public void remove(){ 
			throw new UnsupportedOperationException();  
			}

		public T next() {
			if (!hasNext()) throw new NoSuchElementException();
			return copy.delMax();
		}
	}

	/**
	 * Retorna un iterador para la cola de prioridad.
	 */
	@Override
	public Iterator<T> iterator() {
	
		return new PqIterator();
	}
}
