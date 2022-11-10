package model.data_structuresTest;

import junit.framework.TestCase;
import model.data_structures.SCC;
import model.data_structures.Vertex;
import model.data_structures.DirectedGraph;

public class SCCTest extends TestCase
{

	
	
private DirectedGraph<Integer, Integer> graph;
	
	private SCC<Integer, Integer> SCC;
	
	private int modulo = 8;
	
	private int tamanioComponentes = 80;
	
	//Crea un grafo con un número de componentes conexos igual a módulo
	public void setup1(){
		
		graph = new DirectedGraph<>(modulo*tamanioComponentes);
		for( int i = 0; i < graph.capacity(); i++ ){
			graph.addVertex(i, i);
		}
		
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			int currentVertexModulo = currentVertex.getKey() % modulo;
			for( Vertex<Integer, Integer> currentVertex2 : graph.vertices()){
				int currentVertex2Modulo = currentVertex2.getKey() % modulo;
				if( currentVertex2Modulo == currentVertexModulo && (!((currentVertex.getKey()).equals(currentVertex2.getKey())))){
					graph.addEdge(currentVertex.getKey(), currentVertex2.getKey(), currentVertex2Modulo);
				}
			}
		}
	}
	
	public void testSCC(){
		setup1();
		
		try{
			SCC = new SCC<>(graph);
		}catch( Exception e){
			fail( "No debería fallar" );
		}
		try{
			SCC = new SCC<>(null);
			fail( "Debería fallar" );
		}catch( Exception e){
			
		}
	}
	
	public void testId(){
		setup1();
		SCC = new SCC<>(graph);
		for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
			assertEquals("El vértice no está en el componente conexo esperado", SCC.id(currentVertex.getKey()), SCC.id(currentVertex.getKey()));
		}
	}
	
	public void testCount(){
		setup1();
		SCC = new SCC<>(graph);
		assertEquals("El número de componentes conexos no es el esperado",modulo, SCC.count());
	}
	
	
}
