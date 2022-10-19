package model.data_structures;

import java.util.NoSuchElementException;


public class SCC<K,V>
{

	private boolean[] marked;     // marked[v] = has vertex v been visited?
	private int[] id;             // id[v] = id of strong component containing v
	private int count;            // number of strongly-connected components
	/**
	 * Copia de los vértices del grafo
	 */
	private Vertex<K, V> vertices[];

	/**
	 * Computes the strong components of the digraph {@code G}.
	 * @param G the digraph
	 */
	@SuppressWarnings("unchecked")
	public SCC(DirectedGraph<K, V> G) {
		DirectedGraph<K, V> reverse = G.reverse();
		DepthFirstOrder<K, V> dfs = new DepthFirstOrder<>(reverse);
		int verticesNo= G.V();
		marked = new boolean[verticesNo];
		id = new int[verticesNo];
		vertices = (Vertex<K, V>[]) new Vertex[verticesNo];
		copyVertices(G);
		
		for (Vertex<K, V> v : dfs.reversePost()) {
			if (!marked[positionOf(v)]) {
				dfs(G, v);
				count++;
			}
		}

		// check that id[] gives strong components
		//  assert check(G);
	}

	// DFS on graph G
	private void dfs(DirectedGraph<K, V> G, Vertex<K, V> v) { 
		marked[positionOf(v)] = true;
		id[positionOf(v)] = count;
		for( DirectedEdge<K> currentEdge : G.adj(v.getKey())){
			Vertex<K, V> toVertex = G.getVertex(currentEdge.toV());
			if (!marked[positionOf(toVertex)]) dfs(G, toVertex);
		}
	}

	/**
	 * Returns the number of strong components.
	 * @return the number of strong components
	 */
	public int count() {
		return count;
	}

	/**
	 * Are vertices {@code v} and {@code w} in the same strong component?
	 * @param  v one vertex
	 * @param  w the other vertex
	 * @return {@code true} if vertices {@code v} and {@code w} are in the same
	 *         strong component, and {@code false} otherwise
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 * @throws IllegalArgumentException unless {@code 0 <= w < V}
	 */
	public boolean stronglyConnected(Vertex<K, V> v, Vertex<K, V> w) {
		validateVertex(v);
		validateVertex(w);
		return id[positionOf(v)] == id[positionOf(w)];
	}

	/**
	 * Returns the component id of the strong component containing vertex {@code v}.
	 * @param  v the vertex
	 * @return the component id of the strong component containing vertex {@code v}
	 * @throws IllegalArgumentException unless {@code 0 <= s < V}
	 */
	public int id( K vertexKey ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++){
			if( vertices[i].getKey().equals(vertexKey) )
				return id[i];
		}
		throw new NoSuchElementException( "El vértice con llave " + vertexKey + " no está en ningún componente conexo del grafo"  );
	}



	// throw an IllegalArgumentException unless {@code 0 <= v < V}
	private void validateVertex(Vertex<K, V> v) {
		int V = marked.length;
		if (positionOf(v) < 0 || positionOf(v) >= V)
			throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	}




	/**
	 * Copia los vértices del grafo en el arreglo vértices
	 * @param graph Grafo del cual se quieren copiar los vértices
	 */
	private void copyVertices( DirectedGraph<K, V> graph ){
		int index = 0;
		for( Vertex<K, V> currentVertex : graph.vertices()){
			vertices[index] = currentVertex;
			index++;
		}
	}

	/**
	 * Indica la posición de la información del vértice en marked, edgeTo, distTo de acuerdo a vertices
	 * @param vertex vértice del cual se quiere saber su posición
	 * @return posición de la información del vértice en todos los arreglos
	 * @throws NoSuchElementException si no encuentra el vértice, nunca debería lanzar excepción
	 */
	private int positionOf( Vertex<K, V> vertex ) throws NoSuchElementException{
		K key = vertex.getKey();
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El vértice no fue encontrado" ); 
	}
}
