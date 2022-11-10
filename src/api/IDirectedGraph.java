package api;

/**
 * Interfaz de un grafo dirigido
 * @param <K> Tipo de dato que representa los identificadores ï¿½nicos de los vï¿½rtices
 * @param <V> Tipo de dato de la informaciï¿½n de los vï¿½rtices
 */
public interface IDirectedGraph<K,V>  {

	public void addVertex( K id, V info );
	
	public void addEdge( K source, K dest, double weight );
	
}
