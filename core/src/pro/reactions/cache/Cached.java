package pro.reactions.cache;

public interface Cached<T> {
	T getCached(String key);

	boolean cache(String key, T t);

	void update();

	long updateTime();
}
