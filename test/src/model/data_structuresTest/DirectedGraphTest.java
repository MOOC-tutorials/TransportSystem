package model.data_structuresTest;

import java.util.NoSuchElementException;

import junit.framework.TestCase;
import model.data_structures.DirectedEdge;
import model.data_structures.DirectedGraph;
import model.data_structures.Queue;
import model.data_structures.Bag;
import model.data_structures.Vertex;

public class DirectedGraphTest extends TestCase {

	private DirectedGraph<Integer, Integer> graph;

	public void setup1(){
		graph = new DirectedGraph<>(100);
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

	public void testDirectedGraph(){
		try{
			setup1();
		}catch( IllegalArgumentException e){
			fail( "No debería fallar" );
		}
	}

	public void testDirectedGraphError(){
		try{
			graph = new DirectedGraph<>(-1);
			fail( "Debería fallar" );
		}catch (IllegalArgumentException e) {

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
		DirectedEdge<Integer> expectedEdge;
		for( int i = 0; i < graph.V() - 1; i++){
			expectedEdge = new DirectedEdge<Integer>(i, i+1, i+0.5);
			assertTrue( "El grafo no contiene el eje " + expectedEdge, graph.containsEdge(expectedEdge) );
		}
		expectedEdge = new DirectedEdge<Integer>(99, 0, 99.5);
		assertFalse( "El grafo no debería contener el eje", graph.containsEdge(expectedEdge) );
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

		Integer expectedFromVKey = 0;
		Integer expectedToVKey = 1;
		double expectedWeight = 0.5;
		for( DirectedEdge<Integer> currentEdge : graph.edges() ){
			assertEquals("La llave del vértice de origen no es la esperada", expectedFromVKey, currentEdge.fromV());
			assertEquals("La llave del vértice de destino no es la esperada", expectedToVKey, currentEdge.toV());
			assertEquals("El peso del eje no es el esperado", expectedWeight, currentEdge.weight());
			expectedFromVKey++;
			expectedToVKey++;
			expectedWeight++;
		}
	}

	public void testDeleteVertex(){
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
		}catch (NoSuchElementException e){
			fail("No debería lanzar excepción");
		}
	}

	public void testDeleteEdge(){
		setup3();
		try{
			graph.deleteEdge( 500, 10, 10.5);;
			fail( "Debería lanzar excepción" );
		}catch (NoSuchElementException e){

		}
		try{
			for( DirectedEdge<Integer> currentEdge : graph.edges()){
				graph.deleteEdge( currentEdge );
				for( DirectedEdge<Integer> currentEdge2 : graph.edges()){
					assertFalse( "El grafo debería contener el eje", currentEdge.equals(currentEdge2) );
				}
			}
		}catch (NoSuchElementException e){
			fail("No debería lanzar excepción");
		}
	}

	public void testAdj(){
		setup3();
		for( int i = 0; i < graph.capacity() - 1; i++){
			Bag<DirectedEdge<Integer>> expectedDLL = new Bag<>();
			expectedDLL.addAtEnd(new DirectedEdge<Integer>(i, i+1, i+0.5));
			for( DirectedEdge<Integer> currentEdge : graph.adj(i)){
				assertEquals( "Los vértices de origen no son iguales", currentEdge.fromV(), expectedDLL.getFirst().fromV());
				assertEquals( "Los vértices de destino no son iguales", currentEdge.toV(), expectedDLL.getFirst().toV());
				assertEquals( "Los pesos no son iguales", currentEdge.weight(), expectedDLL.getFirst().weight());
			}
		}
	}

	public void testOutDegree(){
		setup2();
		for( int i = 0; i < graph.capacity(); i++){
			for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
				graph.addEdge(currentVertex.getKey(), currentVertex.getKey(), i+0.8);
			}
		}
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertEquals("El grado de salida del vértice " + currentVertex + " no es el esperado", graph.capacity(), graph.outdegree(currentVertex.getKey()));
		}
	}

	public void testIndegree(){
		setup2();
		for( int i = 0; i < graph.capacity(); i++){
			for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
				graph.addEdge(currentVertex.getKey(), currentVertex.getKey(), i+0.8);
			}
		}
		for( Vertex<Integer, Integer> currentVertex : graph.vertices()){
			assertEquals("El grado de entrada del vértice " + currentVertex + " no es el esperado", graph.capacity(), graph.indegree(currentVertex.getKey()));
		}
	}
	
	public void testEdgesTo(){
		setup2();
		int currentPosition = 0;
		for(Vertex<Integer, Integer> currentVertex : graph.vertices() ){
			Queue<DirectedEdge<Integer>> expectedEdges = new Queue<>();
			if( currentPosition != 0 )
				expectedEdges.enqueue(new DirectedEdge<Integer>(currentPosition, currentPosition+1, currentPosition+0.5));
			for( DirectedEdge<Integer> expectedEdge : graph.edgesTo(currentVertex.getKey())){
				assertEquals(expectedEdges.dequeue(), expectedEdge);
			}
			currentPosition++;
		}
	}

	public void testEdges(){

		setup3();
		Bag<DirectedEdge<Integer>> expectedEdges = new Bag<>();
		for( int i = 0; i < graph.capacity() - 1; i++ ){
			expectedEdges.addAtEnd( new DirectedEdge<Integer>(i, i+1, i+0.5));
		}
		int currentPosition = 0;
		for( DirectedEdge<Integer> currentEdge : graph.edges() ){
			DirectedEdge<Integer> expectedEdge = expectedEdges.getElement(currentPosition);
			assertEquals("El vértice de origen del eje no es el esperado", expectedEdge.fromV(), currentEdge.fromV());
			assertEquals("El vértice de destino del eje no es el esperado", expectedEdge.toV(), currentEdge.toV());
			assertEquals("El peso del eje no es el esperado", expectedEdge.weight(), currentEdge.weight());

			currentPosition++;
		}
	}

	public void testVertices(){
		setup2();
		Queue<Vertex<Integer, Integer>> expectedVertices = new Queue<>();
		for( int i = 0; i < graph.capacity(); i++ ){
			expectedVertices.enqueue(new Vertex<Integer, Integer>(i, i));
		}
		for( Vertex<Integer, Integer> currentVertex : graph.vertices() ){
			Vertex<Integer, Integer> expectedVertex = expectedVertices.dequeue();
			assertEquals("La llave del vértice no es la esperada", expectedVertex.getKey(), currentVertex.getKey());
			assertEquals("El valor del vértice no es el esperado", expectedVertex.getValue(), currentVertex.getValue());
		}
	}
}
