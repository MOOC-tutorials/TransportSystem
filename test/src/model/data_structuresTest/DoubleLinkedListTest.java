

package model.data_structuresTest;

import java.util.NoSuchElementException;

import junit.framework.TestCase;
import model.data_structures.DoubleLinkedList;


/**
 * Clase de pruebas para DoubleLinkList.Java
 * @author da.ramos,hd.castellanos
 *
 */
public class DoubleLinkedListTest extends TestCase {

	/**
	 * Atributo que permite probar la clase DoubleLinkedList
	 */
	private DoubleLinkedList<Double> list;

	/**
	 * Crea un escenario con la nueva lista y los 3 nodos
	 */
	public void setup1(){
		try{
			list = new DoubleLinkedList<>();
		} catch( Exception e){
			fail( "No debería lanzar excepción" );
		}
	}

	/**
	 * Prueba que se cree la lista vacia
	 */
	public void testDoubleLinkedList(){
		setup1();
		assertNotNull("La lista debió haber sido creada", list);
	}

	/**
	 * Prueba el metodo get size de la clase DoubleLinkedList
	 */
	public void testGetSize(){
		setup1();

		for( int i = 0; i < 100; i++){
			int range =  5 ;     
			list.addAtEnd(new Double((Math.random() * range)));
		}
		assertEquals("El tamaño no es el esperado", 100, list.getSize());
	}

	/**
	 * Prueba el metodo isEmpty() de la clase DoubleLinkedList
	 */
	public void testIsEmpty(){
		setup1();
		assertTrue("La lista debería estar vacía", list.isEmpty());
		list.addAtEnd(new Double(3.4));
		assertFalse("La lista no debería estar vacía", list.isEmpty());
	}
	
	/**
	 * Prueba el método isOrdered() de la clase DoubleLinkedList.
	 */
	public void testIsOrdered(){
		setup1();
		int range =  10 ;     
		for( int i = 0; i < 100; i++){
			list.addAtEnd(new Double((Math.random() * range)));
		}
		assertFalse("La lista no debería estar ordenada", list.isOrdered());
		setup1();
		for( int i = 0; i < 100; i++){
			list.addAtEnd(new Double(i + 0.5));
		}
		assertTrue("La lista debería estar ordenada", list.isOrdered());
	}

	/**
	 * Prueba el metodo addAtEnd() de la clase DoubleLinkedList
	 */
	public void testAddAtEnd(){
		setup1();

		int range = 4;
		Double random = new Double( Math.random() * range);
		list.addAtEnd(random);
		assertEquals("El úlimo nodo no es el esperado", random, list.getLast());
		random = new Double( Math.random() * range);
		list.addAtEnd(random);
		assertEquals( "El último nodo no es el esperado", random, list.getLast());
	}


	/**
	 * Prueba el metodo addAtK() de la clase DoubleLinkedList
	 * y verifica que el nodo se agregue correctamente en el indice deseado
	 */
	public void testAddAtK(){
		setup1();

		try{
			Double toAdd = new Double( 5.555 );
			list.addAtK(toAdd, 2);
			fail( "No debería agregar el nodo" );
		} catch( NoSuchElementException e){
			assertTrue("No se agregó el nodo", list.isEmpty());
		}

		try{
			list.addAtEnd(5.555);
			list.addAtEnd(5.555);
			list.addAtK(3.5, 3);
			fail( "No debería agregar el nodo" );
		} catch( NoSuchElementException e){
			assertEquals( "No se agregó el nodo", 2, list.getSize() );
		}
		list = new DoubleLinkedList<Double>();
		list.addAtEnd(5.2);
		list.addAtEnd(443.3);
		list.addAtK(53.2, 2);
		assertEquals( "El primer elemento no es el esperado", 5.2 , list.getFirst() );
		assertEquals( "El segundo elemento no es el esperado", 443.3 , list.getElement(1) );
		assertEquals( "El tercer elemento no es el esperado", 53.2, list.getLast() );


		list = new DoubleLinkedList<Double>();
		list.addAtEnd(2.3);
		list.addAtK(1.2, 1);
		assertEquals( "El primer elemento no es el esperado", 2.3, list.getFirst() );
		assertEquals( "El segundo elemento no es el esperado", 1.2, list.getElement(1) );

		list = new DoubleLinkedList<Double>();
		list.addAtEnd(1.5);
		list.addAtEnd(3.36);
		list.addAtK(2.35, 0);
		assertEquals( "El primer elemento no es el esperado", 2.35, list.getFirst() );
		assertEquals( "El segundo elemento no es el esperado", 1.5, list.getElement(1) );
		assertEquals( "El tercer elemento no es el esperado", 3.36, list.getLast() );
	}

	/**
	 * Prueba el metodo getElement() de la clase DoubleLinkedList
	 * y comprueba que recupere el nodo que se encuentra en el indice 
	 * dado por parametro
	 */
	public void testGetElement(){
		setup1();

		assertNull( "Debería ser null", list.getElement(1) );

		Double[] doubles = new Double[100];
		int range = 10;
		for( int i = 0; i < doubles.length ; i++ ){
			doubles[i] = Math.random() * range;
			list.addAtEnd(doubles[i]);
		}
		int cnt = 0;
		for( Double element : list){
			assertEquals( "El nodo no es el esperado", doubles[cnt], element );
			cnt++;
		}
		assertNull( "Debería ser null", list.getElement(doubles.length) );
	}

