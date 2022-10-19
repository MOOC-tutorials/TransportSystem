package api;

/**
 * Interfaz de un grafo dirigido
 * @param <K> Tipo de dato que representa los identificadores únicos de los vértices
 * @param <V> Tipo de dato de la información de los vértices
 */
public interface IDirectedGraph<K,V>  {

	public void addVertex( K id, V info );
	
	public void addEdge( K source, K dest, double weight );
	
}
