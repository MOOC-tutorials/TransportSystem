package model.data_structures;

import java.util.NoSuchElementException;

/**
 * Clase para encontrar la distancia y los caminos entre vértices
 * Implementación basada en: https://algs4.cs.princeton.edu/42digraph/DepthFirstDirectedPaths.java.html
 * @param <K> tipo de llave de los vértices	
 * @param <V> tipo de valor de los vértices
 */
public class BFSPaths<K, V> {
	
	/**
	 * Constante para indicar que la distancia entre dos nodos es infinita (no tienen conexión)
	 */
	private static final int INFINITY = Integer.MAX_VALUE;
	
	/**
	 * Arreglo de booleans que indican si el vértice ya fue visitado en BFS
	 */
	private boolean marked[];
	
	/**
	 * Arreglo de vertices que se encuentran en el camino de la BFS
	 */
//	private Vertex<K, V>[] edgeTo;
	private DirectedEdge<K>[] edgeTo;
	
	/**
	 * Arreglo de enteros con las distancias de un vértice a los demás
	 */
	private double[] distTo;
	
	/**
	 * Arreglo con una copia de los vértices del grafo
	 */
	private Vertex<K, V>[] vertices;

	/**
	 * Hace BFS desde un vértice que entra por parámetro.
	 * @param graph Grafo sobre el que se va a hacer BFS
	 * @param source Origen de BFS
	 */
	@SuppressWarnings("unchecked")
	public BFSPaths( DirectedGraph<K, V> graph, Vertex<K, V> source ){
		int NumberOfVertices = graph.V();
		marked = new boolean[NumberOfVertices];
		distTo = new double[NumberOfVertices];
		edgeTo = (DirectedEdge<K>[]) new DirectedEdge[NumberOfVertices];

		vertices = (Vertex<K, V>[]) new Vertex[NumberOfVertices];
		copyVertices(graph);
		for( int i = 0; i < NumberOfVertices; i++){
			distTo[i] = INFINITY;
		}
		BFS(graph, source);
	}
	
	/**
	 * Hace BFS desde múltiples fuentes, útil para saber a qué elementos del iterable se puede llegar desde los demás
	 * @param graph Grafo sobre el que se va a hacer BFS
	 * @param sources Iterable de vértices
	 */
	@SuppressWarnings("unchecked")
	public BFSPaths( DirectedGraph<K, V> graph, Iterable<Vertex<K, V>> sources ){
		int NumberOfVertices = graph.V();
		marked = new boolean[NumberOfVertices];
		distTo = new double[NumberOfVertices];
		edgeTo = (DirectedEdge<K>[]) new DirectedEdge[NumberOfVertices];
		vertices = (Vertex<K, V>[]) new Vertex[NumberOfVertices];
		copyVertices(graph);
		for( int i = 0; i < NumberOfVertices; i++){
			distTo[i] = INFINITY;
		}
		BFS(graph, sources);
	}
	
	/**
	 * Algoritmo BFS para un origen
	 * @param graph Grafo sobre el que se va a hacer BFS. 
	 * @param source Origen de BFS.
	 */
	private void BFS( DirectedGraph<K, V> graph, Vertex<K, V> source ){
		Queue<Vertex<K, V>> queue = new Queue<>();
		int posSource = positionOf(source.getKey());
		marked[posSource] = true; 
		distTo[posSource] = 0;
		queue.enqueue(source);
		while(!queue.isEmpty()){
			Vertex<K, V> dequeuedVertex = queue.dequeue();
			int posDequeuedVertex = positionOf(dequeuedVertex.getKey());
			for( DirectedEdge<K> currentEdge : graph.adj(dequeuedVertex.getKey())){
				Vertex<K, V> toVertex = graph.getVertex(currentEdge.toV());
				int posToVertex = positionOf(toVertex.getKey());
				if( !marked[posToVertex] ){
					edgeTo[ posToVertex ] = currentEdge;
					distTo[ posToVertex ] = distTo[posDequeuedVertex] + currentEdge.weight();
					marked[ posToVertex ] = true;
					queue.enqueue(toVertex);
				}
			}
		}
	}
	
