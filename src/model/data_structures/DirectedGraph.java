package model.data_structures;

import java.util.NoSuchElementException;

import api.IDirectedGraph;

/**
 * https://algs4.cs.princeton.edu/42digraph/Digraph.java.html
 * https://algs4.cs.princeton.edu/43mst/EdgeWeightedGraph.java.html
 * @param <K>
 * @param <V>
 */
public class DirectedGraph<K, V> implements IDirectedGraph<K, V> {

	/**
	 * Nï¿½mero de vï¿½rtices del grafo
	 */
	private int V;

	/**
	 * Nï¿½mero de ejes del grafo
	 */
	private int E;

	private int capacity;

	private int freeVertexIndex;

	/**
	 * Lista de adyacencia
	 */
	private Bag<DirectedEdge<K>>[] adj;

	/**
	 * Lista de vï¿½rtices del grafo
	 */
	private Vertex<K, V>[] vertices;

	/**
	 * Lista de nï¿½meros de ejes de entrada por vï¿½rtice
	 */
	private int[] indegree;

	/**
	 * Crea un nuevo grafo con pesos dirigido con tamaï¿½o inicial
	 * @param V nï¿½mero inicial de vï¿½rtices
	 */
	@SuppressWarnings("unchecked")
	public DirectedGraph( int V ) { 
		if( V < 0 ) throw new IllegalArgumentException( "El nï¿½mero de vï¿½rtices del grafo debe ser mayor a 0.");
		this.V = 0;
		this.E = 0;
		capacity = V;
		freeVertexIndex = 0;
		indegree = new int[V];
		adj = ( Bag<DirectedEdge<K>>[] ) new Bag[V];
		vertices = (Vertex<K, V>[]) new Vertex[V];
		for( int v = 0; v < V; v++){
			adj[v] = new Bag<DirectedEdge<K>>();
		}
	}	

	/**
	 * @return nï¿½mero de vï¿½rtices del grafo
	 */
	public int V(){
		return V;
	}

	/**
	 * @return Nï¿½mero de ejes del grafo
	 */
	public int E(){
		return E;
	}

	/**
	 * @return capacidad de vï¿½rtices del grafo
	 */
	public int capacity(){
		return capacity;
	}

	/**
	 * @return true si no tiene vï¿½rtices, false de lo contrario
	 */
	public boolean isEmpty(){
		return V == 0;
	}

	/**
	 * Mï¿½todo para saber la posiciï¿½n en el arreglo de un vï¿½rtice co llave K
	 * @param key llave del vï¿½rtice 
	 * @return posiciï¿½n del vï¿½rtice en el arreglo 
	 * @throws NoSuchElementException si el vï¿½rtice no se encuentra (no deberï¿½a lanzarce nunca)
	 */
	private int positionOf( K key ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El vï¿½rtice con la llave " + key + " no se encuentra en el grafo" );
	}

