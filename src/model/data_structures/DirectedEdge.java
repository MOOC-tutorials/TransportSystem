package model.data_structures;


/**
 * Clase para los ejes del grafo
 * Implementaciï¿½n tomada de: https://algs4.cs.princeton.edu/43mst/Edge.java.html
 */
public class DirectedEdge<K> implements Comparable <DirectedEdge<K>>{

	/**
	 * Vï¿½rtice de un extremo del eje.
	 */
	private final K fromV;

	/**
	 * Vï¿½rtice del otro extremo del eje.
	 */
	private final K toV;

	/**
	 * Peso del eje.
	 */
	private final double weight;

	/**
	 * Crea un nuevo eje entre dos vï¿½rtices con un peso dado
	 * @param pFromV primer vï¿½rtice
	 * @param pTov segundo vï¿½rtice
	 * @param weight peso del eje
	 */
	public DirectedEdge( K pFromV, K pTov, double weight){
		if( Double.isNaN(weight) ) throw new IllegalArgumentException( "El peso no es un nï¿½mero" );
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
	 * @return el vï¿½rtice de origen
	 */
	public K fromV(){
		return fromV;
	}

	/**
	 * 
	 * @return el vï¿½rtice destino
	 */
	public K toV(){
		return toV;
	}

	/**
	 * @param vertex Vï¿½rtice diferente al que se quiere obtener
	 * @return Vï¿½rtice diferente al ingresado por parï¿½metro
	 */
	public K other( K vertex ){
		if( vertex == fromV )
			return toV;
		if ( vertex == toV )
			return fromV;
		else throw new IllegalArgumentException( "Extremo del eje no vï¿½lido" );
	}

	/**
	 * Compara el peso de dos ejes.
	 * @return un entero negativo, cero o un entero positivo si el peso del eje es 
	 * menor, igual o mayor que el del eje ingresado por parï¿½metro.
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
