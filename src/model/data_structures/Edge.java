package model.data_structures;
/**
 * Clase que representa un eje con peso de un grafo no dirigido
 * Implementación basada en: https://algs4.cs.princeton.edu/43mst/Edge.java.html
 */
public class Edge<K> implements Comparable<Edge<K>> {

	/**
	 * Uno de los vértices que conecta el eje
	 */
	private final K v;
	
	/**
	 * El otro de los vértices que conecta el eje
	 */
	private final K w;
	
	/**
	 * Peso del eje
	 */
	private final double weight;
	
	/**
	 * crea un nuevo eje
	 * @param v vértice que conecta el eje
	 * @param w vértice que conecta el eje
	 * @param weight peso del eje
	 */
	public Edge( K v, K w, double weight ){
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	/**
	 * @return peso del eje
	 */
	public double weight(){
		return weight;
	}
	
	/**
	 * @return uno de los vértices que conecta el eje
	 */
	public K either(){
		return v;
	}
	
	/**
	 * @param vertex vértice opuesto al que se quiere obtener
	 * @return vértice opueseto a vertex
	 * @throws IllegalArgumentException si el vértice no es ninguno de los que conecta el eje
	 */
	public K other( K vertex ) throws IllegalArgumentException{
		if( vertex == w ) 
			return v;
		else if( vertex == v ) 
			return w;
		else 
			throw new IllegalArgumentException( "Vértice no válido" );
	}
	
	/**
	 * @param edge Eje con el que se quiere comparar
	 * @return 1 si el peso del eje es mayor al de edge, -1 si es menor y 0 si son iguales
	 */
	@Override
	public int compareTo(Edge<K> edge) {
		return Double.compare(weight, edge.weight);
	}
	
	/**
	 * @return representación en String del eje
	 */
	public String toString(){
		return v + "-" + w + ", " + weight;
	}
}
