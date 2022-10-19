package model.vo;

/**
 * Excepciones para los servicios.
 */
public class VOCalendarDate implements Comparable<VOCalendarDate> {
	/**
	 * Id del servicio.
	 */
	public int serviceId;

	/**
	 * Fecha
	 */
	public int date;

	/**
	 * Tipo de excepción en el servicio.
	 */
	public int exceptionType;

	/**
	 * Crea una nueva fecha con 
	 * @param pServiceId
	 * @param pDate
	 * @param pExceptionType
	 */
	public VOCalendarDate( int pServiceId, int pDate, int pExceptionType){
		serviceId = pServiceId;
		date = pDate;
		exceptionType = pExceptionType;
	}

	public int getServiceId(){
		return serviceId;
	}

	public int getDate(){
		return date;
	}

	public int getExceptionType(){
		return exceptionType;
	}
	
	public String toString(){
		return date+"";
	}

	@Override
	public int compareTo(VOCalendarDate pVOCalendarDate) {

		if( serviceId > pVOCalendarDate.getServiceId() )
			return 1;
		else if( serviceId < pVOCalendarDate.getServiceId())
			return -1;
		else
			return 0;
	}
}
