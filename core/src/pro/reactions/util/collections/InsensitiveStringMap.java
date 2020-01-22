package pro.reactions.util.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

// Maps are using Object as argument for keys, so, no implementation for you, Map
public class InsensitiveStringMap<V> /*implements Map<String, V>*/{
	private static final InsensitiveStringMap EMPTY_MAP = new InsensitiveStringMap();

	private final boolean upperCase;
	private final Map<String, V> origin;

	private InsensitiveStringMap() {
		this.upperCase = false;
		this.origin = Collections.emptyMap();
	}

	public InsensitiveStringMap(boolean upperCase) {
		this.upperCase = upperCase;
		this.origin = new HashMap<>();
	}

	public InsensitiveStringMap(boolean upperCase, Map<String, V> origin) {
		this.upperCase = upperCase;
		this.origin = new HashMap<>();
		putAll(origin);
	}

	public InsensitiveStringMap(boolean upperCase, InsensitiveStringMap<V> origin) {
		this.upperCase = upperCase;
		this.origin = new HashMap<>();
		putAll(origin);
	}

	public V get(String key) {
		key = keyCase(key);
		return origin.get(key);
	}

	public V getCased(String key) {
		return origin.get(key);
	}

	public V getOrDefault(String key, V def) {
		key = keyCase(key);
		return origin.getOrDefault(key, def);
	}

	public V put(String key, V value) {
		key = keyCase(key);
		return origin.put(key, value);
	}

	public V putIfAbsent(String key, V value) {
		key = keyCase(key);
		return origin.putIfAbsent(key, value);
	}

	public void putAll(InsensitiveStringMap<V> map) {
		if(map.upperCase && upperCase)
			origin.putAll(map.origin);
		else {
			map.origin.forEach(upperCase ?
					(k, v) -> origin.put(k.toUpperCase(), v) :
					(k, v) -> origin.put(k.toLowerCase(), v)
			);
		}
	}

	public void putAll(Map<String, V> map) {
		map.forEach(upperCase ?
				(k, v) -> origin.put(k.toUpperCase(), v) :
				(k, v) -> origin.put(k.toLowerCase(), v)
		);
	}

	public boolean containsKey(String key) {
		key = keyCase(key);
		return origin.containsKey(key);
	}

	public boolean containsAnyKey(String... keys) {
		for(String key : keys)
			if(containsKey(key)) return true;
		return false;
	}

	public boolean containsEveryKey(String... keys) {
		for(String key : keys)
			if(!containsKey(key)) return false;
		return true;
	}

	public V remove(String key) {
		key = keyCase(key);
		return origin.remove(key);
	}

	public boolean isEmpty() {
		return origin.isEmpty();
	}

	public void clear() {
		origin.clear();
	}

	public Collection<V> values() {
		return origin.values();
	}

	public Set<String> keySet() {
		return origin.keySet();
	}

	private String keyCase(String s) {
		return upperCase ? s.toUpperCase() : s.toLowerCase();
	}

	public Map<String, V> map() {
		return origin;
	}

	public void forEach(BiConsumer<String, V> consumer) {
		origin.forEach(consumer);
	}

	@SuppressWarnings("unchecked")
	public static <V> InsensitiveStringMap<V> emptyMap() {
		return EMPTY_MAP;
	}
}
