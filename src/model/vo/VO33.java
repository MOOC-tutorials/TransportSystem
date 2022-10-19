package model.vo;

public class VO33 implements Comparable<VO33> {
	private int routeId;
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
	public VO33( VOStopTimeRuta stopTime  ){
		this.routeId = stopTime.getRouteId();
		tripId = stopTime.getTripId();
		arrivalTime = stopTime.getArrivalTime();
		departureTime = stopTime.getDepartureTime();
		stopId = stopTime.getStopId();
		stopSequence = stopTime.getStopSequence();
		stopHeadsign = stopTime.getStopHeadsign();
		pickupType = stopTime.getPickupType();
		dropOffType = stopTime.getDropoffType();
		shapeDistTraveled = stopTime.getShapeDistTraveled();
	}

	/**
	 * @return id del viaje
	 */
	public int getTripId(){
		return tripId;
	}
	
	public int getRouteId() {
		return routeId;
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


	public String toString(){
		return "StopID: " + stopId + " TripId: " + tripId + " DepartureTime: " + departureTime + "routeID: " + routeId ;
	}

	@Override
	public int compareTo(VO33 pVOStopTimeRuta) {
		return  arrivalTime.compareTo(pVOStopTimeRuta.getDepartureTime());
	}
}
