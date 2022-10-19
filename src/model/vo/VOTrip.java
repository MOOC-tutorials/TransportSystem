package model.vo;

import api.IList;
import model.data_structures.Bag;
import model.data_structures.Queue;

public class VOTrip implements Comparable<VOTrip>{

	/**
	 *  atributo para el id de la ruta
	 */
	private int routeId;

	/**
	 * Atributo para el id del servicio
	 */
	private int serviceId;

	/**
	 * Atributo para el id del viaje
	 */
	private int TripId;

	/**
	 * Atributo para el afiche del viaje
	 */
	private String tripHeadSing;

	/**
	 * Atributo para el nombre corto del viaje
	 */
	private String tripShortName;

	/**
	 * Atributo para el id de la direccion
	 */
	private int DirectionId;

	/**
	 * Atributo para el id de la cuadra
	 */
	private int BlockId;

	/**
	 * Atributo para la forma de la ruta
	 */
	private int shapeId;

	/**
	 * Atributo para el acceso de silla de rueda del viaje 
	 */
	private int WheelsChairAccs;

	/**
	 * Atributo para el acceso de bicicletas del viaje 
	 */
	private int bikesAllwd;

	/**
	 * fila de updates para este viaje
	 */


	private Double totalDelayTime;

	/**
	 * Crea un nuevo viaje con la informacion dada por parametro
	 * @param pRouteId id de la ruta del nuevo viaje 
	 * @param pServiceId  id de servicio del nuevo viaje 
	 * @param pTripId id del nuevo viaje 
	 * @param pTripHead afiche del nuevo viaje 
	 * @param pTripShort nombre corto del nuevo viaje 
	 * @param pDirectionId id de la direccion del nuevo viaje 
	 * @param pBlockId id de la cuadra en la que se encuentra el nuevo viaje 
	 * @param pShapeId id de la forma del  nuevo viaje 
	 * @param pWheels acceso de silla de ruedas del nuevo viaje 
	 * @param pBikes acceso de bicicletas del nuevo viaje 
	 */
	public VOTrip(int pRouteId, int pServiceId, int pTripId, String pTripHead, String pTripShort, int pDirectionId, int pBlockId, 
			int pShapeId, int pWheels, int pBikes){

		routeId = pRouteId;
		serviceId = pServiceId;
		TripId = pTripId;
		tripHeadSing = pTripHead;
		tripShortName = pTripShort;
		DirectionId = pDirectionId;
		BlockId = pBlockId;
		shapeId = pShapeId;
		WheelsChairAccs = pWheels;
		bikesAllwd= pBikes;
		totalDelayTime = 0.0;
	}

	/**
	 * @return si el viaje permite bicicletas
	 */
	public int getBikesAllwd(){
		return bikesAllwd;
	}

	/**
	 * @return si el viaje tiene acceso para silla de ruedas
	 */
	public int getWheelsChairAccs(){
		return WheelsChairAccs;
	}

	/**
	 * @return el id de la forma del viaje
	 */
	public int getShapeId(){
		return shapeId;
	}

	/**
	 * @return id de la cuadra 
	 */
	public int getBlockId(){
		return BlockId;
	}

	/**
	 * @return id de la direccion del viaje
	 */
	public  int getDirectionId(){
		return DirectionId;
	}
	/**
	 * @return el nombre corto del viaje
	 */
	public String getTripShortName(){
		return tripShortName;
	}

	/**
	 * @return afiche del viaje
	 */
	public String getTripHeadSign(){
		return tripHeadSing;
	}

	/**
	 * @return id del viaje
	 */
	public int getTripId(){
		return TripId;

	}
	/**
	 * @return id del servicio de la ruta
	 */
	public int getServiceId(){
		return serviceId;
	}

	/**
	 * @return id de la ruta del viaje 
	 */
	public int getRouteId(){
		return routeId;
	}

	public String toString()
	{
		return "TripId: "+ TripId;
	}


	@Override
	public int compareTo(VOTrip pTrip) {
		if( TripId > pTrip.getTripId() )
			return 1;
		else if( TripId < pTrip.getTripId() )
			return -1;
		else
			return 0;
	}
}
