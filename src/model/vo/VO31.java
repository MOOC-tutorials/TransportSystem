package model.vo;

import model.data_structures.Bag;
import model.data_structures.DirectedEdge;
import model.data_structures.RedBlackBST;
import model.data_structures.Vertex;

public class VO31 {
	int stopId;
	
	Bag<Double> routeIdSParada;
	
	public VO31( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> parada){
	stopId = parada.getKey();
	routeIdSParada = new Bag<>();
	RedBlackBST<Integer, VOStopTimeRuta> bst = parada.getValue();
	for (Integer key  : bst.keys())
	{
	VOStopTimeRuta actual = bst.get(key);
	
	if (!routeIdSParada.contains((double)actual.getRouteId()))	
	routeIdSParada.addAtEnd((double) actual.getRouteId());
	}
	}
	
	
	
	public int getStopId() {
		return stopId;
	}
	
	public Bag<Double> getRouteIdSParada() {
		return routeIdSParada;
	}
	
	public String toString(){
		return " StopID : "  + getStopId() + " Rutas :" + getRouteIdSParada()  ;
	}
}
