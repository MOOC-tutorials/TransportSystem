package model.vo;

import api.IList;
import model.data_structures.DoubleLinkedList;
import model.data_structures.Queue;



/**
 * Clase que modela un objeto de tipo VOStopTIme
 * @author da.ramos,hd.castellanos
 *
 */
public class VOStopTime implements Comparable<VOStopTime>{
	/**
	 * Atributo para el id del viaje
	 */
	private int tripId;

	/**
	 * Atributo para la hora de llegada
	 */
	private String arrivalTime;

	/**
	 * Atributo para la hora de salida
	 */
	private String departureTime;

	/**
	 * Atributo para el id de la parada
	 */
	private int stopId;

	/**
	 * Atributo para la secuencia de parada
	 */
	private int stopSequence;

	/**
	 * Atributo para el afiche de parada
	 */
	private String stopHeadsign;

	/**
	 * Atributo para el tipo de recogida
	 */
	private int pickupType;

	/**
	 * Atributo para el tipo de dropoff
	 */
	private int dropOffType;

	/**
	 * Atributo para la distancia
	 */
	private double shapeDistTraveled;

	/**
	 *  lista de paradas para este StopTime
	 */
	/**
	 * lista de viajes para este stopTIme
	 */
	private DoubleLinkedList <VOTrip> trips;

	/**
	 * Crea un nuevo tiempo de parada con la informacion dada por parametro
	 * @param pTripId id de la ruta 
	 * @param pArrivalTime hora de llegada
	 * @param pDepartureTime hora de salida
	 * @param pStopId id de la parada
	 * @param pStopSequence secuencia de parada
	 * @param pStopHeadsign Afiche de parada
	 * @param pPickupType tiempo de recogida
	 * @param pDropoffType tiempo de DropOff
	 * @param pShapeDistTraveled  distancia recorrida
	 */
	public VOStopTime( int pTripId , String pArrivalTime, String pDepartureTime, int pStopId, int pStopSequence, String pStopHeadsign, int pPickupType, int pDropoffType, double pShapeDistTraveled ){
		tripId = pTripId;
		arrivalTime = pArrivalTime;
		departureTime = pDepartureTime;
		stopId = pStopId;
		stopSequence = pStopSequence;
		stopHeadsign = pStopHeadsign;
		pickupType = pPickupType;
		dropOffType = pDropoffType;
		shapeDistTraveled = pShapeDistTraveled;

	}

	/**
	 * @return id del viaje
	 */
	public int getTripId(){
		return tripId;
	}
	/**
	 * @return hora de llegada
	 */
	public String getArrivalTime(){
		return arrivalTime;
	}
	/**
	 * @return hora de salida
	 */
	public String getDepartureTime(){
		return departureTime;
	}
	/**
	 * @return id de la parada
	 */
	public int getStopId(){
		return stopId;
	}

	/**
	 * @return secuencia de paradas
	 */
	public int getStopSequence(){
		return stopSequence;
	}

	/**
	 * @return afiche de parada
	 */
	public String getStopHeadsign(){
		return stopHeadsign;
	}
	/**
	 * @return tipo de recogida
	 */
	public int getPickupType(){
		return pickupType;
	}
	/**
	 * @return tipo de dropoff
	 */
	public int getDropoffType(){
		return dropOffType;
	}
	/**
	 * @return Distancia recorrida 
	 */
	public double getShapeDistTraveled(){
		return shapeDistTraveled;
	}


	public IList<VOTrip> getTrips(){
		return trips;
	}
	public void addTrip (VOTrip pTrip){
		trips.addAtEnd(pTrip);
	}


	public String toString(){
		return "StopID: " + stopId + " TripId: " + tripId + " ArrivalTime: " + arrivalTime;
	}

	@Override
	public int compareTo(VOStopTime pVOStopTime) {
		if( stopSequence > pVOStopTime.getStopSequence() )
			return 1;
		if( stopSequence < pVOStopTime.getStopSequence() )
			return -1;
		else
			return 0;
	}
}
