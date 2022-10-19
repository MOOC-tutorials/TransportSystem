package model.data_structuresTest;

import junit.framework.TestCase;

import model.data_structures.Queue;
import model.data_structures.Queue.Node;

public class QueueTest extends TestCase {

	/**
	 * Atributo de tipo fila que permite realizar las pruebas
	 */
	private Queue<Integer> fila;


	/**
	 * Nodo 1 de la fila
	 */
	private Node<Integer> node1;
	/**
	 * Nodo 2 de la fila
	 */
	private Node<Integer> node2;
	/**
	 * Nodo 3 de la fila
	 */
	private Node<Integer> node3;

	/**
	 * Crea el setUp necesario para las pruebas
	 */
	public void setUp1 (){
		try{
			fila = new Queue<Integer>();
			node1 = new Node<Integer>( 1 );
			node2 = new Node<Integer>( 2 );
			node3 = new Node<Integer>( 3 );

		}
		catch(Exception e){
			fail( "No debería lanzar excepción" );	
		}


	}

	/**
	 * Verifica si se creo una nueva fila vacia
	 */
	public void testQueue (){
		setUp1();
		assertNotNull("La fila debio haber sido creada", fila);
	}
	/**
	 * Prueba el metodo isEmpty de la clase Queue
	 */
	public void testIsEmpty(){
		setUp1();


		assertTrue("La fila debería estar vacía", fila.isEmpty());

		fila.enqueue(node1.getElement());
		assertFalse("La lista no debería estar vacía", fila.isEmpty());
	}

	/**
	 * Prueba el metodo getSie() de la clase Queue
	 */
	public void testGetSize(){
		setUp1();

		fila.enqueue(node1.getElement());
		fila.enqueue(node2.getElement());
		fila.enqueue(node3.getElement());

		assertEquals("El nodo no es el esperado", 3, fila.getSize());
	}


	/**
	 * Prueba el metodo enQueue() de la clase Queue y verifica que el nuevo elemento sea agregado al final 
	 * de la fila  
	 */
	public void testEnqueue(){
		setUp1();
		fila.enqueue(node1.getElement());
		assertEquals( "El primer elemento no es el esperado", node1.getElement(), fila.peek());
		assertEquals( "El último elemento no es el esperado", node1.getElement(), fila.getlast().getElement() );
		assertEquals( "El tamaño de la lista no es el esperado", 1, fila.getSize() );
		assertNull( "El nodo siguiente no es el esperado", node1.getNext() );

		fila.enqueue(node2.getElement());
		assertEquals( "El primer elemento no es el esperado", node1.getElement(), fila.peek());
		assertEquals ("El elemento ultimo elemento no es el esperado",node2.getElement(), fila.getlast().getElement());
		assertEquals( "El tamaño de la lista no es el esperado", 2, fila.getSize() );
		assertNull( "El elemento siguiente no es el esperado", node2.getNext() );

		fila.enqueue(node3.getElement());
		assertEquals( "El primer elemento no es el esperado", node1.getElement(), fila.peek());
		assertEquals ("El elemento ultimo elemento no es el esperado",node3.getElement(), fila.getlast().getElement());
		assertEquals( "El tamaño de la lista no es el esperado", 3, fila.getSize() );
		assertNull( "El elemento siguiente no es el esperado", node3.getNext() );




	}
	/**
	 * Prueba el metodo deQueue() de la clase Queue y verifica que se elimine y retorne
	 * el primer elemento
	 * 
	 */
	public void testDequeue(){
		setUp1();	
		fila.enqueue(node1.getElement());
		fila.enqueue(node2.getElement());

		assertEquals( "El elemento  que se quiere eliminar no es el esperado", node1.getElement(), fila.dequeue() );
		assertEquals( "El elemento que se quiere eliminar no es el esperado", node2.getElement(), fila.peek());



	}



}