	/**
	 * @param key llave del vï¿½rtice
	 * @return true si el grafo contien el vï¿½rtice de llave K, false de lo contrario
	 */
	public boolean containsVertex( K key ){
		if( !isEmpty() ){
			for( int i = 0; i < V; i++){
				Vertex<K, V> currentVertex = vertices[i];
				if( key.equals(currentVertex.getKey())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @param edge eje que se quiere comprobar si esï¿½ en el grafo
	 * @return treu si el eje existe en el grafo, false de lo contrario
	 */
	public boolean containsEdge( DirectedEdge<K> edge ){
		if( E > 0 ){
			for( DirectedEdge<K> currentEdge : adj(edge.fromV()) ){
				if( currentEdge.toV().equals(edge.toV()) && Double.compare(currentEdge.weight(), edge.weight()) == 0)
					return true;
			}
		}
		return false;
	}

	/**
	 * @param key llave del vï¿½rtice que se quiere obtener
	 * @return vï¿½rtice con la llave K
	 * @throws NoSuchElementException Si el vï¿½rtice no se encuentra en el grafo
	 */
	public Vertex<K, V> getVertex( K key ) throws NoSuchElementException{
		for( Vertex<K, V> currentVertex : vertices ){
			if( currentVertex.getKey().equals(key)){
				return currentVertex;
			}
		}
		throw new NoSuchElementException("El vï¿½rtice con la llave " + key + " no estï¿½ en el grafo");
	}

	/**
	 * Aumenta el tamaï¿½o de los arreglos a la nueva capacidad, para que el grafo pueda tener mï¿½s vï¿½rtices
	 * @param newSize Nuevo tamaï¿½o de los arreglos
	 */
	@SuppressWarnings("unchecked")
	private void resize( int newSize ){
		Bag<DirectedEdge<K>>[] auxAdj = (Bag<DirectedEdge<K>>[]) new Bag[newSize];
		Vertex<K, V>[] auxVertices = (Vertex<K, V>[]) new Vertex[newSize];
		int[] auxIndegree = new int[newSize];
		for( int i = 0; i < V; i++ ){
			auxAdj[i] = adj[i];
			auxVertices[i] = vertices[i];
			auxIndegree[i] = indegree[i];
		}
		capacity = newSize;
		adj = auxAdj;
		vertices = auxVertices;
		indegree = auxIndegree;
	}

	/**
	 * Agrega un eje entre dos vï¿½rtices
	 * @param fromV llave del vï¿½rtice origen
	 * @param toV llave del vï¿½rtice destino
	 * @param weight peso del vï¿½rtice
	 */
	public void addEdge( K fromV, K toV, double weight ) throws IllegalStateException{
		DirectedEdge<K> toAdd = new DirectedEdge<K>(fromV, toV, weight);
		if( !containsEdge(toAdd) ){
			adj[positionOf(fromV)].addAtEnd(toAdd);
			indegree[positionOf(toV)]++;
			E++;
		}
		else{
			throw new IllegalStateException( "El eje " + toAdd + " ya estï¿½ en el grafo" );
		}
	}

	/**
	 * Agrega un nuevo vï¿½rtice al grafo
	 * @param id llave del vï¿½rtice
	 * @param info informaciï¿½n del vï¿½rtice
	 */
	@Override
	public void addVertex(K id, V info) {
		if( V + 1 <= capacity ){
			if( !containsVertex(id) ){
				vertices[freeVertexIndex] = new Vertex<>(id, info);
				freeVertexIndex++;
				V++;
			}
			else{
				vertices[positionOf(id)].setValue(info);
			}
		}
		else{
			resize(capacity * 2);
			addVertex(id, info);
		}
	}

	/**
	 * Elimina un eje
	 * @param fromV llave del vï¿½rtice origen
	 * @param toV llave del vï¿½rtice destino
	 * @param weight peso del vï¿½rtice
	 */
	public void deleteEdge( DirectedEdge<K> edge ) throws NoSuchElementException{
		try{
			adj[positionOf(edge.fromV())].delete( edge );
		}catch (NoSuchElementException e ){
			throw new NoSuchElementException( "El vï¿½rtice no existe" );
		}
		E--;
	}

	/**
	 * Elimina un eje
	 * @param fromV llave del vï¿½rtice origen
	 * @param toV llave del vï¿½rtice destino
	 * @param weight peso del vï¿½rtice
	 */
	public void deleteEdge( K fromV, K toV, double weight ) throws NoSuchElementException{
		deleteEdge(new DirectedEdge<K>(fromV, toV, weight));
	}


	/**
	 * Elimina un vï¿½rtice del grafo
	 * @param id llave del vï¿½rtice
	 * @param info informaciï¿½n del vï¿½rtice
	 */
	public void deleteVertex(K id) throws NoSuchElementException{
		if( containsVertex(id) ){
			for( int i = 0; i < V; i++ ){
				if( vertices[i] != null && !adj[i].isEmpty() ){
					for( DirectedEdge<K> currentEdge : adj[i] ){
						if( currentEdge.toV().equals(id) )
							deleteEdge(currentEdge);
					}
				}
			}
			for( int i = positionOf(id); i < V-1; i++){
				vertices[i] = vertices[i+1];
				adj[i] = adj[i+1];
				freeVertexIndex--;
			}
			V--;
		}
		else{
			throw new NoSuchElementException("El vï¿½rtice no se encuentra en el grafo");
		}
		if( V - 1 < capacity / 2 ){
			resize(capacity /2);
		}
	}

	/**
	 * @param key llave del vï¿½rtice 
	 * @return Iterable de ejes que salen del vï¿½rtice de llave key.
	 */
	public Iterable<DirectedEdge<K>> adj( K key ){
		return adj[positionOf(key)];
	}

	/**
	 * @param key llave del vï¿½rtice
	 * @return nï¿½mero de ejes que salen del vï¿½rtice
	 */
	public int outdegree( K key ){
		return adj[positionOf(key)].getSize();
	}

	/**
	 * @param key llave del vï¿½rtice
	 * @return nï¿½mero de ejes que llegan al vï¿½rtice
	 */
	public int indegree( K key ){
		return indegree[positionOf(key)];
	}

	/**
	 * @param key llave del vï¿½rtice
	 * @return iterable con los ejes que llegan al vï¿½rtice con llave key
	 */
	public Iterable<DirectedEdge<K>> edgesTo( K key ){
		Queue<DirectedEdge<K>> ans = new Queue<>();
		for( DirectedEdge<K> currentEdge : edges() )
		{
			if (currentEdge.toV() == key ) {
			ans.enqueue(currentEdge);
			}
		}
		
		return ans;
	}
	
	/**
	 * retorna el nï¿½mero de ejes que llegan al vertice con llave K
	 * @param Key llave del vertice
	 * @return el nï¿½mero de ejes que llegan al vertice con llave K
	 */
	
	public int sizeOfEdgesTo(K Key){
		Queue<DirectedEdge<K>> ans = new Queue<>();
		for( DirectedEdge<K> currentEdge : edgesTo(Key) ){
			ans.enqueue(currentEdge);
		}
		
		return ans.getSize();
	}
	
	/**
	 * retorna el nï¿½mero de ejes que salen del vertice con llave K
	 * @param key llave del vertice
	 * @return el nï¿½mero de ejes que salen del vertice con llave K
	 */
	
	public int sizeOfEdgesFrom (K key){
		return adj[positionOf(key)].getSize();
	}
	
	

	
	/**
	 * calcula la congestion de una parada con llave K 
	 * @param key la llve de la parad
	 * @return la congestion de la parada con llave K
	 */
	public int congestion(K key){
		int congestion = 0;
		congestion =+ sizeOfEdgesFrom(key);
		congestion =+ sizeOfEdgesTo(key);
		return congestion;
		
		
		
		
	}
	
	
	/**
	 * @return iterable con todos los ejes del grafo
	 */
	public Iterable<DirectedEdge<K>> edges(){
		Bag<DirectedEdge<K>> ans = new Bag<>();
		for( int i = 0; i < V; i++ ){
			if( adj[i] != null){
				for( DirectedEdge<K> currentEdge : adj[i] ){
					ans.addAtEnd(currentEdge);
				}
			}
		}
		return ans;
	}
	
	
	/**
	 * @return retorna el vertices mas congestionado del grafo 
	 */
	public Vertex<K, V> darVerticeMasCongestionado (){
		
		Vertex<K, V> masCongestion = vertices[0];
		int mayorCongestion = congestion(vertices[0].getKey());
		for (Vertex<K, V> currentVertex: vertices()) {
			if (congestion(currentVertex.getKey()) > mayorCongestion) {
				masCongestion = currentVertex;
				mayorCongestion = congestion(currentVertex.getKey());
			}
		}
		
	return masCongestion;
	}
	

	/**
	 * @return iterable con todos los vï¿½rtices del grafo
	 */
	public Iterable<Vertex<K, V>> vertices(){
		Queue<Vertex<K, V>> ans = new Queue<>();
		for( Vertex<K, V> currentVertex : vertices ){
			if( currentVertex != null )
				ans.enqueue(currentVertex);
		}
		return ans;
	}

	public DirectedGraph<K, V> reverse (){

		DirectedGraph<K,V> reverse = new DirectedGraph<>(V);
		for (int v = 0; v < V; v++) {
			reverse.addVertex(vertices[v].getKey(), vertices[v].getValue());
			
			}
		for (int v = 0; v < V; v++) {
		for( DirectedEdge<K> currentEdge : adj(vertices[v].getKey())){

			
			reverse.addEdge(vertices[v].getKey(), currentEdge.toV(), currentEdge.weight());
		}
		}
		return reverse;

	}

	/**
	 * Representaciï¿½n del grafo como string
	 */
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append( V + " vï¿½rtices, " + E + " ejes.\n");
		for( int i = 0; i < V; i++ ){
			s.append( vertices[i] + ": ");
			for( DirectedEdge<K> edge : adj[i] ){
				s.append(edge + "  ");
			}
			s.append("\n");
		}
		return s.toString();
	}
}
