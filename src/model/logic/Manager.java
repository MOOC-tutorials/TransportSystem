package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

import api.IList;
import api.IManager;
import model.data_structures.BFSPaths;
import model.data_structures.Bag;
import model.data_structures.Cycles;
import model.data_structures.DirectedEdge;
import model.data_structures.DirectedGraph;
import model.data_structures.DoubleLinkedList;
import model.data_structures.Edge;
import model.data_structures.EdgeWeightedGraph;
import model.data_structures.MST;
import model.data_structures.MaxPriorityQueue;
import model.data_structures.Parejas39;
import model.data_structures.Queue;
import model.data_structures.RedBlackBST;
import model.data_structures.SCC;
import model.data_structures.SeparateChainingHash;
import model.data_structures.Stack;
import model.data_structures.Vertex;
import model.exceptions.DateNotFoundExpection;
import model.vo.VO1;
import model.vo.VO31;
import model.vo.VO33;
import model.vo.VOCamino35;
import model.vo.VO36;
import model.vo.VOCalendar;
import model.vo.VOCalendarDate;
import model.vo.VOList;
import model.vo.VOParadasCiclo38;
import model.vo.VORoute;
import model.vo.VOStopTime;
import model.vo.VOStopTimeRuta;
import model.vo.VOTrip;

public class Manager implements IManager{

	/**
	 * Hash con los viajes, luego se usarï¿½ para crear el grafo
	 */
	private DoubleLinkedList<VOTrip> tripsDLL;

	private SeparateChainingHash<Integer, VOList<VOTrip>> tripsHash;


	/**
	 * Hash con las rutas , luego se usara para crear el grafo
	 */

	private SeparateChainingHash<Integer, VOList<VORoute>> routesHash;


	/**
	 * Hash con los tiempos de llegada, luego se usarï¿½ para crear el grafo
	 */
	private SeparateChainingHash<Integer, VOList<VOStopTime>> stopTimesHash;

	/**
	 * Ã¡rbol con los calendar dates 
	 */
	private RedBlackBST<Integer, VOCalendar> calendarBST;

	/**
	 * Grafo con las paradas. Id de cada parada en la llave y ï¿½rbol rojo-negro con todos los stopTimes con el arrival time como llave 
	 */
	private DirectedGraph<Integer, RedBlackBST<Integer, VOStopTimeRuta>> stopsGraph;

	/**
	 * Sub-
	 */
	private DirectedGraph<Integer, RedBlackBST<Integer, VOStopTimeRuta>> stopsSubGraph;

	private DirectedGraph<Integer, RedBlackBST<Integer, VOStopTimeRuta>> SubGraphPunto1;


	public void init() {
		stopTimesHash  = new SeparateChainingHash<>(1772387); 
		tripsDLL = new DoubleLinkedList<>();
		stopsGraph = new DirectedGraph<>(8759);
		routesHash = new SeparateChainingHash<>(55967);
		calendarBST = new RedBlackBST<>();
		stopsSubGraph = new DirectedGraph<>(8759);
		tripsHash = new SeparateChainingHash<Integer, VOList<VOTrip>>(55967);
		SubGraphPunto1 = new DirectedGraph<>(8759);
	}	

	/**
	 * Carga la informaciï¿½n a los hashes
	 */

	public void cargarGTFS() {
		cargarCalendar("./data/calendar.txt");
		cargarCalendarDates("./data/calendar_dates.txt");
		cargarViajes("./data/trips.txt");
		cargarStopTimes("./data/stop_times.txt");
		cargarRutas("./data/routes.txt");
		cargarViajes1("./data/trips.txt");
	}


	public void cargarViajes1( String pRuta ){
		try{
			BufferedReader br = new BufferedReader( new FileReader(pRuta));
			String ln = br.readLine();
			ln = br.readLine();
			while( ln != null){
				String[] line = ln.split(",");
				VOTrip toAdd = new VOTrip( Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], line[4], Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]), Integer.parseInt(line[8]), Integer.parseInt(line[9]));