	/**
	 * prueba el metodo delete() de la clase DoubleLinkedList
	 * y comprueba que se elimine correctamente el nodo deseado
	 */
	public void testDelete(){
		setup1();

		Double[] doubles = new Double[100];
		for( int i = 0; i < doubles.length ; i++ ){
			doubles[i] = i + 0.5;
			list.addAtEnd(doubles[i]);
		}
		
		try{
			list.delete( 100.5 );
			fail( "No debería eliminar el nodo" );
		} catch( NoSuchElementException e){
			assertEquals( "El tamaño de la lista no es el esperado", doubles.length, list.getSize() );
		}

		
		list.delete(doubles[doubles.length-1] );
		assertEquals( "El último nodo no es el esperado", doubles[98], list.getLast() );
		assertEquals( "El tamaño de la lista no es el esperado", doubles.length - 1, list.getSize());

		
		list.delete(doubles[60]);
		assertEquals( "El nodo no es el esperado", doubles[61], list.getElement(60) );
		assertEquals( "El tamaño de la lista no es el esperado", doubles.length - 2, list.getSize());
		
		list.delete(doubles[0]);
		assertEquals( "El primer nodo no es el esperado", doubles[1], list.getFirst() );
		assertEquals( "El tamaño de la lista no es el esperado", doubles.length - 3, list.getSize());

	}

	/**
	 * prueba el metodo deleteAtK() de la clase DoubleLinkedList
	 * y comprueba que se elimine correctamente el nodo que se encuentra en 
	 * el indice que entra por parametro
	 */
	public void testDeleteAtK(){
		setup1();

		Double[] doubles = new Double[100];
		for( int i = 0; i < doubles.length ; i++ ){
			doubles[i] = i + 0.5;
			list.addAtEnd(doubles[i]);
		}

		try{
			list.deleteAtK(doubles.length);
			fail( "Debería lanzar excepción" );
		} catch( NoSuchElementException e){
			assertEquals( "El tamaño de la lista no es el esperado", doubles.length, list.getSize() );
		}

		list.deleteAtK(doubles.length - 1);
		assertEquals( "El nodo no es el esperado", doubles[doubles.length - 2], list.getLast() );
		assertEquals( "El tamaño de la lista no es el esperado", doubles.length -1, list.getSize());


		list.deleteAtK(50);
		assertEquals( "El nodo no es el esperado", doubles[51], list.getElement(50));
		assertEquals( "El tamaño de la lista no es el esperado", doubles.length -2, list.getSize());
		list.deleteAtK(0);
		assertEquals( "El nodo no es el esperado", doubles[1], list.getFirst() );
		assertEquals( "El tamaño de la lista no es el esperado", doubles.length -3, list.getSize());
	}

	/**
	 * Prueba el metodo postionOf() de la clase DoubleLinkedList
	 * y comprueba que se retorne la posicion del nodo dado por parametro
	 */
	public void testPositionOf(){
		setup1();

		Double[] doubles = new Double[100];
		for( int i = 0; i < doubles.length ; i++ ){
			doubles[i] = i + 0.5;
			list.addAtEnd(doubles[i]);
		}
		
		try{
			list.positionOf(100.5);
			fail( "Debería fallar" );
		} catch( NoSuchElementException e ){
			assertEquals("El tamaño de la lista no es el esperado", doubles.length, list.getSize());
		}

		assertEquals( "La posición del nodo no es la esperada", 0, list.positionOf(doubles[0]) );
		assertEquals( "La posición del nodo no es la esperada", 55, list.positionOf(doubles[55]) );
		assertEquals( "La posición del nodo no es la esperada", doubles.length -1, list.positionOf(doubles[doubles.length -1]) );
	}
	
	/**
	 * Prueba el método replace de DoubleLinkedList
	 */
	public void testReplace(){
		setup1();
		
		Double[] doubles = new Double[100];
		for( int i = 0; i < doubles.length ; i++ ){
			doubles[i] = i + 0.5;
			list.addAtEnd(doubles[i]);
		}
		
		list.replace(5.555, 0);
		list.replace(1.111, 60);
		list.replace(4.444, list.getSize() -1);
		
		assertEquals("El nodo no es el esperado", 5.555, list.getFirst());
		assertEquals("El nodo no es el esperado", 1.111, list.getElement(60));
		assertEquals("El nodo no es el esperado", 4.444, list.getLast());
	}
	
	/**
	 * Prueba el método mergeSort() de DoubleLinkedList.
	 */
	public void testMergeSort(){
		setup1();
		int range =  10 ;     
		for( int i = 0; i < 1000; i++){
			list.addAtEnd(new Double((Math.random() * range)));
		}
		list.mergeSort();
		assertTrue("La lista no está ordenada", list.isOrdered());
	}
}
