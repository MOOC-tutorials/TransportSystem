package model.data_structures;

import java.util.NoSuchElementException;


public class Cycles<K,V> {
	/**
	 * Arreglo que en cada posici�n tiene un boolean para indicar cuando se marca un v�rtice en DFS.
	 */
	private boolean [] marked;

	/**
	 * Arreglo que en cada posici�n
	 */
	private int [] edgeTo;

	private boolean  [] onStack;


	private Stack<Vertex<K, V>> cycle;
	
	private RedBlackBST<Stack<Vertex<K, V>>, Stack<Vertex<K, V>>> cyclesBST;

	private Vertex<K, V> vertices[];

	@SuppressWarnings("unchecked")
	public Cycles (DirectedGraph<K, V> graph){
		int verticesNo = graph.V();
		cyclesBST = new RedBlackBST<>();
		marked = new boolean[verticesNo];
		onStack = new boolean [verticesNo];
		edgeTo = new int [verticesNo];
		vertices = (Vertex<K, V>[]) new Vertex[verticesNo];
		copyVertices(graph);
		for (int v = 0; v < verticesNo; v ++)
			if (!marked[v]){
				dfs(graph,vertices[v]);
			}
	}


//	private void dfs(DirectedGraph<K,V> graph, Vertex<K, V> vertex){
//		int positionOfVertex = positionOf(vertex);
//		marked[positionOfVertex] = true;
//		onStack[positionOfVertex] = true;
//		for( DirectedEdge<K> currentEdge : graph.adj(vertex.getKey())){
//			Vertex<K, V> toVertex = graph.getVertex(currentEdge.toV());
//			//			if (cycle != null) return ;
//
//			if (!marked[positionOf(toVertex)]){
//				edgeTo[positionOf(toVertex)] = positionOfVertex;
//				dfs(graph, toVertex);
//			}
//
//			else if (onStack[positionOf(toVertex)]){
////				for( int i = 0; i < marked.length && marked[i] == true; i++){
////					marked[i] = false;
////					onStack[i] = false;
////				}
//				cycle = new Stack<Integer>();
//				for (int x = positionOfVertex; x!= positionOf(toVertex); x = edgeTo[x]){
//					cycle.push(x);
//				}
//				cycle.push(positionOf(toVertex));
//				cycle.push(positionOfVertex);
//				assert check(cycle);
//				if( !cycles.contains(cycle) )
//					cycles.addAtEnd(cycle);
//				else
//					break;
//			}
//		}
//		onStack[positionOfVertex] = false;
//		System.out.println(cycles);
//	}
	
	private void dfs(DirectedGraph<K,V> graph, Vertex<K, V> vertex){
		int positionOfVertex = positionOf(vertex);
		marked[positionOfVertex] = true;
		onStack[positionOfVertex] = true;
		for( DirectedEdge<K> currentEdge : graph.adj(vertex.getKey())){
			Vertex<K, V> toVertex = graph.getVertex(currentEdge.toV());
			//			if (cycle != null) return ;

			if (!marked[positionOf(toVertex)]){
				edgeTo[positionOf(toVertex)] = positionOfVertex;
				dfs(graph, toVertex);
			}

			else if (onStack[positionOf(toVertex)]){
//				for( int i = 0; i < marked.length && marked[i] == true; i++){
//					marked[i] = false;
//					onStack[i] = false;
//				}
				cycle = new Stack<Vertex<K, V>>();
				for (int x = positionOfVertex; x!= positionOf(toVertex); x = edgeTo[x]){
					cycle.push(vertices[x]);
				}
				cycle.push(toVertex);
				cycle.push(vertices[positionOfVertex]);
				assert check(cycle);
				if( !cyclesBST.contains(cycle) )
					cyclesBST.put(cycle, cycle);
				else
					break;
			}
		}
		onStack[positionOfVertex] = false;
	}

	/**
	 * @return true if the digraph has a directed cycle, false otherwise.
	 */
	public boolean hasCycle() {
		return !cyclesBST.isEmpty();
	}

//	public Stack<Integer> longestCycle(){
//		Stack<Integer> longestCycle = cycles.getFirst();
//		for( Stack<Integer> currentCycle : cycles ){
//			if( currentCycle.getSize() > longestCycle.getSize() )
//				longestCycle = currentCycle;
//		}
//		return longestCycle;
//	}
	
	public MaxPriorityQueue<Stack<Vertex<K, V>>> longestCycles(){
		MaxPriorityQueue<Stack<Vertex<K, V>>> longestCycles = new MaxPriorityQueue<>(cyclesBST.size());

		for( Stack<Vertex<K, V>> currentCycle : cyclesBST.keys() ){
			longestCycles.insert(currentCycle);
		}
		return longestCycles;
	}

	/** 
	 * @return  all cycles in a directed graph if there are any or an emtpy list otherwise
	 */
	public Iterable<Stack<Vertex<K, V>>> cycles() {
		Bag<Stack<Vertex<K, V>>> cycles = new Bag<>();
		for( Stack<Vertex<K, V>> currentCycle : cyclesBST.keys() ){
			cycles.addAtEnd(currentCycle);
		}
		return cycles;
	}

	
	private boolean check(Stack<Vertex<K, V>> cycle){
		// verify cycle
		Vertex<K, V> first = null, last = null;
		for (Vertex<K, V> v : cycle) {
			if (first == null) first = v;
			last = v;
		}
		if (first != last) {
			System.err.printf("cycle begins with %d and ends with %d\n", first, last);
			return false;
		}
	return true;
}

	/**
	 * Copia los v�rtices del grafo en el arreglo v�rtices
	 * @param graph Grafo del cual se quieren copiar los v�rtices
	 */
	private void copyVertices( DirectedGraph<K, V> graph ){
		int index = 0;
		for( Vertex<K, V> currentVertex : graph.vertices()){
			vertices[index] = currentVertex;
			index++;
		}
	}
	/**
	 * 
	 * @param vertex
	 * @return
	 * @throws NoSuchElementException
	 */
	private int positionOf( Vertex<K, V> vertex ) throws NoSuchElementException{
		K key = vertex.getKey();
		for( int i = 0; i < vertices.length; i++ ){
			if( vertices[i].getKey().equals(key) )
				return i;
		}
		throw new NoSuchElementException( "El v�rtice no fue encontrado" ); 
	}
}
