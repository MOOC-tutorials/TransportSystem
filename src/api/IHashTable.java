package api;



public interface IHashTable<Key, Value> {
	public int size();
	public boolean isEmpty();
	public boolean contains(Key key);
	public void put(Key key, Value val);
	public Value get( Key key );
	public void delete( Key key );
	public Iterable<Key> keys();
	
}
