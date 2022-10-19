package model.data_structures;

import java.util.NoSuchElementException;

public class ShortestPath<K,V> {

	
		private Vertex<K, V> vertices[];

		private double[] distTo;          // distTo[v] = distance  of shortest s->v path
	    private DirectedEdge[] edgeTo;    // edgeTo[v] = last edge on shortest s->v path
	    private IndexMinPQ<Double> pq;    // priority queue of vertices

	    /**
	     * Computes a shortest-paths tree from the source vertex {@code s} to every other
	     * vertex in the edge-weighted digraph {@code G}.
	     *
	     * @param  G the edge-weighted digraph
	     * @param  s the source vertex
	     * @throws IllegalArgumentException if an edge weight is negative
	     * @throws IllegalArgumentException unless {@code 0 <= s < V}
	     */
	    public ShortestPath(DirectedGraph<K, V> G, Vertex<K, V> s) {
	    	vertices = (Vertex<K, V>[]) new Vertex[G.V()];
	    	copyVertices(G);
	    	for (DirectedEdge<K> e : G.edges()) {
	            if (e.weight() < 0)
	                throw new IllegalArgumentException("edge " + e + " has negative weight");
	        }

	        distTo = new double[G.V()];
	        edgeTo = new DirectedEdge[G.V()];
            
	        validateVertex(s);

	        for (int v = 0; v < G.V(); v++)
	            distTo[v] = Double.POSITIVE_INFINITY;
	        distTo[positionOf(s)] = 0.0;

	        // relax vertices in order of distance from s
	        pq = new IndexMinPQ<Double>(G.V());
	        
	        pq.insert(positionOf(s),distTo[positionOf(s)]);
	        while (!pq.isEmpty()) {
	            int v = pq.delMin();
	            for (DirectedEdge<K> e : G.adj(s.getKey()))
	                relax(e,G);
	        }

	        // check optimality conditions
	        assert check(G, s);
	    }

	    // relax edge e and update pq if changed
	    private void relax(DirectedEdge<K> e,DirectedGraph<K, V>G) {
	        Vertex<K, V> fromV = G.getVertex(e.fromV()), toV = G.getVertex(e.toV());
	        if (distTo[positionOf(toV)] > distTo[positionOf(fromV)] + e.weight()) {
	            distTo[positionOf(toV)] = distTo[positionOf(fromV)] + e.weight();
	            edgeTo[positionOf(toV)] = e;
	            if (pq.contains(positionOf(toV))) pq.decreaseKey(positionOf(toV), distTo[positionOf(toV)]);
	            else                pq.insert(positionOf(toV), distTo[positionOf(toV)]);
	        }
	    }

	    /**
	     * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
	     * @param  v the destination vertex
	     * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
	     *         {@code Double.POSITIVE_INFINITY} if no such path
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public double distTo(Vertex<K, V> v) {
	        validateVertex(v);
	        return distTo[positionOf(v)];
	    }

	    /**
	     * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
	     *
	     * @param  v the destination vertex
	     * @return {@code true} if there is a path from the source vertex
	     *         {@code s} to vertex {@code v}; {@code false} otherwise
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public boolean hasPathTo(Vertex<K, V> v) {
	        validateVertex(v);
	        return distTo[positionOf(v)] < Double.POSITIVE_INFINITY;
	    }

	    /**
	     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
	     *
	     * @param  v the destination vertex
	     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
	     *         as an iterable of edges, and {@code null} if no such path
	     * @throws IllegalArgumentException unless {@code 0 <= v < V}
	     */
	    public Iterable<DirectedEdge<K>> pathTo(Vertex<K, V> v,DirectedGraph<K, V> G) {
	        validateVertex(v);
	        if (!hasPathTo(v)) return null;
	        Stack<DirectedEdge<K>> path = new Stack<DirectedEdge<K>>();
	        for (DirectedEdge<K> e = edgeTo[positionOf(v)]; e != null; e = edgeTo[positionOf(G.getVertex(e.fromV()))]) {
	            path.push(e);
	        }
	        return path;
	    }


	    // check optimality conditions:
	    // (i) for all edges e:            distTo[e.to()] <= distTo[e.from()] + e.weight()
	    // (ii) for all edge e on the SPT: distTo[e.to()] == distTo[e.from()] + e.weight()
	    private boolean check(DirectedGraph<K, V> G, Vertex<K, V> s) {

	        // check that edge weights are nonnegative
	        for (DirectedEdge<K> e : G.edges()) {
	            if (e.weight() < 0) {
	                System.err.println("negative edge weight detected");
	                return false;
	            }
	        }

	        // check that distTo[v] and edgeTo[v] are consistent
	        if (distTo[positionOf(s)] != 0.0 || edgeTo[positionOf(s)] != null) {
	            System.err.println("distTo[s] and edgeTo[s] inconsistent");
	            return false;
	        }
	        for (int v = 0; v < G.V(); v++) {
	            if (vertices[v] == s) continue;
	            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
	                System.err.println("distTo[] and edgeTo[] inconsistent");
	                return false;
	            }
	        }

	        // check that all edges e = v->w satisfy distTo[w] <= distTo[v] + e.weight()
	        for (int v = 0; v < G.V(); v++) {
	            for (DirectedEdge<K> e : G.adj(vertices[v].getKey())) {
	                Vertex<K, V> toV = G.getVertex(e.toV());
	                if (distTo[v] + e.weight() < distTo[positionOf(toV)]) {
	                    System.err.println("edge " + e + " not relaxed");
	                    return false;
	                }
	            }
	        }

	        // check that all edges e = v->w on SPT satisfy distTo[w] == distTo[v] + e.weight()
	        for (int w = 0; w < G.V(); w++) {
	            if (edgeTo[w] == null) continue;
	            DirectedEdge<K> e = edgeTo[w];
	            Vertex<K, V> v = G.getVertex(e.fromV());
	            if (vertices[w] != G.getVertex(e.toV())) return false;
	            if (distTo[positionOf(v)] + e.weight() != distTo[w]) {
	                System.err.println("edge " + e + " on shortest path not tight");
	                return false;
	            }
	        }
	        return true;
	    }

	    // throw an IllegalArgumentException unless {@code 0 <= v < V}
	    private void validateVertex(Vertex<K, V> v) {
	        int V = distTo.length;
	        if (positionOf(v) < 0 || positionOf(v) >= V)
	            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
	    }
	    
	    

		/**
		 * Copia los vértices del grafo en el arreglo vértices
		 * @param graph Grafo del cual se quieren copiar los vértices
		 */
		private void copyVertices( DirectedGraph<K, V> graph ){
			int index = 0;
			for( Vertex<K, V> currentVertex : graph.vertices()){
				vertices[index] = currentVertex;
				index++;
			}
		}

		/**
		 * Indica la posición de la información del vértice en marked, edgeTo, distTo de acuerdo a vertices
		 * @param vertex vértice del cual se quiere saber su posición
		 * @return posición de la información del vértice en todos los arreglos
		 * @throws NoSuchElementException si no encuentra el vértice, nunca debería lanzar excepción
		 */
		private int positionOf( Vertex<K, V> vertex ) throws NoSuchElementException{
			K key = vertex.getKey();
			for( int i = 0; i < vertices.length; i++ ){
				if( vertices[i].getKey().equals(key) )
					return i;
			}
			throw new NoSuchElementException( "El vértice no fue encontrado" ); 
		}
}
