package model.data_structures;


/**
 * Clase para los ejes del grafo
 * Implementación tomada de: https://algs4.cs.princeton.edu/43mst/Edge.java.html
 */
public class DirectedEdge<K> implements Comparable <DirectedEdge<K>>{

	/**
	 * Vértice de un extremo del eje.
	 */
	private final K fromV;

	/**
	 * Vértice del otro extremo del eje.
	 */
	private final K toV;

	/**
	 * Peso del eje.
	 */
	private final double weight;

	/**
	 * Crea un nuevo eje entre dos vértices con un peso dado
	 * @param pFromV primer vértice
	 * @param pTov segundo vértice
	 * @param weight peso del eje
	 */
	public DirectedEdge( K pFromV, K pTov, double weight){
		if( Double.isNaN(weight) ) throw new IllegalArgumentException( "El peso no es un número" );
		this.fromV = pFromV;
		this.toV = pTov;
		this.weight = weight;
	}

	/**
	 * @return peso del eje
	 */
	public double weight(){
		return weight;
	}

	/**
	 * @return el vértice de origen
	 */
	public K fromV(){
		return fromV;
	}

	/**
	 * 
	 * @return el vértice destino
	 */
	public K toV(){
		return toV;
	}

	/**
	 * @param vertex Vértice diferente al que se quiere obtener
	 * @return Vértice diferente al ingresado por parámetro
	 */
	public K other( K vertex ){
		if( vertex == fromV )
			return toV;
		if ( vertex == toV )
			return fromV;
		else throw new IllegalArgumentException( "Extremo del eje no válido" );
	}

	/**
	 * Compara el peso de dos ejes.
	 * @return un entero negativo, cero o un entero positivo si el peso del eje es 
	 * menor, igual o mayor que el del eje ingresado por parámetro.
	 */
	@Override
	public int compareTo(DirectedEdge<K> pEdge) {
		if(fromV.equals(pEdge.fromV) && toV.equals(pEdge.toV) && Double.compare(weight, pEdge.weight) == 0){
			return 0;
		}
		return 1;
	}

	/**
	 * String que representa el eje.
	 */
	public String toString(){
		return fromV + "->" + toV + " " +  weight ;
	}

}
