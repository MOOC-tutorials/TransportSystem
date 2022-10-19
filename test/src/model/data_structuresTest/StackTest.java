package model.data_structuresTest;

import junit.framework.TestCase;
import model.data_structures.Stack;
import model.data_structures.Stack.Node;

public class StackTest extends TestCase {

	/**
	 * Atribto de la clase Stack,permite hacer las pruebas
	 */
	private Stack<Integer> pila ;
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
	 * Crea el setUp necesario para realizar las pruebas
	 */
	public void setUp1 (){
		try{
			pila = new Stack<Integer>();
			node1 = new Node<Integer>( 1 );
			node2 = new Node<Integer>( 2 );
			node3 = new Node<Integer>( 3 );



		}
		catch(Exception e){
			fail( "No debería lanzar excepción" );	
		}


	}


	/**
	 *  Prueba que se cree una pila vacia
	 */
	public void testStack (){
		setUp1();
		assertNotNull("La fila debio haber sido creada", pila);
	}


	/**
	 * Prueba el metodo isEmpty() de la clase Stack
	 */
	public void testIsEmpty(){
		setUp1();


		assertTrue("La fila debería estar vacía", pila.isEmpty());

		pila.push(node1.getElement());
		assertFalse("La lista no debería estar vacía", pila.isEmpty());
	}



	/**
	 * Prueba el metodo getSize() de la clase stack
	 */
	public void testGetSize(){
		setUp1();

		pila.push(node1.getElement());
		assertEquals("El tamano de la pila no es el correcto", new Integer (1), pila.getSize());

		pila.push(node2.getElement());
		assertEquals("El tamano de la pila no es el correcto", new Integer (2), pila.getSize());

		pila.push(node3.getElement());
		assertEquals("El tamano de la pila no es el correcto", new Integer (3), pila.getSize());



	}

	/**
	 * Prueba el metodo push de la clase Stack y verifica que el nuevo nodo sea agregado
	 * y guardado como el primero
	 */
	public void testPush(){
		setUp1();

		pila.push(node1.getElement());
		assertEquals("El primer elemento no es el esperado",node1.getElement(),pila.peek().getElement() );
		pila.push(node2.getElement());
		assertEquals("El primer elemento no es el esperado",node2.getElement(),pila.peek().getElement() );
		pila.push(node3.getElement());
		assertEquals("El primer elemento no es el esperado",node3.getElement(),pila.peek().getElement() );






	}


	/**
	 * Prueba el metodo pop de la clase Stack y verifica que elimine y retorne el nodo
	 * que fue agragado recientemente
	 */
	public void testPop(){

		setUp1();
		pila.push(node1.getElement());
		pila.push(node2.getElement());
		pila.push(node3.getElement());
		assertEquals("El elemento no es el esperado ",node3.getElement(),pila.pop());
		assertEquals("El elemento no es el esperado ",node2.getElement(),pila.pop());
		assertEquals("El elemento no es el esperado ",node1.getElement(),pila.pop());


	}



}
