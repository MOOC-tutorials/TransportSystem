package view;



import java.util.NoSuchElementException;
import java.util.Scanner;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import Controller.Controller;
import model.data_structures.Bag;
import model.data_structures.DoubleLinkedList;
import model.data_structures.MST;
import model.data_structures.Queue;
import model.exceptions.DateNotFoundExpection;
import model.vo.VO1;
import model.vo.VO31;
import model.vo.VO33;
import model.vo.VOCamino35;
import model.vo.VOStopTime;
import model.vo.VOStopTimeRuta;



public class View {
	/**
	 * Main
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		boolean fin = false;
		while (!fin) {
			printMenu();

			int option = sc.nextInt();

			switch (option) {
			// Cargar
			case 1:

				// Memoria y tiempo
				long memoryBeforeCase1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				long startTime = System.nanoTime();
				
				// Cargar data
					Controller.cargarGTFS();


				// Tiempo en cargar
				long endTime = System.nanoTime();
				long duration = (endTime - startTime) / (1000000);

				// Memoria usada
				long memoryAfterCase1 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				System.out.println("Tiempo en cargar: " + duration + " milisegundos \nMemoria utilizada:  "
						+ ((memoryAfterCase1 - memoryBeforeCase1) / 1000000.0) + " MB");

				break;

			// 1
			case 2:
				// Parada
				System.out.println("Ingrese el id de la parada:");
				String stopId = sc.next();
				// Fecha deseada
				System.out.println(
						"Ingrese la fecha deseada Ej: 20170625 (AnoMesDia) \n Esta fecha se utilizara para los otros metodos.");
				String fechaCase2 = sc.next();

				System.out.println( Controller.paradasAlcanzables1(stopId, fechaCase2));
				System.out.println("Desea conocer un camino? (S/N)");
				String rta = sc.next();
				while(rta.toUpperCase().equals("S")) {
					System.out.println("Conocer camino para llegar a una parada. Ingrese la parada ");
					String stopId2 = sc.next();
					System.out.println(Controller.camino1(stopId, stopId2));
					

				}
				
				break;
			// 2
			case 3:

       			System.out.println(Controller.darSCCRedDeBuses());
         		
				
				break;

			// 3
			case 4:
				System.out.println("Ingrese el id de la parada origen");
				String stopId4 = sc.next();
				System.out.println("Ingrese la hora de Inicio en formato HH:MM");
				String horaInicio = sc.next();
				System.out.println("Ingrese la hora de fin en formato HH:MM");
				String horaFin = sc.next();
				System.out.println("Ingrese la fecha");
				String fecha4 = sc.next();
               	Controller.construirSubGrafo( horaInicio, horaFin,stopId4, fecha4);
				break;

//3,1
			case 5:
				Queue<VO31> paradas = Controller.consultarParadas31();
				for (VO31 voParada : paradas) {
					System.out.println(voParada);
				}
				break;

//3,2
			case 6:
				System.out.println("Ingrese el id de la parada a consultar");
				String stopId6 = sc.next();
				Queue<VOStopTimeRuta> llegadas = Controller.darItinerarioLLegada(stopId6);
				for (VOStopTimeRuta voStopTime : llegadas) {
					System.out.println("Información de la parada");
					System.out.println(voStopTime);
				}
				break;

//3,3
			case 7:
				System.out.println("Ingrese el id de la parada a consultar");
				String stopId7 = sc.next();
				Queue<VO33> salidas = Controller.itinerarioSalida33(stopId7);
				for (VO33 voStopTime : salidas) {
					System.out.println("Información de la parada");
					System.out.println(voStopTime);
				}
				break;

//3,4
			case 8:
				DoubleLinkedList<VOStopTimeRuta> paradaCongestionada = Controller.darInformacionParadaMasCongestionada();
           		System.out.println(paradaCongestionada);
				break;

//3,5
			case 9:
				System.out.println("Ingrese el id de la parada de origen");
				String paradaO = sc.next();
				
				System.out.println("Ingrese el id de la parada de destino");
				String paradaDestino = sc.next();
				
				System.out.println("Ingrese la hora en formato HH:MM");
				String hora = sc.next();
				
				System.out.println(Controller.caminoMenorLongitud35(paradaO, paradaDestino, hora));

				break;

			// 3.6
			case 10:
				System.out.println("Ingrese el id de la parada de origen");
				String paradaOrigen = sc.next();
				
				System.out.println("Ingrese el id de la parada de destino");
				String paradaD = sc.next();
				
				System.out.println("Ingrese la hora");
				String hora34 = sc.next();
				System.out.println(Controller.CaminoMenorTiempo(paradaOrigen, paradaD, hora34));
				
//				
//				}
				break;
			// 3.7
			case 11:
				
					System.out.println(Controller.scc37());
				

				break;

			// 3.8
			case 12:
				System.out.println("Ingrese la hora de inicio para la b�squeda en formaro HH:MM");
				String horaInicio12 = sc.next();
				
				System.out.println(Controller.darInformacionParadasEnCicloMasGrande(horaInicio12));
				break;

            // 3.9
			case 13:
				System.out.println("Ingrese la hora de inicio para la b�squeda");
				String horaInicio13	 = sc.next();
				MST<Integer, Integer> arbol = Controller.mst39(horaInicio13);
				System.out.println(arbol.edges());
				System.out.println(arbol.weight());
				break;
			// SALIR
			case 14:

				fin = true;
				sc.close();
				break;

			}
		}
	}

	private static void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Proyecto 3----------------------");
		System.out.println("1. Cargar la informaci�n est�tica necesaria para la operaci�n del sistema");
		System.out.println("2. Dar las paradas alcanzables desde una parada (1)");
		System.out.println(
				"3. Dar componentes fuertemente conexas en el grafo de paradas (2)");
		System.out.println(
				"4. Crear un subgrafo a partir de una fecha, parada origen y rango de hora dadas (3)");
		System.out.println(
				"5. Dar las paradas del subgrafo(3,1)");
		System.out.println("6. Dar el itinerario de llegada de una parada(3,2)");
		System.out.println(
				"7.Dar el itinerario de salida de una parada(3,3)");
		System.out.println(
				"8. Dar la parada m�s congestionada (con m�s viajes de llegada y de salida) del subgrafo(3,4)");
		System.out.println(
				"9. Dar el camino m�s corto (de menor distancia) entre dos paradas(3,5)");
		System.out.println(
				"10. Dar el camino de menor tiempo entre dos paradas(3,6)");
		System.out.println(
				"11. Dar la mayor componente conexa del subgrafo(3,7)");
		System.out.println("12. Dar un ciclo simple del subgrafo(3,8)");
		System.out.println(
				"13. Dar el �rbol de recubrimiento m�nimo del grafo simplicado(3,9)");
		System.out.println("14. Salir.\n");
		System.out.println("Ingrese la opci�n deseada y luego presione enter: (e.g., 1):");

	}
}
