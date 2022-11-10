package model.data_structuresTest;

import java.util.NoSuchElementException;

import junit.framework.TestCase;
import model.data_structures.Bag;
import model.data_structures.DirectedEdge;
import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.Vertex;

public class EdgeWeightedGraphTest extends TestCase{
	private EdgeWeightedGraph<Integer, Integer> graph;

	public void setup1(){
		graph = new EdgeWeightedGraph<>(100);
	}

	public void setup2(){
		setup1();
		for( int i = 0; i < graph.capacity(); i++ ){
			graph.addVertex(i, i);
		}
	}

	public void setup3(){
		setup2();
		for( int i = 0; i < graph.capacity() - 1; i++ ){
			graph.addEdge(i, i+1, i+0.5);
		}
	}

	public void testEdgeWeightedGraph(){
		try{
			setup1();
		}catch( IllegalArgumentException e){
			fail( "No debería fallar" );
		}
	}

	public void testEdgeWeightedGraphError(){
		try{
			graph = new EdgeWeightedGraph<>(-1);
			fail( "Debería fallar" );
		}catch (IllegalArgumentException e) {

		}
	}

	public void testV(){
		setup2();
		assertEquals("La cantidad de vértices no es la esperada", graph.capacity(), graph.V());
	}

	public void testE(){
		setup3();
		int expectedEdges = 0;
		for( Edge<Integer> currentEdge : graph.edges()){
			expectedEdges++;
		}
		assertEquals("El número de ejes no es el esperado", expectedEdges, graph.E() );
	}

	public void testAddVertex(){
		setup1();
		//Prueba que se agreguen los vértices
		for( int i = 0; i < graph.capacity(); i++ ){
			graph.addVertex(i, i);
		}
		Integer currentKey = 0;
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertEquals("La llave del vértice no es la esperada", currentKey, currentVertex.getKey());
			assertEquals("El valor del vértice no es el esperado", currentKey, currentVertex.getValue());
			currentKey++;
		}

		setup1();
		for( int i = 0; i < graph.capacity(); i++ ){
			graph.addVertex(i, i*100);
		}

		//Prueba que se sobreescriban los valores
		Integer currentKey2 = 0;
		currentKey = 0;
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertEquals("La llave del vértice no es la esperada", currentKey, currentVertex.getKey());
			assertEquals("El valor del vértice no es el esperado", currentKey2, currentVertex.getValue());
			currentKey2 += 100;
			currentKey++;
		}

		setup1();
		//Prueba el rezise
		for( int i = 0; i < 1000; i++ ){
			graph.addVertex(i, i);
		}
		currentKey = 0;
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertEquals("La llave del vértice no es la esperada", currentKey, currentVertex.getKey());
			assertEquals("El valor del vértice no es el esperado", currentKey, currentVertex.getValue());
			currentKey++;
		}
	}

	public void testAddEdge(){
		setup2();
		try{
			for( int i = 0; i < graph.capacity() - 1; i++ ){
				graph.addEdge(i, i+1, i+0.5);
			}
		}catch (IllegalStateException e){
			fail( "No debería lanzar excepción.");
		}
		try{
			graph.addEdge(1, 2, 1.5);
			fail( "debería lanzar excepción");
		}catch (IllegalStateException e){

		}
		
		for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
			if( graph.adj(currentVertex.getKey()) != null){
				for( Edge<Integer> currentEdge : graph.adj(currentVertex.getKey()) ){
					assertTrue(currentEdge.either().equals(currentVertex.getKey()) || currentEdge.other(currentEdge.either()).equals(currentVertex.getKey()));
				}
			}
		}
	}
	
	public void testContainsVertex(){
		setup2();
		for( int i = 0; i < graph.V(); i++ ){
			assertTrue("El grafo no contiene el vértice", graph.containsVertex(i));
			assertFalse("El grafo no debería contener el vértice", graph.containsVertex(i + 100000000));
		}
	}
	
	public void testContainsEdge(){
		setup3();
		Edge<Integer> expectedEdge;
		Edge<Integer> expectedEdge2;
		for( int i = 0; i < graph.V() - 1; i++){
			expectedEdge = new Edge<Integer>(i, i+1, i+0.5);
			expectedEdge2 = new Edge<Integer>(i+1, i, i+0.5);
			assertTrue( "El grafo no contiene el eje " + expectedEdge, graph.containsEdge(expectedEdge) );
			assertTrue( "El grafo no contiene el eje " + expectedEdge, graph.containsEdge(expectedEdge2) );

		}
		expectedEdge = new Edge<Integer>(99, 0, 99.5);
		assertFalse( "El grafo no debería contener el eje", graph.containsEdge(expectedEdge) );
	}
	
	public void testDeleteEdge(){
		setup3();
		try{
			graph.deleteEdge( 500, 10, 10.5);;
			fail( "Debería lanzar excepción" );
		}catch (NoSuchElementException e){

		}
		try{
			for( Edge<Integer> currentEdge : graph.edges()){
				graph.deleteEdge( currentEdge );
			}
			assertTrue("No debería haber ejes en el grafo",graph.E() == 0);
		}catch (NoSuchElementException e){
			e.printStackTrace();
			fail("No debería lanzar excepción");

		}
	}
	
	public void testDeleteVertex(){
		setup3();
		setup3();
		try{
			graph.deleteVertex(500);;
			fail( "Debería lanzar excepción" );
		}catch (NoSuchElementException e){

		}
		try{
			for( int i = 99; i >= 0; i--){
				graph.deleteVertex(i);
				assertFalse("El grafo no debería tener el vértice", graph.containsVertex(i));
			}
			assertTrue("El grafo debería estar vacío", graph.isEmpty());
		}catch (NoSuchElementException e){
			fail("No debería lanzar excepción");
		}
	}
	
	public void testDegree(){
		setup3();
		System.out.println(graph);
		graph.addEdge(0, 99, 99.0);
		for(Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertEquals("El grado del vértice no es el esperado" ,graph.degree(currentVertex.getKey()), 2 );
		}
	}
}
