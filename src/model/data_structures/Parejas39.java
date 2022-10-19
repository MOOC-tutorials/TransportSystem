package model.data_structures;

import java.util.NoSuchElementException;

public class Parejas39<K, V>{
	
	private Bag<Bag<Vertex<K, V>>> parejas;
	
	private boolean marked[];

	private Vertex<K, V> vertices[];
	
	public Parejas39( DirectedGraph<K, V> G ){
		int v = G.V();
		marked = new boolean[v];
		vertices = (Vertex<K, V>[]) new Vertex[v];
		parejas = new Bag<>();
		copyVertices(G);
		for( int i = 0; i < v; i++ ){
			if( !marked[i] ){
				for( DirectedEdge<K> edge : G.adj(vertices[i].getKey()) ){
					parejas(G,vertices[i], G.getVertex(edge.toV()));
				}
			}
		}
		
	}
	
	public void parejas(DirectedGraph<K,V> G, Vertex<K, V> v1, Vertex<K, V> v2){
		for( DirectedEdge<K> edge : G.adj(v2.getKey()) ){
			if( edge.toV().equals(v1.getKey()) ){
				Bag<Vertex<K, V>> newBag = new Bag<>();
				newBag.addAtEnd(v1);
				newBag.addAtEnd(v2);
				parejas.addAtEnd(newBag);
				break;
			}
		}
	}
	
	public Bag<Bag<Vertex<K, V>>> parejas(){
		return parejas;
	}
	
	public int contador(){
		return parejas.getSize();
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
