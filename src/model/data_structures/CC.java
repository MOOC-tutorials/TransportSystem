package model.data_structures;

import java.util.NoSuchElementException;

/**
 * Clase para encontrar los componentes conexos de un digrafo
 * Implementación basada en: https://algs4.cs.princeton.edu/41graph/CC.java.html
 *
 * @param <K> Tipo de llave de los vértices
 * @param <V  Tipo de valores de los vértices
 */
public class CC<K, V> {

	/**
	 * Arreglo que en cada posición tiene un boolean para indicar cuando se marca un vértice en DFS.
	 */
	private boolean marked[];
	
	/**
	 * Arreglo con el id del componente conexo al que pertenece el vértice correspondiente a la posición.
	 */
	private int id[];
	
	/**
	 * Tamaño del componente conexo al que pertenece el vértice correspondiene a la posición.
	 */
	private int size[];
	
	/**
	 * Número de componentes conxos del grafo
	 */
	private int count;
	
	/**
	 * Copia de los vértices del grafo
	 */
	private Vertex<K, V> vertices[];
	
	/**
	 * Identifica los componentes conexos del grafo, su cantidad y su tamaño
	 * @param graph grafo sobre el que se quieren encontrar los componentes conexos
	 */
	@SuppressWarnings("unchecked")
	public CC( DirectedGraph<K, V> graph){
		int verticesNo = graph.V();
		marked = new boolean[verticesNo];
		id = new int[verticesNo];
		size = new int[verticesNo];
		vertices = (Vertex<K, V>[]) new Vertex[verticesNo];
		copyVertices(graph);
		for( int i = 0; i < verticesNo; i++ ){
			if( !marked[i] ){
				Dfs( graph, vertices[i] );
				count++;
			}
		}
	}
	
	/**
	 * Halla los componentes conexos usando DFS.
	 * @param graph grafo sobre el que se quieren hallar sus componentes conexos
	 * @param vertex vértice desde el cual comienza DFS.
	 */
	private void Dfs( DirectedGraph<K, V> graph, Vertex<K, V> vertex ){
		int positionOfVertex = positionOf(vertex);
		marked[positionOfVertex] = true;
		id[positionOfVertex] = count;
		size[count]++;
		for( DirectedEdge<K> currentEdge : graph.adj(vertex.getKey())){
			Vertex<K, V> toVertex = graph.getVertex(currentEdge.toV());
			if( !marked[positionOf(toVertex)] ){
				Dfs( graph, toVertex );
			}
		}
	}
	
	/**
	 * @param vertexKey llave del vértice del cuál se quiere saber el id del componente conexo en el que se encuentra
	 * @return id del componente conexo al que pertenece el vértice cuya llave fue ingresada por parámetro
	 * @throws NoSuchElementException si el vértice no está en ningún componente del grafo. Es decir, si no está en el grafo
	 */
	public int id( K vertexKey ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++){
			if( vertices[i].getKey().equals(vertexKey) )
				return id[i];
		}
		throw new NoSuchElementException( "El vértice con llave " + vertexKey + " no está en ningún componente conexo del grafo"  );
	}
	
	/**
	 * @param vertexKey  llave del vértice del cuál se quiere saber el tamaño del componente conexo en el que se encuentra
	 * @return tamaño del componente conexo al que pertenece el vértice cuya llave fue ingresada por parámetro
	 * @throws @throws NoSuchElementException si el vértice no está en ningún componente del grafo. Es decir, si no está en el grafo
	 */
	public int size( K vertexKey ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++){
			if( vertices[i].getKey().equals(vertexKey) )
				return size[id[i]];
		}
		throw new NoSuchElementException( "El vértice con llave " + vertexKey + "no está en ningún componente conexo del grafo"  );
	}
	
	/**
	 * @return cantidad de componentes conexos en el grafo.
	 */
	public int count(){
		return count;
	}
	
	/**
	 * @param vertexKey1 llave del primer vértice
	 * @param vertexKey2 llave del segundo vértice
	 * @return true si los dos vértices con llaves ingresadas por parámetro están en el mismo componente conexo, false de lo contrario.
	 */
	public boolean connected( K vertexKey1, K vertexKey2 ){
		return id( vertexKey1 ) == id( vertexKey2 );
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
