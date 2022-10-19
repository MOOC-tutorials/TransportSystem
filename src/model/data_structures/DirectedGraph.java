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
	 * N�mero de v�rtices del grafo
	 */
	private int V;

	/**
	 * N�mero de ejes del grafo
	 */
	private int E;

	private int capacity;

	private int freeVertexIndex;

	/**
	 * Lista de adyacencia
	 */
	private Bag<DirectedEdge<K>>[] adj;

	/**
	 * Lista de v�rtices del grafo
	 */
	private Vertex<K, V>[] vertices;

	/**
	 * Lista de n�meros de ejes de entrada por v�rtice
	 */
	private int[] indegree;

	/**
	 * Crea un nuevo grafo con pesos dirigido con tama�o inicial
	 * @param V n�mero inicial de v�rtices
	 */
	@SuppressWarnings("unchecked")
	public DirectedGraph( int V ) { 
		if( V < 0 ) throw new IllegalArgumentException( "El n�mero de v�rtices del grafo debe ser mayor a 0.");
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
	 * @return n�mero de v�rtices del grafo
	 */
	public int V(){
		return V;
	}

	/**
	 * @return N�mero de ejes del grafo
	 */
	public int E(){
		return E;
	}

	/**
	 * @return capacidad de v�rtices del grafo
	 */
	public int capacity(){
		return capacity;
	}

	/**
	 * @return true si no tiene v�rtices, false de lo contrario
	 */
	public boolean isEmpty(){
		return V == 0;
	}

	/**
	 * M�todo para saber la posici�n en el arreglo de un v�rtice co llave K
	 * @param key llave del v�rtice 
	 * @return posici�n del v�rtice en el arreglo 
	 * @throws NoSuchElementException si el v�rtice no se encuentra (no deber�a lanzarce nunca)
	 */
	private int positionOf( K key ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El v�rtice con la llave " + key + " no se encuentra en el grafo" );
	}

	/**
	 * @param key llave del v�rtice
	 * @return true si el grafo contien el v�rtice de llave K, false de lo contrario
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
	 * @param edge eje que se quiere comprobar si es� en el grafo
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
	 * @param key llave del v�rtice que se quiere obtener
	 * @return v�rtice con la llave K
	 * @throws NoSuchElementException Si el v�rtice no se encuentra en el grafo
	 */
	public Vertex<K, V> getVertex( K key ) throws NoSuchElementException{
		for( Vertex<K, V> currentVertex : vertices ){
			if( currentVertex.getKey().equals(key)){
				return currentVertex;
			}
		}
		throw new NoSuchElementException("El v�rtice con la llave " + key + " no est� en el grafo");
	}

	/**
	 * Aumenta el tama�o de los arreglos a la nueva capacidad, para que el grafo pueda tener m�s v�rtices
	 * @param newSize Nuevo tama�o de los arreglos
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
	 * Agrega un eje entre dos v�rtices
	 * @param fromV llave del v�rtice origen
	 * @param toV llave del v�rtice destino
	 * @param weight peso del v�rtice
	 */
	public void addEdge( K fromV, K toV, double weight ) throws IllegalStateException{
		DirectedEdge<K> toAdd = new DirectedEdge<K>(fromV, toV, weight);
		if( !containsEdge(toAdd) ){
			adj[positionOf(fromV)].addAtEnd(toAdd);
			indegree[positionOf(toV)]++;
			E++;
		}
		else{
			throw new IllegalStateException( "El eje " + toAdd + " ya est� en el grafo" );
		}
	}

	/**
	 * Agrega un nuevo v�rtice al grafo
	 * @param id llave del v�rtice
	 * @param info informaci�n del v�rtice
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
	 * @param fromV llave del v�rtice origen
	 * @param toV llave del v�rtice destino
	 * @param weight peso del v�rtice
	 */
	public void deleteEdge( DirectedEdge<K> edge ) throws NoSuchElementException{
		try{
			adj[positionOf(edge.fromV())].delete( edge );
		}catch (NoSuchElementException e ){
			throw new NoSuchElementException( "El v�rtice no existe" );
		}
		E--;
	}

	/**
	 * Elimina un eje
	 * @param fromV llave del v�rtice origen
	 * @param toV llave del v�rtice destino
	 * @param weight peso del v�rtice
	 */
	public void deleteEdge( K fromV, K toV, double weight ) throws NoSuchElementException{
		deleteEdge(new DirectedEdge<K>(fromV, toV, weight));
	}


	/**
	 * Elimina un v�rtice del grafo
	 * @param id llave del v�rtice
	 * @param info informaci�n del v�rtice
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
			throw new NoSuchElementException("El v�rtice no se encuentra en el grafo");
		}
		if( V - 1 < capacity / 2 ){
			resize(capacity /2);
		}
	}

	/**
	 * @param key llave del v�rtice 
	 * @return Iterable de ejes que salen del v�rtice de llave key.
	 */
	public Iterable<DirectedEdge<K>> adj( K key ){
		return adj[positionOf(key)];
	}

	/**
	 * @param key llave del v�rtice
	 * @return n�mero de ejes que salen del v�rtice
	 */
	public int outdegree( K key ){
		return adj[positionOf(key)].getSize();
	}

	/**
	 * @param key llave del v�rtice
	 * @return n�mero de ejes que llegan al v�rtice
	 */
	public int indegree( K key ){
		return indegree[positionOf(key)];
	}

	/**
	 * @param key llave del v�rtice
	 * @return iterable con los ejes que llegan al v�rtice con llave key
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
	 * retorna el n�mero de ejes que llegan al vertice con llave K
	 * @param Key llave del vertice
	 * @return el n�mero de ejes que llegan al vertice con llave K
	 */
	
	public int sizeOfEdgesTo(K Key){
		Queue<DirectedEdge<K>> ans = new Queue<>();
		for( DirectedEdge<K> currentEdge : edgesTo(Key) ){
			ans.enqueue(currentEdge);
		}
		
		return ans.getSize();
	}
	
	/**
	 * retorna el n�mero de ejes que salen del vertice con llave K
	 * @param key llave del vertice
	 * @return el n�mero de ejes que salen del vertice con llave K
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
	 * @return iterable con todos los v�rtices del grafo
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
	 * Representaci�n del grafo como string
	 */
	public String toString(){
		StringBuilder s = new StringBuilder();
		s.append( V + " v�rtices, " + E + " ejes.\n");
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
