package model.data_structures;

import java.util.NoSuchElementException;

public class DepthFirstOrder<K,V> {
	private boolean[] marked;          // marked[v] = has v been marked in dfs?
	private int[] pre;                 // pre[v]    = preorder  number of v
	private int[] post;                // post[v]   = postorder number of v
	private Queue<Integer> preorder;   // vertices in preorder
	private Queue<Integer> postorder;  // vertices in postorder
	private int preCounter;            // counter or preorder numbering
	private int postCounter; 


	// counter for postorder numbering

	/**
	 * Copia de los vértices del grafo
	 */
	private Vertex<K, V> vertices[];




	/**
	 * Determines a depth-first order for the edge-weighted digraph {@code G}.
	 * @param G the edge-weighted digraph
	 */
	@SuppressWarnings("unchecked")
	public DepthFirstOrder(DirectedGraph<K, V> G) {
		int verticesNo = G.V();
		pre    = new int[verticesNo];
		post   = new int[verticesNo];
		postorder = new Queue<Integer>();
		preorder  = new Queue<Integer>();
		marked    = new boolean[verticesNo];
		vertices = (Vertex<K, V>[]) new Vertex[verticesNo];
		copyVertices(G);
		for (int v = 0; v < verticesNo; v++)
			if (!marked[v]) dfs(G, vertices[v]);
	}


	// run DFS in edge-weighted digraph G from vertex v and compute preorder/postorder
	private void dfs(DirectedGraph<K, V> G, Vertex<K, V> v) {
		marked[positionOf(v)] = true;	
		pre[positionOf(v)] = preCounter++;
		preorder.enqueue(positionOf(v));
		for( DirectedEdge<K> currentEdge : G.adj(v.getKey())){
			Vertex<K, V> toVertex = G.getVertex(currentEdge.toV());
			if (!marked[positionOf(toVertex)]) {
				dfs(G, toVertex);
			}
		}
		postorder.enqueue(positionOf(v));
		post[positionOf(v)] = postCounter++;
	}

	/**
	 * Returns the preorder number of vertex {@code v}.
	 * @param  v the vertex
	 * @return the preorder number of vertex {@code v}
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public int pre(Vertex<K, V> v) {
		validateVertex(v);
		return pre[positionOf(v)];
	}

	/**
	 * Returns the postorder number of vertex {@code v}.
	 * @param  v the vertex
	 * @return the postorder number of vertex {@code v}
	 * @throws IllegalArgumentException unless {@code 0 <= v < V}
	 */
	public int post(Vertex<K, V> v) {
		validateVertex(v);
		return post[positionOf(v)];
	}

	/**
	 * Returns the vertices in postorder.
	 * @return the vertices in postorder, as an iterable of vertices
	 */
	public Iterable<Vertex<K, V>> post() {
		Queue<Vertex<K, V>> ans = new Queue<>();
		for (Integer pos : postorder){
			ans.enqueue(vertices[pos]);
		}
		return ans;
	}

	/**
	 * Returns the vertices in preorder.
	 * @return the vertices in preorder, as an iterable of vertices
	 */
	public Iterable<Vertex<K, V>> pre() {
		Queue<Vertex<K, V>> ans = new Queue<>();
		for (Integer pos : preorder){
			ans.enqueue(vertices[pos]);
		}
		return ans;
	}

	/**
	 * Returns the vertices in reverse postorder.
	 * @return the vertices in reverse postorder, as an iterable of vertices
	 */
	public Iterable<Vertex<K, V>> reversePost() {
		Stack<Vertex<K, V>> reverse = new Stack<>();
		for (Integer v : postorder)
			reverse.push(vertices[v]);
		return reverse;
	}

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
	public int positionOf( Vertex<K, V> vertex ) throws NoSuchElementException{
		K key = vertex.getKey();
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El vértice no fue encontrado" ); 
	}
}
