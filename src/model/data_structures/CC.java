package model.data_structures;

import java.util.NoSuchElementException;

/**
 * Clase para encontrar los componentes conexos de un digrafo
 * Implementaciï¿½n basada en: https://algs4.cs.princeton.edu/41graph/CC.java.html
 *
 * @param <K> Tipo de llave de los vï¿½rtices
 * @param <V  Tipo de valores de los vï¿½rtices
 */
public class CC<K, V> {

	/**
	 * Arreglo que en cada posiciï¿½n tiene un boolean para indicar cuando se marca un vï¿½rtice en DFS.
	 */
	private boolean marked[];
	
	/**
	 * Arreglo con el id del componente conexo al que pertenece el vï¿½rtice correspondiente a la posiciï¿½n.
	 */
	private int id[];
	
	/**
	 * Tamaï¿½o del componente conexo al que pertenece el vï¿½rtice correspondiene a la posiciï¿½n.
	 */
	private int size[];
	
	/**
	 * Nï¿½mero de componentes conxos del grafo
	 */
	private int count;
	
	/**
	 * Copia de los vï¿½rtices del grafo
	 */
	private Vertex<K, V> vertices[];
	
	/**
	 * Identifica los componentes conexos del grafo, su cantidad y su tamaï¿½o
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
	 * @param vertex vï¿½rtice desde el cual comienza DFS.
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
	 * @param vertexKey llave del vï¿½rtice del cuï¿½l se quiere saber el id del componente conexo en el que se encuentra
	 * @return id del componente conexo al que pertenece el vï¿½rtice cuya llave fue ingresada por parï¿½metro
	 * @throws NoSuchElementException si el vï¿½rtice no estï¿½ en ningï¿½n componente del grafo. Es decir, si no estï¿½ en el grafo
	 */
	public int id( K vertexKey ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++){
			if( vertices[i].getKey().equals(vertexKey) )
				return id[i];
		}
		throw new NoSuchElementException( "El vï¿½rtice con llave " + vertexKey + " no estï¿½ en ningï¿½n componente conexo del grafo"  );
	}
	
	/**
	 * @param vertexKey  llave del vï¿½rtice del cuï¿½l se quiere saber el tamaï¿½o del componente conexo en el que se encuentra
	 * @return tamaï¿½o del componente conexo al que pertenece el vï¿½rtice cuya llave fue ingresada por parï¿½metro
	 * @throws @throws NoSuchElementException si el vï¿½rtice no estï¿½ en ningï¿½n componente del grafo. Es decir, si no estï¿½ en el grafo
	 */
	public int size( K vertexKey ) throws NoSuchElementException{
		for( int i = 0; i < vertices.length; i++){
			if( vertices[i].getKey().equals(vertexKey) )
				return size[id[i]];
		}
		throw new NoSuchElementException( "El vï¿½rtice con llave " + vertexKey + "no estï¿½ en ningï¿½n componente conexo del grafo"  );
	}
	
	/**
	 * @return cantidad de componentes conexos en el grafo.
	 */
	public int count(){
		return count;
	}
	
	/**
	 * @param vertexKey1 llave del primer vï¿½rtice
	 * @param vertexKey2 llave del segundo vï¿½rtice
	 * @return true si los dos vï¿½rtices con llaves ingresadas por parï¿½metro estï¿½n en el mismo componente conexo, false de lo contrario.
	 */
	public boolean connected( K vertexKey1, K vertexKey2 ){
		return id( vertexKey1 ) == id( vertexKey2 );
	}
	
	
	
	/**
	 * Copia los vï¿½rtices del grafo en el arreglo vï¿½rtices
	 * @param graph Grafo del cual se quieren copiar los vï¿½rtices
	 */
	private void copyVertices( DirectedGraph<K, V> graph ){
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
		K key = vertex.getKey();
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El vï¿½rtice no fue encontrado" ); 
	}
}
