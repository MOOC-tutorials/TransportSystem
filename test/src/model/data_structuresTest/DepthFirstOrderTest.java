package model.data_structuresTest;

import javax.swing.text.Position;

import junit.framework.TestCase;
import model.data_structures.Bag;
import model.data_structures.DepthFirstOrder;
import model.data_structures.DirectedGraph;
import model.data_structures.Queue;
import model.data_structures.Stack;
import model.data_structures.Vertex;

public class DepthFirstOrderTest extends TestCase
{

	private DirectedGraph<Integer, Integer> graph;

	private DepthFirstOrder<Integer, Integer> DFO;

	private int modulo = 8;

	private int tamanioComponentes = 80;

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


	public void testDFO(){
		setup1();

		try{
			DFO = new DepthFirstOrder<>(graph);
		}catch( Exception e){
			fail( "No debería fallar" );
		}
		try{
			DFO = new DepthFirstOrder<>(null);
			fail( "Debería fallar" );
		}catch( Exception e){

		}
	}

	public void testPre(){
		setup1();
		DFO = new DepthFirstOrder<>(graph);
		Bag<Vertex<Integer, Integer>> test = new Bag<>();
        for (Vertex<Integer, Integer> v : DFO.pre()) {
          
            test.addAtEnd(v);
            
            assertEquals( test.positionOf(v) , DFO.pre(v) );    
            
        }
        
        
        
	}
	
	public void testPost(){
		setup1();
		DFO = new DepthFirstOrder<>(graph);
		Bag<Vertex<Integer, Integer>> test = new Bag<>();
        for (Vertex<Integer, Integer> v : DFO.post()) {
                    test.addAtEnd(v);               
                    assertEquals( test.positionOf(v) , DFO.post(v));  
        }
        
          
	}
	
	public void testReversePostOrder(){
		
		setup1();
		DFO = new DepthFirstOrder<>(graph);
		Bag<Vertex<Integer, Integer>> test = new Bag<>();
        for (Vertex<Integer, Integer> v : DFO.reversePost()) {
                    test.addAtEnd(v);               
  
        }
	}

	



}
