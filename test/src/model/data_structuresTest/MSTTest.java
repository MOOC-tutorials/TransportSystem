package model.data_structuresTest;

import junit.framework.TestCase;
import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.MST;

public class MSTTest extends TestCase {
	private EdgeWeightedGraph<Integer, Integer> graph;

	private MST<Integer, Integer> mst;
	public void setup1(){
		graph = new EdgeWeightedGraph<>(100);
		for( int i = 0; i < graph.capacity(); i++ ){
			graph.addVertex(i, i);
		}
		for( int i = 0; i < graph.capacity() - 1; i++ ){
			graph.addEdge(i, i+1, i+0.5);
			graph.addEdge(i, i+1, 2*(i+0.5));
		}
	}
	
	public void testMST(){
		setup1();
		mst = new MST<>(graph);
	}
	
	public void testEdges(){
		testMST();
		Integer i = 0;
		for( Edge<Integer> edge : mst.edges()){
			assertEquals(i, edge.either());
			assertEquals(edge.weight(), i+0.5);
			assertEquals(new Integer(i+1), edge.other(edge.either()));
			i++;
		}
	}
	
	public void testWeight(){
		testMST();
		double expectedWeight = 0;
		for( int i = 0; i < graph.capacity() - 1; i++ ){
			expectedWeight += i+0.5;
		}
		assertEquals(expectedWeight, mst.weight());
	}
}
