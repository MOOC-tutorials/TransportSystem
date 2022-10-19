package api;

/**
 * Interfaz de un grafo dirigido
 * @param <K> Tipo de dato que representa los identificadores �nicos de los v�rtices
 * @param <V> Tipo de dato de la informaci�n de los v�rtices
 */
public interface IDirectedGraph<K,V>  {

	public void addVertex( K id, V info );
	
	public void addEdge( K source, K dest, double weight );
	
}
