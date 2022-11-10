package model.vo;



/**
 * Información de las transferencias entre rutas
 */
public class VOTransfer implements Comparable<VOTransfer> {
	/**
	 * Id de la parada donde empieza la transferencia.
	 */
	private int fromStopId;

	/**
	 * Id de la parada donde termina la transferencia.
	 */
	private int toStopId;

	/**
	 * Tipo de transferencia
	 */
	private int transferType;

	/**
	 * Tiempo minimo para hacer la transferencia.
	 */
	private int minTransferTime; 
	
	

	public VOTransfer(  int pTansferType, int pMintransferTime ){

		transferType = pTansferType;
		minTransferTime = pMintransferTime;
		
	}

	public int getFromStopId(){
		return fromStopId;
	}

	public int getToStopId(){
		return toStopId;
	}

	public int getTransferType(){
		return transferType;
	}

	public int getMinTransferTime(){
		return minTransferTime;
	}
	
	
	

	@Override
	public int compareTo(VOTransfer pTransfer) {
		return 0;
	}
}
