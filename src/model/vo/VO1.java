package model.vo;

import model.data_structures.RedBlackBST;
import model.data_structures.Vertex;

/**
 * ParadaAlcanzable del diagrama de secuencia
 */
public class VO1 {
	public int stopIdLlegada;
		
	public boolean transbordo;
	
	public VO1( int stopidLlegada, boolean transbordo  ){
		this.stopIdLlegada = stopidLlegada;
		this.transbordo = transbordo;
	}
	
	public int getStopIdLlegada() {
		return stopIdLlegada;
	}
	
	public String transbordo(){
		return (transbordo == true)? "Sí": "No";
	}
	
	public String toString(){
		return "Se llega desde: " + " Transbordo: " + ((transbordo == true)? "Sí": "No");
	}
}
