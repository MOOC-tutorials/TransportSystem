package model.vo;

import api.IList;
import model.data_structures.DoubleLinkedList;

/**
 * Representation of a route object
 */
public class VORoute implements Comparable<VORoute> {

	/**
	 * Atributo para el ID de la rta
	 */
	private int routeId;

	/**
	 * Atributo para el Id de la agencia
	 */
	private String agencyId;

	/**
	 * Atributo para el nombre corto de la ruta
	 */
	private String shortName;

	/**
	 * Atributo para el nombre largo de la ruta
	 */
	private String longName;

	/**
	 * Atributo para la descripcion de la ruta
	 */
	private String routeDesc;

	/**
	 * Atributo para el tipo de ruta
	 */
	private int routeType;

	/**
	 * Atributo para el url de la ruta
	 */
	private String routeUrl;

	/**
	 * Atributo para el color de la ruta
	 */
	private String routeColor;

	/**
	 * Atributo para el color de texto de la rta
	 */
	private String routeTextColor;


	private int totalParadas;





	/**
	 * Crea una nueva ruta con la informacion dada por parametro
	 * @param pRouteId Id de la nueva ruta
	 * @param pAgencyId Id de la agencia de la nueva ruta
	 * @param pShortName nombre corto de la nueva ruta
	 * @param pLongName nombre largo de la nueva ruta
	 * @param pRouteDesc descripcion de la nueva ruta
	 * @param pRouteType tipo de la nueva ruta
	 * @param pRouteUrl url de la nueva ruta
	 * @param pRouteColor color de la nueva ruta
	 * @param pRouteTextColor color de textode la nueva ruta
	 */
	public VORoute( int pRouteId, String pAgencyId, String pShortName, String pLongName, String pRouteDesc, int pRouteType, String pRouteUrl, String pRouteColor, String pRouteTextColor, int pParadas){
		routeId = pRouteId;
		agencyId = pAgencyId;
		shortName = pShortName;
		longName = pLongName;
		routeDesc = pRouteDesc;
		routeType = pRouteType;
		routeUrl = pRouteUrl;
		routeColor = pRouteColor;
		routeTextColor = pRouteTextColor;
		totalParadas = pParadas;
	}




	/**
	 * @return id - Route's id number
	 */
	public int getId() {
		return routeId;
	}

	/**
	 * @return id de la agencia de la ruta
	 */
	public String getAgencyId(){
		return agencyId;
	}

	/**
	 * @return name - route name
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @return el nomre largo de la ruta
	 */
	public String getLongName(){
		return longName;
	}

	/**
	 * @return descripcion de la ruta
	 */
	public String getDesc(){
		return routeDesc;
	}

	/**
	 * @return Tipo de la ruta
	 */
	public int getRouteType(){
		return routeType;
	}

	/**
	 * @return url de la ruta
	 */
	public String getRouteUrl(){
		return routeUrl;
	}

	/**
	 * @return color de la ruta
	 */
	public String getRoutecolor(){
		return routeColor;
	}

	/**
	 * @return color de texto de la ruta
	 */
	public String getRouteTextColor(){
		return routeTextColor;
	}
	
	


	public String toString(){
		return routeId + "";
	}

	/**
	 * @return el numero total de paradas de esta ruta 
	 */
	public int getTotalStops (){
		return totalParadas;
	}

//	/**
//	 * Calcula el numero total de paradas para esta ruta 
//	 */
//	public void addStop(){
//		IList<VOTrip> routeTrips = getTrips(); //trips de esta ruta 
//		DoubleLinkedNode <VOTrip> currentTrip = routeTrips.getFirst();
//		DoubleLinkedList<VOStopTime> tripsStopTime = new DoubleLinkedList<>(); 
//		while (currentTrip!= null){
//			DoubleLinkedNode <VOStopTime> currentStopTime = currentTrip.getData().getStopTimes().getFirst(); //Stop Time de los trips
//			while (currentStopTime!= null){
//				tripsStopTime.addAtEnd(currentStopTime.getData()); // agrega todos los stopTimes de los trips a una lista 
//				currentStopTime = currentStopTime.getNext();
//			}
//
//			currentTrip = currentTrip.getNext();
//		}
//		if (tripsStopTime!= null){
//			DoubleLinkedNode <VOStopTime> currentStopTime = tripsStopTime.getFirst();
//			while (currentStopTime!= null){
//				if (currentStopTime.getData().getStop() != null) // si el currentStopTime tiene una parada , aumenta en uno el numero total de paradas 
//					totalParadas ++;
//
//				currentStopTime = currentStopTime.getNext();
//			}
//		}
//	}
//	
//	public int numeroRetrasos(){
//		int ans = 0;
//		DoubleLinkedNode<VOService> currentService = services.getFirst();
//		while( currentService != null ){
//			ans += currentService.getData().numeroRetrasos();
//			currentService = currentService.getNext();
//		}
//		return ans;
//	}

	@Override
	public int compareTo(VORoute pRoute) {
		if( routeId > pRoute.routeId )
			return 1;
		else if( routeId < pRoute.routeId )
			return -1;
		else
			return 0;
	}
}
