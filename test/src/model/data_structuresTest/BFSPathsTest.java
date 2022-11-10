package model.data_structuresTest;

import java.util.NoSuchElementException;

import junit.framework.TestCase;
import model.data_structures.DirectedGraph;
import model.data_structures.BFSPaths;
import model.data_structures.DirectedEdge;
import model.data_structures.Vertex;

public class BFSPathsTest extends TestCase {
	private DirectedGraph<Integer, Integer> graph;

	private BFSPaths<Integer, Integer> BFSPaths;

	public void setup1(){
		graph = new DirectedGraph<>(100);
		for( int i = 0; i < graph.capacity(); i++ ){
			graph.addVertex(i, i);
		}
		for( int i = 0; i < graph.capacity() - 1; i++ ){
			graph.addEdge(i, i+1, 10.0);
		}
	}

	public void setup2(){
		graph = new DirectedGraph<>(100);
		for( int i = 0; i < graph.capacity(); i++){
			graph.addVertex(i, i);
		}
		for( int i = 2; i < graph.capacity() - 1; i = i + 2 ){
			if( graph.containsVertex(i+2)){
				graph.addEdge(i, i+2, 10.0);
			}
		}
		graph.addEdge(0, 2, 0.5);
		graph.addEdge(98, 0, 98.5);
		for( int i = 1; i < graph.capacity() - 1; i = i + 2 ){
			if( graph.containsVertex(i+2)){
				graph.addEdge(i, i+2, 10.0);
			}
		}	
		graph.addEdge(99, 1, 99.5);
	}

	public void testPathsOneSource(){
		setup1();
		try{
			BFSPaths = new BFSPaths<>(graph, graph.getVertex(0));
		}catch( Exception e){
			fail( "No debería lanzar excepción" );
		}
	}

	public void testPathsMultipleSources(){

		setup1();
		try{
			BFSPaths = new BFSPaths<>(graph, graph.vertices());
		}catch( Exception e){
			fail( "No debería lanzar excepción" );
		}
	}


	public void testHasPathTo(){
		setup1();
		//Probando que los vértices no tengan caminos "hacia atrás"
		BFSPaths = new BFSPaths<>(graph, graph.vertices());
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			BFSPaths = new BFSPaths<>(graph, currentVertex);
			int currentKey = currentVertex.getKey();
			for( int i = 1; i < graph.V(); i++){
				int previousKey = currentKey - i;
				if( graph.containsVertex(previousKey) ){
					Vertex<Integer, Integer> currentVertex2 = graph.getVertex(currentKey - i);
					assertFalse( "El vértice " + currentVertex + " no debería tener un camino hasta " + currentVertex2, BFSPaths.hasPathTo(currentVertex2));
				}
			}
		}

		//Probando que haya un camino para llegar a todos los vértices.
		graph.addEdge(99, 0, 99.0);
		BFSPaths = new BFSPaths<>(graph, graph.vertices());
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertTrue("Todos los nodos del grafo deberían tener un camino para llegar a los demás", BFSPaths.hasPathTo(currentVertex));
		}

		//Probando que los vértices pares estén conectando sólo a los vértices pares y los impares estén conectados sólo a los impares.
		setup2();
		for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
			BFSPaths = new BFSPaths<>(graph, currentVertex);
			if( currentVertex.getKey() % 2 == 0 ){
				for( Vertex<Integer, Integer> currentVertex2 : graph.vertices()){
					if( currentVertex2.getKey() % 2 == 0 ){
						assertTrue( "El vértice " + currentVertex + " debería tener un camino hasta " + currentVertex2, BFSPaths.hasPathTo(currentVertex2));
					}
					else{
						assertFalse( "El vértice " + currentVertex + " no debería tener un camino hasta " + currentVertex2, BFSPaths.hasPathTo(currentVertex2));
					}
				}
			}
			else{
				for( Vertex<Integer, Integer> currentVertex2 : graph.vertices()){
					if( currentVertex2.getKey() % 2 == 0 ){
						assertFalse( "El vértice " + currentVertex + " debería tener un camino hasta " + currentVertex2, BFSPaths.hasPathTo(currentVertex2));
					}
					else{
						assertTrue( "El vértice " + currentVertex + " no debería tener un camino hasta " + currentVertex2, BFSPaths.hasPathTo(currentVertex2));
					}
				}
			}
		}
	}

	public void testDistTo(){
		setup1();
		for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
			BFSPaths = new BFSPaths<>(graph, currentVertex);
			for( Vertex<Integer, Integer> currentVertex2 : graph.vertices() ){
				try{
					double expectedDist = (currentVertex2.getKey() - currentVertex.getKey()) * 10.0;
					assertEquals("La distancia desde " + currentVertex + " a " + currentVertex2 + " no es la esperada", expectedDist, BFSPaths.distTo(currentVertex2));
				}catch( NoSuchElementException e){
					
				}
			}
		}
	}

	public void testPathTo(){
		setup1();

		BFSPaths = new BFSPaths<>(graph, graph.getVertex(0));
		for( Vertex<Integer, Integer> currentVertex2 : graph.vertices() ){
			if( !BFSPaths.hasPathTo(currentVertex2) ){
				assertNull("No debería haber un camino desde" + graph.getVertex(0) + " a " + currentVertex2, BFSPaths.pathTo(currentVertex2));
			}
			else{
				int i = 1;
				for( DirectedEdge<Integer> currentEdgeInPath : BFSPaths.pathTo(currentVertex2)){
					assertEquals("El vértice de origen no es el esperado", new Integer(i-1), currentEdgeInPath.fromV());
					assertEquals("El vértice de destino no es el esperado", new Integer(i), currentEdgeInPath.toV());
					assertEquals("El peso no es el esperado", 10.0, currentEdgeInPath.weight());

					i++;
				}
			}
		}
	}
}