	/**
	 * Algoritmo BFS para varios orígenes
	 * @param graph Grafo sobre el que se va a hacer BFS
	 * @param sources Iterable de vértices
	 */
	private void BFS( DirectedGraph<K, V> graph, Iterable<Vertex<K, V>> sources){
		Queue<Vertex<K, V>> queue = new Queue<>();
		for( Vertex<K, V> currentSource : sources ){
			int posCurrentSource = positionOf(currentSource.getKey());
			marked[ posCurrentSource ] = true;
			distTo[ posCurrentSource ] = 0;
			queue.enqueue(currentSource);
		}
		while(!queue.isEmpty()){
			Vertex<K, V> dequeuedVertex = queue.dequeue();
			int posDequeuedVertex = positionOf(dequeuedVertex.getKey());
			for( DirectedEdge<K> currentEdge : graph.adj(dequeuedVertex.getKey())){
				Vertex<K, V> toVertex = graph.getVertex(currentEdge.toV());
				int posToVertex = positionOf(toVertex.getKey());
				if( !marked[posToVertex] ){
					edgeTo[ posToVertex ] = currentEdge;
					distTo[ posToVertex ] = distTo[posDequeuedVertex] + 1;
					marked[ posToVertex ] = true;
					queue.enqueue(toVertex);
				}
			}
		}
	}
	
	/**
	 * Si el BFS se hace desde un sólo origen, indica si se puede llegar desde el vértice origen al vértice que se indica por parámetro.
	 * Si el BFS se hace desde múltiples orígenes, indica si es posible llegar desde por lo menos un vértice al vértice que se indica por parámetro.
	 * @param vertex vértice al que se quiere llegar desde el origen/orígenes
	 * @return true si se puede llegar al vértice desde el origen / si se puede llegar al vértice desde cualquier vértice origen, false de lo contrario
	 */
	public boolean hasPathTo( Vertex<K, V> vertex ){
		return marked[positionOf(vertex.getKey())];
	}
	
	/**
	 * Sólo es válido si el BFS se hace desde un sólo origen.
	 * @param vertex vértice del cual se quiere conocer su distancia desde el origen
	 * @return Distancia desde el origen
	 * @throws NoSuchElementException si no es posible llegar desde el origen al vértice
	 */
	public double distTo( Vertex<K, V> vertex ) throws NoSuchElementException{
		double distToVertex = distTo[positionOf(vertex.getKey())]; 
		if( distToVertex < INFINITY )
			return distToVertex;
		else
			throw new NoSuchElementException( "El vértice no tiene un camino a " + vertex );
	}
	
	/**
	 * @param vertex vértice del cual se quiere conocer el camino para llegar desde el origen al vértice
	 * @return Iterable con los ejes en el camino para llegar desde el origen hasta el vértice
	 */
	public Iterable<DirectedEdge<K>> pathTo( Vertex<K, V> vertex ){
		if( !hasPathTo(vertex) )
			return null;
		Stack<DirectedEdge<K>> path = new Stack<>();

		for( Vertex<K, V> currentVertex = vertex; distTo[positionOf(currentVertex.getKey())] != 0; currentVertex = vertices[positionOf(edgeTo[positionOf(currentVertex.getKey())].fromV())] ){
			path.push(edgeTo[positionOf(currentVertex.getKey())]);
		}
		return path;
	}
	
	public DirectedEdge<K> edgeTo( Vertex<K, V> vertex ){
		return edgeTo[positionOf(vertex.getKey())];
	}
	
	/**
	 * Indica la posición de la información del vértice en marked, edgeTo, distTo de acuerdo a vertices
	 * @param vertex vértice del cual se quiere saber su posición
	 * @return posición de la información del vértice en todos los arreglos
	 * @throws NoSuchElementException si no encuentra el vértice, nunca debería lanzar excepción
	 */
	private int positionOf( K key ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El vértice no fue encontrado" ); 
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
}
