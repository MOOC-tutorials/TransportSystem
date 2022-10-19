package model.vo;

import model.data_structures.Bag;
import model.data_structures.RedBlackBST;
import model.data_structures.Vertex;

public class VOParadasCiclo38 {
	private Bag<Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>>> paradas;
		
	public VOParadasCiclo38(){
		paradas = new Bag<>();
	}
	
	public void agregarParada( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> parada){
		paradas.addAtEnd(parada);
	}
	
	private Bag<Integer> rutas(){
		Bag<Integer> rutas = new Bag<>();
		for( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> parada : paradas ){
			for( Integer llave : parada.getValue().keys() ){
				int routeId = parada.getValue().get(llave).getRouteId();
				if( !rutas.contains(routeId) )
					rutas.addAtEnd(routeId);
			}
		}
		return rutas;
	}
	
	private Bag<Integer> viajes(){
		Bag<Integer> viajes = new Bag<>();
		for( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> parada : paradas ){
			for( Integer llave : parada.getValue().keys() ){
				int tripId = parada.getValue().get(llave).getTripId();
				if( !viajes.contains(tripId) )
					viajes.addAtEnd(tripId);
			}
		}
		return viajes;
	}
	
	private Bag<Integer> ids(){
		Bag<Integer> ids = new Bag<>();
		for( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> parada : paradas ){
			ids.addAtEnd(parada.getKey());
		}
		return ids;
	}
	
	
	public String toString(){
		return "Paradas: " + ids() + " Rutas: " + rutas() + " Viajes: " + viajes();
	}
}
