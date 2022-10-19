package api;

public interface IQueue<E> extends Iterable<E>{

	public void enqueue(E pElement);

	public E dequeue();
	
	

}
