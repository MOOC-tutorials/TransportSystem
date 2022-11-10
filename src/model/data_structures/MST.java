package model.data_structures;

import java.util.NoSuchElementException;

/**
 * Clase que usa el algoritmo de Prim para encontrar el MST de un grafo no dirigido.
 * Implementaciï¿½n tomada de: https://algs4.cs.princeton.edu/43mst/PrimMST.java.html
 */
public class MST<K, V> {
	private static double FLOATING_POINT_EPSILON = 1E-12;

	private Edge<K>[] edgeTo;
	private double[] distTo;
	private boolean[] marked;
	private IndexMinPQ<Double> pq;
	private Vertex<K, V>[] vertices;

	public MST(EdgeWeightedGraph<K, V> graph){
		edgeTo = (Edge<K>[]) new Edge[graph.V()];
		distTo = new double[graph.V()];
		marked = new boolean[graph.V()];
		vertices = (Vertex<K, V>[]) new Vertex[graph.V()];
		pq = new IndexMinPQ<>(graph.V());
		copyVertices(graph);
		for( int v = 0; v < graph.V(); v++){
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		for( Vertex<K, V> currentVertex : vertices){
			if( !marked[positionOf(currentVertex)])
				prim(graph, currentVertex);
		}
		assert check(graph);
	}

	private void prim( EdgeWeightedGraph<K, V> graph, Vertex<K, V> vertex){
		distTo[positionOf(vertex)] = 0.0;
		pq.insert(positionOf(vertex), distTo[positionOf(vertex)]);
		while( !pq.isEmpty() ){
			int v = pq.delMin();
			scan(graph, v);
		}
	}

	private void scan(EdgeWeightedGraph<K, V> graph, int v){
		marked[v] = true;
		Vertex<K, V> vertex = vertices[v];
		for( Edge<K> edge : graph.adj(vertex.getKey())){
			K w = edge.other(vertex.getKey());
			int positionOfW = positionOf(w);
			if( marked[positionOfW])
				continue;
			if( edge.weight() < distTo[positionOfW] ){
				distTo[positionOfW] = edge.weight();
				edgeTo[positionOfW] = edge;
				if( pq.contains(positionOfW))
					pq.decreaseKey(positionOfW, distTo[positionOfW]);
				else
					pq.insert(positionOfW, distTo[positionOfW]);
			}
		}
	}

	public Iterable<Edge<K>> edges(){
		Queue<Edge<K>> mst = new Queue<>();
		for( int v = 0; v < edgeTo.length; v++){
			Edge<K> edge = edgeTo[v];
			if( edge != null)
			{
				mst.enqueue(edge);
			}
		}
		return mst;
	}
	
	public double weight(){
		double weight = 0.0;
		for( Edge<K> edge : edges()){
			weight += edge.weight();
		}
		return weight;
	}

	private boolean check(EdgeWeightedGraph<K, V> graph){
		
		double totalWeight = 0.0;
		for( Edge<K> edge : edges()){
			totalWeight += edge.weight();
		}
		if( Math.abs(totalWeight - weight()) > FLOATING_POINT_EPSILON){
			System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, weight());
            return false;
		}
		return true;
	}

	/**
	 * Copia los vï¿½rtices del grafo en el arreglo vï¿½rtices
	 * @param graph Grafo del cual se quieren copiar los vï¿½rtices
	 */
	private void copyVertices( EdgeWeightedGraph<K, V> graph ){
		int index = 0;
		for( Vertex<K, V> currentVertex : graph.vertices()){
			vertices[index] = currentVertex;
			index++;
		}
	}

	/**
	 * Indica la posiciï¿½n de la informaciï¿½n del vï¿½rtice en marked, edgeTo, distTo de acuerdo a vertices
	 * @param vertex vï¿½rtice del cual se quiere saber su posiciï¿½n
	 * @return posiciï¿½n de la informaciï¿½n del vï¿½rtice en todos los arreglos
	 * @throws NoSuchElementException si no encuentra el vï¿½rtice, nunca deberï¿½a lanzar excepciï¿½n
	 */
	private int positionOf( Vertex<K, V> vertex ) throws NoSuchElementException{
		return positionOf(vertex.getKey());
	}

	private int positionOf( K key){
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El vï¿½rtice no fue encontrado" ); 
	}
}
