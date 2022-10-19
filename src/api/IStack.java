package api;

public interface IStack<E> extends Iterable<E> {

	public void push (E item);

	public E pop();
}
