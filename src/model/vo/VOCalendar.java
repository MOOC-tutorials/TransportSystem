package model.vo;


import model.data_structures.DoubleLinkedList;
import model.data_structures.RedBlackBST;

/**
 * Clase con la información de las fechas en las que se presta un servicio
 */
public class VOCalendar implements Comparable<VOCalendar>{

	/**
	 * Id del servicio
	 */
	private int serviceId;

	/**
	 * Boolean que indica si hay servicio los lunes. 
	 */
	private boolean monday;

	/**
	 * Boolean que indica si hay servicio los martes. 
	 */
	private boolean tuesday;

	/**
	 * Boolean que indica si hay servicio los miércoles. 
	 */
	private boolean wednesday;

	/**
	 * Boolean que indica si hay servicio los jueves. 
	 */
	private boolean thursday;

	/**
	 * Boolean que indica si hay servicio los viernes.
	 * 
	 */
	private boolean friday;

	/**
	 * Boolean que indica si hay servicio los sábados. 
	 */
	private boolean saturday;


	/**
	 * Boolean que indica si hay servicio los domingos. 
	 */
	private boolean sunday;

	/**
	 * Fecha en la que comienza el servicio.
	 */
	private int startDate;

	/**
	 * Fecha en la que termina el servicio.
	 */
	private int endDate;

	/**
	 * Información acerca de cada fecha.
	 */
	private RedBlackBST<Integer, VOCalendarDate> calendarDatesBST;
	
	private DoubleLinkedList<Integer> routeIdsInCalendar;

	public VOCalendar( int pServiceId, boolean pMonday, boolean pTuesday, boolean pWednesday, boolean pThursday, boolean pFriday, boolean pSaturday, boolean pSunday, int pStartDate, int pEndDate ){
		serviceId = pServiceId;
		monday = pMonday;
		tuesday = pTuesday;
		wednesday = pWednesday;
		thursday = pThursday;
		friday = pFriday;
		saturday = pSaturday;
		sunday = pSunday;
		startDate = pStartDate;
		endDate = pEndDate;
		calendarDatesBST = new RedBlackBST<Integer, VOCalendarDate>();
		routeIdsInCalendar = new DoubleLinkedList<>();
	}
	
	public void addRouteId( int routeId ){
		routeIdsInCalendar.addAtEnd(routeId);
	}
	
	public DoubleLinkedList<Integer> getRouteIds(){
		return routeIdsInCalendar;
	}

	public void addCalendarDates( VOCalendarDate pToAdd){
		calendarDatesBST.put(pToAdd.getDate(), pToAdd);
	}

	public int getServiceId(){
		return serviceId;
	}

	public boolean getMonday(){
		return monday;
	}

	public boolean getTuesday(){
		return tuesday;
	}

	public boolean getWednesday(){
		return wednesday;
	}

	public boolean getThursday(){
		return thursday;
	}

	public boolean getFriday(){
		return friday;
	}

	public boolean getSaturday(){
		return saturday;
	}

	public boolean getSunday(){
		return sunday;
	}

	public int getStartDate(){
		return startDate;
	}

	public int getEndDate(){
		return endDate;
	}


	public RedBlackBST<Integer, VOCalendarDate> getCalendarDates(){
		return calendarDatesBST;
	}

	public String toString(){
		return "ServiceId: " + serviceId + " Dates: " + calendarDatesBST;
	}


	@Override
	public int compareTo(VOCalendar pVOCalendar) {
		if( serviceId > pVOCalendar.getServiceId() )
			return 1;
		else if( serviceId < pVOCalendar.getServiceId())
			return -1;
		else
			return 0;

	}
}
