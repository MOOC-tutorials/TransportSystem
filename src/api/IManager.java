package api;

import java.util.NoSuchElementException;

import model.data_structures.Bag;
import model.data_structures.MST;
import model.data_structures.MaxPriorityQueue;
import model.data_structures.Queue;
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
import model.vo.VORoute;
import model.vo.VOStopTime;

public interface IManager {
	
	
	
	public void cargarGTFS();
	
	public void init();
	
	public void crearGrafo( );
	
	/**
	 * Mï¿½todo para la primera parte del punto 1
	 * @param StopId stopId de la parada de origen
	 * @param fecha fecha 
	 * @return Paradas alcanzables desde la parada de origen y si hubo que hacer transbordo o no para llegar a ella
	 */
	
	public Bag<VO1>  paradasAlcanzables1( String StopId, String fecha );
	
	/**
	 * Mï¿½todo para la segunda parte del punto 1
	 * @param stopIdOrigen StopId de la parada de origen
	 * @param stopIdDestino StopId de la parada de destino
	 * @return Secuencia de paradas para llegar desde la parada de origen a la parada de destino
	 */
	
	public Bag<Integer> camino1(String stopIdOrigen, String stopIdDestino);

	
	public Queue<VO31> consultarParadas31();
	
	public Queue<VO33> itinerarioSalida33(String pStopId);
		
	public Queue<Integer> scc37() throws NoSuchElementException;
	
	public MST<Integer, Integer> mst39(String horaInicio);
	
	/**
	 * metodo 2
	 */
	
	Queue<Queue<Integer>> darSCCRedDeBuses();
	
	/**
	 * metodo 3
	 */
	void construirSubGrafo(String pHoraInicio, String pHoraFin,
			String pStopId, String pDate);
	  
	/**
	 * metodo 3,2
	 */
	Queue<VOStopTimeRuta> darItinerarioLLegada(String pStopid);
	
	/**
	 * metodo 3,4
	 */
	
	DoubleLinkedList<VOStopTimeRuta> darInformacionParadaMasCongestionada();
	
	VOCamino35 caminoMenorLongitud35( String idOrigen, String idDestino, String horaSalida ) throws NoSuchElementException;
	
	
	/**
	 * metodo 3,6
	 */
	
	VO36 CaminoMenorTiempo(String idOrigen, String idDestino, String horaSalida );
	
	
	/**
	 * metodo 3,8
	 */
	VOParadasCiclo38 darInformacionParadasEnCicloMasGrande(String pHoraInicio);
	
}
