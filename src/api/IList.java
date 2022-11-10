package api;

/**
 * Abstract Data Type for a list of generic objects
 * @param <T> Tipo de datos que va a contener la lista.
 */
public interface IList<T> extends Iterable<T>  {

	/**
	 * Retorna el tamaï¿½o de la lista
	 * @return el tamaï¿½o de la lista
	 */
	int getSize();

	/**
	 * Retorna el primer nodo de la lista
	 * @return primer nodo de la lista
	 */
	T getFirst();


	/**
	 * Agrega un Nodo al final de la lista
	 * @param pToAdd informaciï¿½n a agregar
	 */
	void addAtEnd( T pToadd );

	/**
	 * Agrega un Nodo en la posiciï¿½n dada por parametro
	 * @param pToadd Informaciï¿½n a agregar
	 * @param pIndex Indice en el que se quiere agregar
	 */
	void addAtK( T pToAdd, int pIndex );

	/**
	 * Retorna el nodo que se encuentra en la posiciï¿½n dada por parametro
	 * @param pIndex posiciï¿½n del nodo que se quiere recuperar
	 * @return Nodo en la posiciï¿½n dada por parametro
	 */
	T getElement( int pIndex );

	/**
	 * Elimina un nodo de la lista
	 * @param pNode Nodo que se quiere eliminar
	 */
	void delete( T pNode );

	/**
	 * Elimina el nodo que se encuentra en la posiciï¿½n dada
	 * @param pIndex posiciï¿½n del nodo que se quiere eliminar
	 */
	void deleteAtK( int pIndex );

	/**
	 * Retorna la posiciï¿½n del nodo que se quiere buscar
	 * @param pNode Nodo que se quiere buscar
	 * @return posiciï¿½n del nodo que se quiere buscar
	 */
	int positionOf( T pNode );

	/**
	 * Permite saber si la lista se encuentra vacia
	 * @return false si no esta vacia, true si lo esta
	 */
	boolean isEmpty();

	/**
	 * Permite ordenar la lista
	 */
//	void quickSort();
}
