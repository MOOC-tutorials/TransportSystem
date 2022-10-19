package model.data_structuresTest;

import junit.framework.TestCase;
import model.data_structures.CC;
import model.data_structures.Cycles;
import model.data_structures.DirectedGraph;
import model.data_structures.Vertex;

public class CyclesTest extends TestCase {

private DirectedGraph<Integer, Integer> graph;
	
	private Cycles<Integer, Integer> cycles;
	
	private int modulo = 3;
	
	private int tamanioComponentes = 10;
	
	//Crea un grafo con un número de componentes conexos igual a módulo
	public void setup1(){
		
		graph = new DirectedGraph<>(modulo*tamanioComponentes);
		graph.addVertex(0, 0);
		graph.addVertex(1, 1);
		graph.addVertex(2, 2);
		graph.addVertex(3, 3);
		graph.addVertex(4, 4);

		graph.addEdge(0, 1, 1.0);
		graph.addEdge(1, 2, 1.0);
		graph.addEdge(2, 3, 1.0);
		graph.addEdge(3, 4, 1.0);
		graph.addEdge(4, 0, 1.0);

		graph.addEdge(0, 1, 2.0);
		graph.addEdge(1, 2, 2.0);
		graph.addEdge(2, 0, 2.0);
	

		cycles = new Cycles<>(graph);
	}
	
	public void testHasCycle(){
		setup1();
		
		assertTrue(cycles.hasCycle());		
	}
	
	public void testLongestCycle(){
		setup1();
		int count = 0;
		for( Vertex<Integer, Integer> currentVertex : cycles.longestCycles().delMax()){
			int key = currentVertex.getKey();
			assertTrue( key == 0 || key == 1 || key == 2 || key == 3 || key == 4   );
			count++;
		}
		
		assertEquals(6, count);
	}
}
