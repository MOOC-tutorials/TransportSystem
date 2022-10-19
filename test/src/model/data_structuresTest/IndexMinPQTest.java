package model.data_structuresTest;

import junit.framework.TestCase;
import model.data_structures.IndexMinPQ;

public class IndexMinPQTest extends TestCase{
	
	private IndexMinPQ<Double> pq;
	
	public void setup1(){
		pq = new IndexMinPQ<Double>(100);
	}
	
	public void setup2(){
		setup1();
		for( int i = 0; i < 100; i++){
			pq.insert(i,new Double(i + 0.5));
		}
	}
	
	public void testIsempty(){
		setup1();
		assertTrue("La cola debería estar vacía", pq.isEmpty());
		setup2();
		assertFalse("La cola no debería estar vacía", pq.isEmpty());
	}
	
	public void testInsert(){
		setup2();
		boolean greaterThanCurrentMin = true;
		for( int i = 0; i < 100; i++){
			Integer currentMin = pq.delMin();
			for( Integer index: pq){
				Double element = pq.keyOf(index);
				if( element < currentMin ){
					greaterThanCurrentMin = false;
					break;
				}
			}
		}
		assertTrue("Debería ser true", greaterThanCurrentMin);
	}
	
	public void testDelMin(){
		setup2();
		
		int min = pq.delMin();
		boolean greaterThanMin = true;
		for( Integer index: pq){
			Double element = pq.keyOf(index);
			if( element < min )
				greaterThanMin = false;
				break;
		}
		assertTrue("Deberìa ser true", greaterThanMin);
	}
	
	public void testSize(){
		setup2();
		assertEquals("El tamaño de la cola no es el esperado",100, pq.size());
	}
	
	public void testContains(){
		setup1();
		
		for( int i = 0; i < 100; i++){
			assertFalse("La cola no debería tener el elemento", pq.contains(i));
		}
		setup2();
		for( int i = 0; i < 100; i++){
			assertTrue("La cola debería tener el elemento", pq.contains(i));
		}
		for( int i = 100; i < 200; i++ ){
			try{
				pq.contains(i);
				fail( "Debería lanzar excepción ");
			}catch ( IllegalArgumentException e){
				
			}
		}
	}
	
	public void testMinIndex(){
		setup2();
		for( int i = 0; i < 100; i++){
			assertEquals(i, pq.minIndex());
			pq.delMin();
		}
	}
	
	public void testKeyOf(){
		setup2();
		for( int i = 0; i < 100; i++){
			assertEquals(i+0.5, pq.keyOf(pq.minIndex()));
			pq.delMin();
		}
	}
	
	public void testChangeKey(){
		setup2();
		for( int i = 0; i < 100; i++){
			Double currentKey = pq.keyOf(i);
			pq.changeKey(i, currentKey+1);
		}
		for( int i = 0; i < 100; i++){
			assertEquals(i+1.5, pq.keyOf(pq.minIndex()));
			pq.delMin();
		}
	}
	
	public void testDecreaseKey(){
		setup2();
		pq.decreaseKey(5, 4.0);
		assertEquals(4.0, pq.keyOf(5));
		assertEquals(4.5, pq.keyOf(4));
	}
	
	public void testIncreaseKey(){
		setup2();
		pq.increaseKey(5, 6.0);
		assertEquals(6.0, pq.keyOf(5));
		assertEquals(6.5, pq.keyOf(6));
	}
	
	public void testDelete(){
		setup2();
		for( int i = 0; i < 100; i++){
			pq.delete(i);
		}
		assertTrue("La cola debería estar vacía", pq.isEmpty());
	}
}