				Integer hashedRouteId = new Integer(toAdd.getRouteId()).hashCode();
				if( tripsHash.contains(hashedRouteId) ){//Si el hash contiene el key
					Queue<VOList<VOTrip>> chain = tripsHash.getAll(hashedRouteId); //Cadena del hash con todos los elementos con la misma llave
					boolean added = false;
					for( VOList<VOTrip> voList : chain){//Itera sobre los elementos de la cadena
						if( voList.getId() == toAdd.getServiceId()){//Revisa si la lista con el tripId no tiene el elemento
							voList.addVO(toAdd);
							added = true;
							break;
						}
					}
					if( added == false ){
						VOList<VOTrip> newTripsWithRouteIdList = new VOList<VOTrip>(toAdd.getRouteId());
						newTripsWithRouteIdList.addVO(toAdd);
						tripsHash.put(hashedRouteId, newTripsWithRouteIdList);
					}
				}
				else{//Si el hash no contiene la llave
					VOList<VOTrip> newTripsWithRouteIdList = new VOList<VOTrip>(toAdd.getRouteId());
					newTripsWithRouteIdList.addVO(toAdd);
					tripsHash.put(hashedRouteId, newTripsWithRouteIdList);
				}
				ln = br.readLine();
			}
			br.close();
			System.out.println("Viajes cargados");
		} catch (IOException e){
			e.printStackTrace();
		}
	}


	public void cargarCalendar(String pRuta){
		try{
			BufferedReader br = new BufferedReader(new FileReader(pRuta));
			String ln = br.readLine();
			while( ln != null ){
				ln = br.readLine();
				if( ln != null ){
					String[] line = ln.split(",");
					VOCalendar toAdd = new VOCalendar(Integer.parseInt(line[0]), (line[1].equals("1"))? true:false, (line[2].equals("1"))? true:false, (line[3].equals("1"))? true:false, (line[4].equals("1"))? true:false, (line[5].equals("1"))? true:false, (line[6].equals("1"))? true:false, (line[7].equals("1"))? true:false, Integer.parseInt(line[8]), Integer.parseInt(line[9]));

					calendarBST.put(toAdd.getServiceId(), toAdd);
				}
			}
			br.close();
			System.out.println("Calendario cargado");
		}catch( IOException e){
			e.printStackTrace();
		}
	}

	public void cargarCalendarDates (String pRuta){
		try{
			BufferedReader br = new BufferedReader(new FileReader( pRuta ));
			String ln = br.readLine();
			while( ln != null ){
				ln = br.readLine();
				if( ln != null ){
					String[] line = ln.split(",");
					VOCalendarDate toAdd = new VOCalendarDate( Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]) );

					for (Integer key : calendarBST.keys()){
						VOCalendar currentCalendar = calendarBST.get(key);
						if (currentCalendar.getServiceId() == toAdd.getServiceId()){
							currentCalendar.addCalendarDates(toAdd);
						}
					}
				}
			}
			br.close();
			System.out.println("Fechas cargadas");
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */


	public void cargarRutas( String pRuta ){
		//		try{
		//			BufferedReader br = new BufferedReader( new FileReader(pRuta));
		//			String ln = br.readLine();
		//			ln = br.readLine();
		//			while( ln != null){
		//				String[] line = ln.split(",");
		//				VORoute toAdd = new VORoute( Integer.parseInt(line[0]),line[1], line[2], line[3], line[4], Integer.parseInt(line[5]), line[6], line[7], line[8], 0);
		//
		//				Integer hashedRouteId = new Integer(toAdd.getId()).hashCode();
		//				if( routesHash.contains(hashedRouteId) ){//Si el hash contiene el key
		//					Queue<VOList<VORoute>> chain = routesHash.getAll(hashedRouteId); //Cadena del hash con todos los elementos con la misma llave
		//					boolean added = false;
		//					for( VOList<VORoute> voList : chain){//Itera sobre los elementos de la cadena
		//						if( voList.getId() == toAdd.getId()){//Revisa si la lista con el tripId no tiene el elemento
		//							voList.addVO(toAdd);
		//							added = true;
		//							break;
		//						}
		//					}
		//					if( added == false ){
		//						VOList<VORoute> newTripsWithRouteIdList = new VOList<VORoute>(toAdd.getId());
		//						newTripsWithRouteIdList.addVO(toAdd);
		//						routesHash.put(hashedRouteId, newTripsWithRouteIdList);
		//					}
		//				}
		//				else{//Si el hash no contiene la llave
		//					VOList<VORoute> newTripsWithRouteIdList = new VOList<VORoute>(toAdd.getId());
		//					newTripsWithRouteIdList.addVO(toAdd);
		//					routesHash.put(hashedRouteId, newTripsWithRouteIdList);
		//				}
		//				ln = br.readLine();
		//			}
		//			br.close();
		//			System.out.println("rutas cargadas");
		//		} catch (IOException e){
		//			e.printStackTrace();
		//		}
	}

	/**
	 * Carga la informaciï¿½n de los viajes desde un archivo csv en una tabla de hash.
	 * @param pRuta ruta del archivo con la informaciï¿½n.
	 */
	public void cargarViajes( String pRuta ){
		try{
			BufferedReader br = new BufferedReader( new FileReader(pRuta));
			String ln = br.readLine();
			ln = br.readLine();
			while( ln != null){
				String[] line = ln.split(",");
				VOTrip toAdd = new VOTrip( Integer.parseInt(line[0]), Integer.parseInt(line[1]), Integer.parseInt(line[2]), line[3], line[4], Integer.parseInt(line[5]), Integer.parseInt(line[6]), Integer.parseInt(line[7]), Integer.parseInt(line[8]), Integer.parseInt(line[9]));

				tripsDLL.addAtEnd(toAdd);

				ln = br.readLine();
			}
			br.close();
			System.out.println("Viajes cargados");
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Carga la informaciï¿½n de los viajes desde un archivo csv una tabla de has (separate chainig).
	 * @param pRuta ruta del archivo con la informaciï¿½n.
	 */
	public void cargarStopTimes(String pRuta){
		try {
			BufferedReader br = new BufferedReader( new FileReader( pRuta ));
			String ln = br.readLine();
			ln = br.readLine();
			while( ln != null){
				String[] line = ln.split(",");
				VOStopTime toAdd = new VOStopTime(0, "", "", 0, 0, "", 0,0, 0.0);
				if (line.length == 8){
					toAdd = new VOStopTime (Integer.parseInt(line[0]), line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), line[5], Integer.parseInt(line[6]), Integer.parseInt(line[7]), 0.0);
				}
				else {
					toAdd = new VOStopTime( Integer.parseInt(line[0]), line[1], line[2], Integer.parseInt(line[3]), Integer.parseInt(line[4]), line[5], Integer.parseInt(line[6]), Integer.parseInt(line[7]), Double.parseDouble(line[8]));
				}
				Integer hashedTripId = new Integer(toAdd.getTripId()).hashCode();
				if(stopTimesHash.contains(hashedTripId) ){//Si el hash contiene el key
					Queue<VOList<VOStopTime>> chain = stopTimesHash.getAll(hashedTripId); //Cadena del hash con todos los elementos con la misma llave
					boolean added = false;
					for( VOList<VOStopTime> voList : chain){//Itera sobre los elementos de la cadena
						if( voList.getId() == toAdd.getTripId()){//Revisa si la lista con el tripId no tiene el elemento
							voList.addVO(toAdd);
							added = true;
							break;
						}
					}
					if( added == false ){
						VOList<VOStopTime> newStopTimesWithStopIdList = new VOList<VOStopTime>(toAdd.getTripId());
						newStopTimesWithStopIdList.addVO(toAdd);
						stopTimesHash.put(hashedTripId, newStopTimesWithStopIdList);
					}
				}
				else{//Si el hash no contiene la llave
					VOList<VOStopTime> newStopTimesWithStopIdList = new VOList<VOStopTime>(toAdd.getTripId());
					newStopTimesWithStopIdList.addVO(toAdd);
					stopTimesHash.put(hashedTripId, newStopTimesWithStopIdList);
				}
				ln = br.readLine();
			}
			br.close();
			System.out.println("Tiempos de parada cargados");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * Crea el grafo con el nï¿½mero de paradas indicado en el atributo.
	 */

	public void crearGrafo(){
		System.out.println("creando grafo");

		for( VOTrip currentTrip : tripsDLL ){ //Itera sobre los viajes
			for( VOList<VOStopTime> currentList : stopTimesHash.getAll(currentTrip.getTripId()) ){ //Itera sobre los stoptimes del viaje actual
				if( currentList.getId() == currentTrip.getTripId() ){
					//Cola de prioridad para los ejes, la prioridad es la secuencia de los stopTimes
					MaxPriorityQueue<VOStopTime> orderedBySequence = new MaxPriorityQueue<>(20); 
					for( VOStopTime currentStopTime : currentList.getVOList() ){ //Itera sobre los stopTimes
						orderedBySequence.insert(currentStopTime); //Agrega el stopTime actual a la pq
						//Crea un nuevo StopTimeRuta, que es igual que un stopTime, pero con el id de la ruta a la que pertenece el viaje
						//que este a su vez pertenece
						VOStopTimeRuta newST = new VOStopTimeRuta(currentStopTime, currentTrip.getRouteId()); 
						int hora = horaAInt(currentStopTime.getArrivalTime()); 
						//Convierte la hora del stoptime en un entero: 20:15 = 2015
						if( stopsGraph.containsVertex(currentStopTime.getStopId()) ){
							//Si el grafo ya tiene la parada, le agrega el stopTimeRuta a su ï¿½rbol de StopTimes
							stopsGraph.getVertex(currentStopTime.getStopId()).getValue().put(hora, newST);
						}
						else{
							//De lo contrario, crea un nuevo ï¿½rbol con la hora de llegada como key
							RedBlackBST<Integer, VOStopTimeRuta> bst = new RedBlackBST<>();
							bst.put(hora, newST); //Agrega el stopTime al ï¿½rbol
							stopsGraph.addVertex(currentStopTime.getStopId(), bst); //Y agrega el nuevo vï¿½rtice
						}
					}
					VOStopTime st1 = orderedBySequence.delMax(); //Primer tiempo de parada del viaje
					while( !orderedBySequence.isEmpty() ){ //Mientras la pq no estï¿½ vacï¿½a
						VOStopTime st2 = orderedBySequence.delMax(); //Siguiente tiempo de parada del viaje
						//Agrega el vï¿½rtice entre los tiempos de parada, si ya existe no se agrega
						try{
							stopsGraph.addEdge(st1.getStopId(), st2.getStopId(), currentTrip.getRouteId());
						}catch (IllegalStateException e){

						}
						st1 = st2; //Reemplaza el primer tiempo de parada por el segundo
					}
				}
			}
		}
		//		tripsDLL = new DoubleLinkedList<>();
		//		routesHash = new SeparateChainingHash<>();
		//		stopTimesHash = new SeparateChainingHash<>();
		//		calendarBST = new RedBlackBST<>();
		System.out.println("Vï¿½rtices: " + stopsGraph.V() + " Ejes: " + stopsGraph.E());
	}

	/**
	 * @param hora string de la hora
	 * @return hora como int
	 */
	private int horaAInt( String hora ){
		int ans = 0;
		String[] horaArr = hora.split(":");
		ans = Integer.parseInt(horaArr[0].trim())*100;
		ans += Integer.parseInt(horaArr[1].trim());
		if (ans >= 2500 && ans< 2600 ) {
			ans -= 2400;
		}
		else if (ans >= 2600 && ans < 2700)
			ans -= 2400;
		else if (ans >= 2700 && ans < 2800)
			ans -= 2400;
		else if (ans >= 2800 && ans < 2900)
			ans -= 2400;
		else if (ans >= 2900 && ans < 3000)
			ans -= 2400; 

		return ans;
	}


	/**
	 * Metodo 2, dar las componentes fuertemente conexas del grafo 
	 */
	@Override
	public Queue<Queue<Integer>> darSCCRedDeBuses() {

		SCC<Integer, RedBlackBST<Integer, VOStopTimeRuta>> scc = new SCC (stopsGraph);
		Queue<Queue<Integer>> answer = new Queue<>();

		for (int i =0; i < scc.count(); i ++) {

			MaxPriorityQueue<Integer > ids = new MaxPriorityQueue<>(1000);
			Queue <Integer> idsQueue = new Queue<>();


			for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex : stopsGraph.vertices()) {
				if (scc.id(vertex.getKey()) == i) {
					ids.insert(vertex.getKey());
				}


			}
			for (Integer idActual :ids) {
				idsQueue.enqueue(idActual);
			}
			answer.enqueue(idsQueue);
		}

		System.out.println(answer.getSize());
		System.out.println(scc.count());
		return answer;

	}



	/**
	 * Metodo para verificar si una fecha es valida
	 * @param pDate fecha que se va a busacar 
	 * @return
	 */
	public boolean dateValid (int pDate) {
		boolean answer = false;
		for (Integer key:calendarBST.keys()){
			VOCalendar currentCalendar = calendarBST.get(key);
			for (Integer key2:currentCalendar.getCalendarDates().keys()) {
				VOCalendarDate currentDate = currentCalendar.getCalendarDates().get(key2);
				if (currentDate.getDate() == pDate && !answer)
					answer = true;
			}
		}

		return answer;

	}


	public boolean estaEnFecha (VOCalendar pCalendar, int pFecha  ) {
		boolean answer = false;

		for (Integer datesKey:pCalendar.getCalendarDates().keys()) {
			VOCalendarDate currentDate = pCalendar.getCalendarDates().get(datesKey);
			if (currentDate.getDate()  == pFecha )
				answer = true;
		}
		return answer;
	}


	@Override


	/**
	 * Crea un subgrafo con las paradas alcanzables desde una parada de origen con todas sus paradas alcanzables dentro del rango de hora dado 
	 */
	public void construirSubGrafo(String pHoraInicio,
			String pHoraFin, String pStopId, String pDate) {

		int fecha = Integer.parseInt(pDate);
		System.out.println(fecha);

		int stopId = Integer.parseInt(pStopId);
		System.out.println(stopId);
		int horaInicio = horaAInt(pHoraInicio);
		System.out.println(horaInicio);
		int horaFin = horaAInt(pHoraFin);
		System.out.println(horaFin);


		if (dateValid(fecha) == true ) {

			Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> fromStopid = stopsGraph.getVertex(stopId);
			System.out.println(fromStopid.getKey());
			// crea paths con todos los caminos alcanzables desde la parada de origen 
			BFSPaths<Integer, RedBlackBST<Integer,VOStopTimeRuta>> paths = new BFSPaths<>(stopsGraph, fromStopid);
			for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex:stopsGraph.vertices()){
				// revisa si existe un camino desde el origen hasta el vertice 
				if (paths.hasPathTo(vertex) == true || vertex.getKey() == stopId) {
					//recupera el arbol con los StopTimes del vertice
					RedBlackBST<Integer, VOStopTimeRuta> bst = vertex.getValue();
					RedBlackBST<Integer, VOStopTimeRuta> bstToAdd = new RedBlackBST<>();
					//itera sobre los stopTimes del vertice actual 
					for ( Integer hora:bst.keys()){
						VOStopTimeRuta StopTimeActual = bst.get(hora);

						if (StopTimeActual != null) {
							Integer hashedId = new Integer(StopTimeActual.getRouteId()).hashCode();
							if (hashedId!= null) {
								VOList<VOTrip> currentList = tripsHash.get(hashedId);

								for (VOTrip actual :currentList.getVOList()) {

									// revisa si el stoptimeactual esta en la fecha 
									int serviceId = actual.getServiceId();
									VOCalendar calendar = calendarBST.get(serviceId);


									if (estaEnFecha(calendar, fecha) == true){// tiene que revisar todas las fecha primero antes de eliminarlo 

										int horaLLegada = hora;
										int horaSalida = horaAInt(StopTimeActual.getDepartureTime());
										// revisa que el stoptime este en el rango de horas dada , si no esta se elimina del arbol 
										if ((horaLLegada > horaInicio && horaSalida<= horaFin)  ) {
											// elimina el voStopActual si no esta en el rango 
											bstToAdd.put(hora, StopTimeActual);   
										}
									}



								}
							}
						}
					}

					// agrega los vertices que son alcanzables y estan en fecha y rango dado 
					if (bstToAdd.size() != 0){
						stopsSubGraph.addVertex(vertex.getKey(), bstToAdd);
					}
				}
			}
			for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex: stopsSubGraph.vertices()) {
				int key = vertex.getKey();
				for (DirectedEdge<Integer> edge: stopsGraph.adj(key)) {
					if (stopsSubGraph.containsVertex(edge.toV()) == true) {
						stopsSubGraph.addEdge(vertex.getKey(), edge.toV(), edge.weight());
					}
				}
			}

			System.out.println(stopsSubGraph.E());
			System.out.println(stopsSubGraph.V());

		}
		else {
			System.out.println("La fecha no es vaida ");
		}








		// TODO Auto-generated method stub

	}

	@Override
	/**
	 * Metodo 3,2
	 * Consultar el itinerario de llegada de buses a una parada en el subgrafo. La respuesta debe incluir las rutas, sus viajes y 
	 * el horario de cada viaje llegando a la parada. La información debe mostrarse ordenada ascendentemente por el tiempo de llegada.
	 * @param pStopid da la informacÃ³n del itinerario de llegada de una parada del subgrafo 
	 * @return
	 */
	public Queue<VOStopTimeRuta> darItinerarioLLegada(String pStopId) {
		int stopId = Integer.parseInt(pStopId);
		DoubleLinkedList<DirectedEdge<Integer>> edges = new DoubleLinkedList<>();
		Queue <VOStopTimeRuta> answer = new Queue<>();

		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>>vertex =  stopsSubGraph.getVertex(stopId);
		MaxPriorityQueue<VOStopTimeRuta> maxPq = new MaxPriorityQueue<>(vertex.getValue().size());
		for ( Integer key:vertex.getValue().keys()){
			VOStopTimeRuta actual = vertex.getValue().get(key);
			maxPq.insert(actual);
		}

		for (DirectedEdge<Integer> edge :stopsSubGraph.edges()) {
			if (edge.toV().equals(stopId)) {
				edges.addAtEnd(edge);
			}
		}
		for (int i = 0; i < maxPq.size(); i ++) {
			answer.enqueue(maxPq.delMax());
		}


		System.out.println(edges);

		return answer;
	}



	/**
	 * Metodo 3,4 Consultar la informacioÌ�n de la parada maÌ�s congestionada (aquella tal que tenga la mayor cantidad de buses llegando y/o saliendo).
	 * @return  la informacÃ³n de la parada mas congestionada 
	 */
	public DoubleLinkedList<VOStopTimeRuta> darInformacionParadaMasCongestionada() {
		DoubleLinkedList<VOStopTimeRuta> answer = new DoubleLinkedList<>();
		MaxPriorityQueue<VOStopTimeRuta> order = new MaxPriorityQueue<>(20);
		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> masCongestionado = stopsSubGraph.darVerticeMasCongestionado();

		for (Integer tripId :masCongestionado.getValue().keys()) {
			VOStopTimeRuta actual = masCongestionado.getValue().get(tripId);
			order.insert(actual);
		}

		for (int i = 0; i < order.size(); i ++) {
			answer.addAtEnd(order.delMax());
		}


		//System.out.println(stopsSubGraph.adj(masCongestionado.getKey()));

		return answer;
	}

	@Override
	public VO36 CaminoMenorTiempo(String idOrigen, String idDestino, String horaSalida ) {
		int idO = Integer.parseInt(idOrigen);
		int idD = Integer.parseInt(idDestino);
		int horaS = horaAInt(horaSalida);
		int tiemptotal = 0;
		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> origen = stopsSubGraph.getVertex( idO ); //Parada de origen
		Queue<Integer> horariosSalida = origen.getValue().valuesInRange(horaS, origen.getValue().max()); //Horarios de la parada de origen desde la hora de salida
		if( horariosSalida.isEmpty() ){ 
			//Lanza excepción si no hay horarios en el rango
			throw new NoSuchElementException( "No se han encontrado viajes a partir de la parada " + idOrigen + " después de la hora " + horaSalida );
		}
		else{
			Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> destino = stopsSubGraph.getVertex(idD); //Parada de destino
			BFSPaths<Integer, RedBlackBST<Integer, VOStopTimeRuta>> paths = new BFSPaths<>(stopsSubGraph, origen); //BFS sobre origen
			if( paths.hasPathTo(destino) ){ //Si tiene un camino hacia destino
				int horaSalidaReal = horariosSalida.dequeue(); //Menor hora posible después de horaSalida
				Queue<Integer> horariosLlegada = destino.getValue().valuesInRange(horaSalidaReal, destino.getValue().max()); //Horarios de destino desde hora de salida real
				VOStopTimeRuta stopTimeSalida = origen.getValue().get(horaSalidaReal); //StopTime con llave HoraSalidaReal
				int routeIdSalida = stopTimeSalida.getRouteId(); //Ruta de salida 
				int tripIdSalida = stopTimeSalida.getTripId(); //Viaje de salida
				Double peso = paths.edgeTo(destino).weight(); //Ruta de llegada
				int routeIdLlegada = peso.intValue(); 
				VOStopTimeRuta stopTimeLleagada = null; 
				for( Integer horaLlegadaActual : horariosLlegada ){ //Itera sobre los stopTimesRuta de la parada de llegada
					VOStopTimeRuta stopTimeActual = destino.getValue().get(horaLlegadaActual);
					if( stopTimeActual.getRouteId() == routeIdLlegada){ //Si el stopTimeActual tiene el routeId de llegada
						stopTimeLleagada = stopTimeActual; //Se guarda el valor
						break;
					}
				}
				if( stopTimeLleagada != null ){ //Si el stopTimeRuta de llegada no es null
					int tripIdLlegada = stopTimeLleagada.getTripId(); //TripId de llegada 
					for (DirectedEdge<Integer > edge:paths.pathTo(destino)) {


						RedBlackBST<Integer, VOStopTimeRuta> fromBst = stopsSubGraph.getVertex(edge.fromV()).getValue();
						RedBlackBST<Integer, VOStopTimeRuta> ToBst = stopsSubGraph.getVertex(edge.toV()).getValue();
						for (Integer keyFrom:fromBst.keys()){
							VOStopTimeRuta fromActual = fromBst.get(keyFrom);
							Double idFrom = new Double(fromActual.getRouteId());
							if (idFrom == edge.weight()) {
								for (Integer keyTo : ToBst.keys()){
									VOStopTimeRuta toActua = ToBst.get(keyTo);
									Double idTo = new Double(toActua.getRouteId());
									if (idTo == edge.weight()) {
										int tiempoFrom = (horaAInt(fromActual.getDepartureTime()) - horaAInt(fromActual.getArrivalTime())); 
										int tiempoTo = (horaAInt(toActua.getDepartureTime()) - horaAInt(toActua.getArrivalTime()));
										int tiempoRecorrido = (horaAInt(toActua.getArrivalTime()) - horaAInt(fromActual.getDepartureTime())); 
										tiemptotal += (tiempoFrom + tiempoTo + tiempoRecorrido);
									}
								}
							}
						}



					}
					VO36 ans = new VO36(paths.pathTo(destino), horaSalidaReal, routeIdSalida, routeIdLlegada, tripIdSalida, tripIdLlegada, tiemptotal); //Respuesta
					return ans;
				}
				else{ //Lanza execpción si el stopTime de llegaada es null (no debería ocurrir, lanza la exepción del camino antes)
					throw new NoSuchElementException( "No es posible llegar a la parada" );
				}
			}
			else{ //Lanza excepción si no hay camino desde origen a destino
				throw new NoSuchElementException( "No se ha encontrado un camino para llegar a la parada" );
			}
		}
	}

	@Override
	public VOParadasCiclo38 darInformacionParadasEnCicloMasGrande(String pHoraInicio) {
		Cycles<Integer, RedBlackBST<Integer, VOStopTimeRuta>> cycles = new  Cycles<>(stopsSubGraph); //Identifica todos los ciclos del grafo
		int hora = horaAInt(pHoraInicio);
		VOParadasCiclo38 ciclo = new VOParadasCiclo38(); //Crea el VO para la respuesta
		MaxPriorityQueue<Stack<Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>>>> longestCycles = cycles.longestCycles();  //Obtiene los ciclos

		while( !longestCycles.isEmpty() ){ //Si hay ciclos
			Stack<Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>>> cicloActual = longestCycles.delMax(); //Ciclo más grande
			if( cicloActual.peek().getElement().getValue().min() < hora ) //Comprueba la hora inicial
				for( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> currentStop : cicloActual){
					ciclo.agregarParada(currentStop); //Agrega las paradas del ciclo a la respuesta
				}
		}
		return ciclo;
	}


	/**
	 * Crea un subgrafo para el punto  con las paradas alcanzables desde una parada que estan en la fecha dada por parametro
	 * @param pStopId
	 * @param pDate
	 */
	public void crearSubGrafoPunto1 (String pStopId, String pDate) {
		int fecha = Integer.parseInt(pDate);
		System.out.println(fecha);

		int stopId = Integer.parseInt(pStopId);
		System.out.println(stopId);



		if (dateValid(fecha) == true ) {

			Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> fromStopid = stopsGraph.getVertex(stopId);
			System.out.println(fromStopid.getKey());
			// crea paths con todos los caminos alcanzables desde la parada de origen 
			BFSPaths<Integer, RedBlackBST<Integer,VOStopTimeRuta>> paths = new BFSPaths<>(stopsGraph, fromStopid);
			for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex:stopsGraph.vertices()){
				// revisa si existe un camino desde el origen hasta el vertice 
				if (paths.hasPathTo(vertex) == true || vertex.getKey() == stopId) {
					//recupera el arbol con los StopTimes del vertice
					RedBlackBST<Integer, VOStopTimeRuta> bst = vertex.getValue();
					// arbol que se va a agregar al vertice con los VOStopTimes en fecha 
					RedBlackBST<Integer, VOStopTimeRuta> bstToAdd = new RedBlackBST<>();
					//itera sobre los stopTimes del vertice actual 
					for ( Integer hora:bst.keys()){
						VOStopTimeRuta StopTimeActual = bst.get(hora);

						if (StopTimeActual != null) {
							Integer hashedId = new Integer(StopTimeActual.getRouteId()).hashCode();
							if (hashedId!= null) {
								VOList<VOTrip> currentList = tripsHash.get(hashedId);

								for (VOTrip actual :currentList.getVOList()) {

									// revisa si el stoptimeactual esta en la fecha 
									int serviceId = actual.getServiceId();
									VOCalendar calendar = calendarBST.get(serviceId);


									if (estaEnFecha(calendar, fecha) == true){// tiene que revisar todas las fecha primero antes de eliminarlo 


										// si esta en fecha lo agrega al arbol para agregar el vertice  


										bstToAdd.put(hora, StopTimeActual);   
									}
								}
							}
						}
					}

					// agrega los vertices que son alcanzables y estan en fecha y 
					if (bstToAdd.size() != 0){
						SubGraphPunto1.addVertex(vertex.getKey(), bstToAdd);
					}
				}
			}
			for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex: SubGraphPunto1.vertices()) {
				int key = vertex.getKey();
				for (DirectedEdge<Integer> edge: stopsGraph.adj(key)) {
					if (SubGraphPunto1.containsVertex(edge.toV()) == true) {
						SubGraphPunto1.addEdge(vertex.getKey(), edge.toV(), edge.weight());
					}
				}
			}

			System.out.println(SubGraphPunto1.E());
			System.out.println(SubGraphPunto1.V());

		}
		else {
			System.out.println("La fecha no es vaida ");
		}

	}

	@Override
	public Bag<VO1> paradasAlcanzables1( String stopId, String fecha ) {
		int intStopId = Integer.parseInt(stopId);
		Bag<VO1> paradasAlcanzables = new Bag<>();
		crearSubGrafoPunto1(stopId, fecha); //Crea subgrafo en la fecha
		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> source = SubGraphPunto1.getVertex(intStopId); 
		BFSPaths<Integer, RedBlackBST<Integer, VOStopTimeRuta>> paths = new BFSPaths<>(SubGraphPunto1, source); //Paths sobre subgrafo
		for( Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> currentStop : SubGraphPunto1.vertices() ){
			if( paths.hasPathTo(currentStop) ){ //Si hay un camino 
				double routeIdSalida = 0;
				boolean transbordo = true;
				Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> fromStop = SubGraphPunto1.getVertex(paths.edgeTo(currentStop).fromV());
				DirectedEdge<Integer> firstEdge = null;
				for( DirectedEdge<Integer> edge : paths.pathTo(currentStop)){ //Primer eje del camino
					firstEdge = edge;
					break;
				}
				for( DirectedEdge<Integer> currentEdge : SubGraphPunto1.adj(intStopId) ){ //Identifica la ruta de salida
					if( currentEdge.toV().equals(firstEdge.toV()))
						routeIdSalida = currentEdge.weight();
				}
				for( DirectedEdge<Integer> currentEdge : SubGraphPunto1.adj(fromStop.getKey())){ //Identifica si hubo transbordo
					if( currentEdge.weight() == routeIdSalida ){
						transbordo = false;
						break;
					}
				}
				paradasAlcanzables.addAtEnd(new VO1(fromStop.getKey(), transbordo)); //Agrega la parada a la respuesta
			}
		}
		return paradasAlcanzables;
	}

	@Override
	public Bag<Integer> camino1(String stopIdOrigen, String stopIdDestino){
		int intStopIdOrigen = Integer.parseInt(stopIdOrigen); //Id de la parada de origen
		int intStopIdDestino = Integer.parseInt(stopIdDestino);
		Bag<Integer> camino = new Bag<>();
		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> source = SubGraphPunto1.getVertex(intStopIdOrigen); //Parada de origen
		BFSPaths<Integer, RedBlackBST<Integer, VOStopTimeRuta>> paths = new BFSPaths<>(SubGraphPunto1, source); //Paths sobre el grafo
		for( DirectedEdge<Integer> ejeActual : paths.pathTo(SubGraphPunto1.getVertex(intStopIdDestino)) ){
			camino.addAtEnd(ejeActual.toV()); //Agrega los elementos del camino para llegar a la parada de destino a la respuesta 
		}
		return camino; //Retorna la secuencia de paradas
	}

	/**
	 * Consultar las paradas que definen el subgrafo. Por cada parada mostrar su identificador y las rutas que tienen viajes a la parada.
	 */
	@Override
	public Queue<VO31> consultarParadas31() {
		Queue<VO31> answer = new Queue<>();
		for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex :stopsSubGraph.vertices()) {
			VO31 toAdd = new VO31(vertex);
			answer.enqueue(toAdd);

		}
		return answer;
	}

	@Override
	public Queue<VO33> itinerarioSalida33(String pStopId) {
		int stopId = Integer.parseInt(pStopId);
		Queue <VO33> answer = new Queue<>();

		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>>vertex =  stopsSubGraph.getVertex(stopId);
		MaxPriorityQueue<VO33> maxPq = new MaxPriorityQueue<>(vertex.getValue().size());
		for ( Integer key:vertex.getValue().keys()){
			VOStopTimeRuta actual = vertex.getValue().get(key);
			VO33 toAdd = new VO33(actual);
			maxPq.insert(toAdd);
		}


		for (int i = 0; i < maxPq.size(); i ++) {
			answer.enqueue(maxPq.delMax());
		}

		for (VO33 actual :answer) {
			System.out.println(actual.getRouteId());
		}


		return answer;
	}

	@Override
	public VOCamino35 caminoMenorLongitud35(String idOrigen, String idDestino, String horaSalida) throws NoSuchElementException{
		int idO = Integer.parseInt(idOrigen);
		int idD = Integer.parseInt(idDestino);
		int horaS = horaAInt(horaSalida);
		Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> origen = stopsSubGraph.getVertex( idO ); //Parada de origen
		Queue<Integer> horariosSalida = origen.getValue().valuesInRange(horaS, origen.getValue().max()); //Horarios de la parada de origen desde la hora de salida
		if( horariosSalida.isEmpty() ){ 
			//Lanza excepción si no hay horarios en el rango
			throw new NoSuchElementException( "No se han encontrado viajes a partir de la parada " + idOrigen + " después de la hora " + horaSalida );
		}
		else{
			Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> destino = stopsSubGraph.getVertex(idD); //Parada de destino
			BFSPaths<Integer, RedBlackBST<Integer, VOStopTimeRuta>> paths = new BFSPaths<>(stopsSubGraph, origen); //BFS sobre origen
			if( paths.hasPathTo(destino) ){ //Si tiene un camino hacia destino
				int horaSalidaReal = horariosSalida.dequeue(); //Menor hora posible después de horaSalida
				Queue<Integer> horariosLlegada = destino.getValue().valuesInRange(horaSalidaReal, destino.getValue().max()); //Horarios de destino desde hora de salida real
				VOStopTimeRuta stopTimeSalida = origen.getValue().get(horaSalidaReal); //StopTime con llave HoraSalidaReal
				int routeIdSalida = stopTimeSalida.getRouteId(); //Ruta de salida 
				int tripIdSalida = stopTimeSalida.getTripId(); //Viaje de salida
				Double peso = paths.edgeTo(destino).weight(); //Ruta de llegada
				int routeIdLlegada = peso.intValue(); 
				VOStopTimeRuta stopTimeLleagada = null; 
				for( Integer horaLlegadaActual : horariosLlegada ){ //Itera sobre los stopTimesRuta de la parada de llegada
					VOStopTimeRuta stopTimeActual = destino.getValue().get(horaLlegadaActual);
					if( stopTimeActual.getRouteId() == routeIdLlegada){ //Si el stopTimeActual tiene el routeId de llegada
						stopTimeLleagada = stopTimeActual; //Se guarda el valor
						break;
					}
				}
				if( stopTimeLleagada != null ){ //Si el stopTimeRuta de llegada no es null
					int tripIdLlegada = stopTimeLleagada.getTripId(); //TripId de llegada 

					VOCamino35 ans = new VOCamino35(paths.pathTo(destino), horaSalidaReal, routeIdSalida, routeIdLlegada, tripIdSalida, tripIdLlegada); //Respuesta
					return ans;
				}
				else{ //Lanza execpción si el stopTime de llegaada es null (no debería ocurrir, lanza la exepción del camino antes)
					throw new NoSuchElementException( "No es posible llegar a la parada" );
				}
			}
			else{ //Lanza excepción si no hay camino desde origen a destino
				throw new NoSuchElementException( "No se ha encontrado un camino para llegar a la parada" );
			}
		}
	}

	@Override
	public Queue<Integer> scc37() {


		SCC<Integer, RedBlackBST<Integer, VOStopTimeRuta>> scc = new SCC<>(stopsSubGraph);
		Queue<Queue<Integer>> sccQueue = new Queue<>();

		for (int i =0; i < scc.count(); i ++) {

			Queue<Integer> ids = new Queue<>();


			for (Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> vertex : stopsSubGraph.vertices()) {
				if (scc.id(vertex.getKey()) == i) {
					ids.enqueue(vertex.getKey());
				}
			}
			sccQueue.enqueue(ids);
		}

		Queue<Integer> mayor = sccQueue.peek();
		int mayorTamaño = mayor.getSize();
		for ( Queue<Integer> currentQueue:  sccQueue) {
			if (currentQueue.getSize() > mayorTamaño) {
				mayor = currentQueue;
				mayorTamaño = currentQueue.getSize();
			}

		}
		return mayor;
	}

	@Override
	public MST<Integer, Integer> mst39( String horaInicio ) {
		int horaInicial = horaAInt(horaInicio);
		Parejas39<Integer, RedBlackBST<Integer, VOStopTimeRuta>> parejas = new Parejas39<>(stopsSubGraph); //Encuentra las parejas de vértices tales que se puede ir de uno al otro y volver
		EdgeWeightedGraph<Integer, Integer> graph = new EdgeWeightedGraph<>(parejas.contador()); //Crea el grafo no dirido

		for( Bag<Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>>> parejaActual : parejas.parejas() ){ //Recorre las parejas de vértices
			int count = 0; //Cuenta de tiempos
			double time = 0; //Suma de tiempos
			Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> v1 = parejaActual.getFirst(); //Prime vértice
			for( Integer key : v1.getValue().keys() ){
				if( key >= horaInicial){ //Comprueba la hora de la llave
					VOStopTimeRuta currentStoptime = v1.getValue().get(key); //StopTime de la primera parada
					Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> v2 = parejaActual.getLast(); //Segunda parada
					for(  Integer key2 : v2.getValue().keys() ){
						VOStopTimeRuta stopTime2 = v2.getValue().get(key2); //StopTime actual de la segunda parada
						if( stopTime2.getTripId() == currentStoptime.getTripId() ){ //Comprueba que los stopTimes sean del mismo viaje
							time = (horaAInt(stopTime2.getArrivalTime()) - horaAInt(currentStoptime.getDepartureTime())); //Resta el tiempo
							count++; //Aumenta el contador
						}
					}
				}
			}

			//Lo mismo de arriba pero en el otro setido
			v1 = parejaActual.getLast();
			for( Integer key : v1.getValue().keys() ){
				if( key >= horaInicial ){
					VOStopTimeRuta currentStoptime = v1.getValue().get(key);
					Vertex<Integer, RedBlackBST<Integer, VOStopTimeRuta>> v2 = parejaActual.getFirst();
					for(  Integer key2 : v2.getValue().keys() ){
						VOStopTimeRuta stopTime2 = v2.getValue().get(key2); 
						if( stopTime2.getTripId() == currentStoptime.getTripId() ){
							time = (horaAInt(stopTime2.getArrivalTime()) - horaAInt(currentStoptime.getDepartureTime()));
							count++;
						}
					}
				}
			}
	
			time = (time)/count; //Promedio del tiempo 
			int id1 = parejaActual.getFirst().getKey(); //StopId de la primera parada
			int id2 = parejaActual.getLast().getKey(); //StopId de la segunda parada
			graph.addVertex(id1, id1); //Agrega la primera parada (si ya está, reemplaza el valor lo cual no afecta al grafo)
			graph.addVertex(id2, id2); //Agrega la segunda parada
			try{
				graph.addEdge(id1, id2, time); //Agrega el vértice si no ha sido agregado anteriormente
			}
			catch(IllegalStateException e){

			}
		}
		return new MST<>(graph);
	}
}