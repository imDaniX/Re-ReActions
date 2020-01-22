package pro.reactions.util;

/**
 * Kind of objects that can be precompiled from string
 * In ReA it means that no placeholders will be processed and value of action will be parsed only once - here
 */
public interface Precompilable<T> {
	/**
	 * Precompile object from string
	 * @param value Value of object
	 * @return Precompiled version of object
	 */
	T precompile(String value);
}
