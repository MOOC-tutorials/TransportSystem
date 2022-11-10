package Controller;

import java.util.NoSuchElementException;

import api.IManager;
import model.data_structures.Bag;
import model.data_structures.MST;
import model.data_structures.MaxPriorityQueue;
import model.data_structures.Queue;
import model.logic.Manager;
import model.vo.VO1;
import model.vo.VO31;
import model.vo.VO33;
import model.vo.VOCamino35;
import model.vo.VOParadasCiclo38;
import model.vo.VO36;

import model.vo.VOStopTimeRuta;
import model.data_structures.DirectedGraph;
import model.data_structures.DoubleLinkedList;
import model.data_structures.RedBlackBST;
import model.exceptions.DateNotFoundExpection;
import model.vo.VOStopTime;

public class Controller { 

	private final static IManager manager = new Manager();

	public final static void cargarGTFS(){
		manager.init();
		manager.cargarGTFS( );
		manager.crearGrafo();

	}

	public final static Bag<VO1> paradasAlcanzables1( String stopId, String fecha ){
		return manager.paradasAlcanzables1( stopId, fecha );
	}

	
	

	public final static Bag<Integer> camino1( String stopIdOrigen, String stopIdDestino ){
		return manager.camino1( stopIdOrigen, stopIdDestino );
	}

	public static Queue<VO31> consultarParadas31(){
		return manager.consultarParadas31();
	}

	public static Queue<VO33> itinerarioSalida33(String pStopid){
		return manager.itinerarioSalida33(pStopid);
	}

	public static Queue<Integer> scc37() throws NoSuchElementException{
		
			return manager.scc37();
		
	}

	public static MST<Integer, Integer> mst39( String horaInicio ){

		return manager.mst39(horaInicio);
	}



	/**
	 * metodo 2
	 */


	public static Queue<Queue<Integer>> darSCCRedDeBuses(){
		return manager.darSCCRedDeBuses();
	}

	/**
	 * metodo 3
	 */
	public static void construirSubGrafo(String pHoraInicio, String pHoraFin, String pStopId, String pDate){
		manager.construirSubGrafo(pHoraInicio, pHoraFin, pStopId, pDate);
	}

	/**
	 * metodo 3,2
	 */
	public static Queue<VOStopTimeRuta> darItinerarioLLegada(String pStopid){
		return manager.darItinerarioLLegada(pStopid);
	}

	/**
	 * metodo 3,4
	 */

	
	public static DoubleLinkedList<VOStopTimeRuta> darInformacionParadaMasCongestionada(){
		return manager.darInformacionParadaMasCongestionada();
	}
	


	


	public static VOCamino35 caminoMenorLongitud35( String idOrigen, String idDestino, String horaSalida ) throws NoSuchElementException{
		try{
			return manager.caminoMenorLongitud35( idOrigen, idDestino, horaSalida);
		}catch ( NoSuchElementException e){
			throw new NoSuchElementException(e.getMessage());
		}
	}


	/**
	 * metodo 3,6
	 */

	public static VO36 CaminoMenorTiempo(String idOrigen, String idDestino, String horaSalida ){
		return manager.CaminoMenorTiempo(idOrigen,idDestino,horaSalida);
	}


	/**
	 * metodo 3,8
	 */
	public static VOParadasCiclo38 darInformacionParadasEnCicloMasGrande(String pHoraInicio){
		return manager.darInformacionParadasEnCicloMasGrande(pHoraInicio);
	}
}
