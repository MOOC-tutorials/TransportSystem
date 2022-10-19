package model.vo;

import model.data_structures.Bag;
import model.data_structures.DirectedEdge;

public class VOCamino35 {
	private Bag<Integer> camino;
	
	private int horaSalida;
		
	private int routeIdSalida;
	
	private int routeIdLlegada;
	
	private int tripIdSalida;
	
	private int tripIdLlegada;
	
	public VOCamino35( Iterable<DirectedEdge<Integer>> camino, int horaSalida, int routeIdSalida, int RouteidLlegada, int tripIdSalida, int TripIdllegada){
		this.camino = new Bag<>();
		for( DirectedEdge<Integer> currentEdge : camino  ){
			this.camino.addAtEnd(currentEdge.toV());
		}
		this.horaSalida = horaSalida;
		this.routeIdSalida = routeIdSalida;
		this.routeIdLlegada = RouteidLlegada;
		this.tripIdSalida = tripIdSalida;
		this.tripIdLlegada = TripIdllegada;
	}
	
	public String toString(){
		String ans = "Hora de salida" + horaSalida + " Ruta de salida: " + routeIdSalida + " Ruta de llegada: " + routeIdLlegada + " Viaje de salida: " + tripIdSalida + " Viaje de llegada: " + tripIdLlegada;
		int contadorParadas = 0;
		String camino = "";
		for( Integer currentStopId: this.camino ){
			camino += " " + currentStopId + "";
			contadorParadas++;
		}
		ans += " Paradas :" + contadorParadas + " Camino: " + camino ; 
		return ans;
	}
}
