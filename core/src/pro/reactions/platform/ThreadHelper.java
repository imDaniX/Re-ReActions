package pro.reactions.platform;

public interface ThreadHelper {
	/**
	 * Execute runnable in async thread
	 * @param runnable Runnable to run
	 */
	void runAsync(Runnable runnable);

	/**
	 * Execute runnable in async thread
	 * @param wait Units to wait before start
	 * @param runnable Runnable to run
	 */
	void runAsync(long wait, Runnable runnable);

	/**
	 * Execute runnable in async thread repeatedly forever
	 * @param wait Units to wait before start
	 * @param sleep Cooldown between runs
	 * @param runnable Runnable to run
	 */
	void repeatAsync(long wait, long sleep, Runnable runnable);

	/**
	 * Execute runnable in async thread repeatedly
	 * @param wait Units to wait before start
	 * @param sleep Cooldown between runs
	 * @param stop Count of repeats before stop
	 * @param runnable Runnable to run
	 */
	void repeatAsync(long wait, long sleep, int stop, Runnable runnable);

	/**
	 * Execute runnable in main thread
	 * @param runnable Runnable to run
	 */
	void runSync(Runnable runnable);

	/**
	 * Execute runnable in main thread
	 * @param wait Units to wait before start
	 * @param runnable Runnable to run
	 */
	void runSync(long wait, Runnable runnable);

	/**
	 * Execute runnable in main thread repeatedly forever
	 * @param wait Units to wait before start
	 * @param sleep Cooldown between runs
	 * @param runnable Runnable to run
	 */
	void repeatSync(long wait, long sleep, Runnable runnable);

	/**
	 * Execute runnable in main thread repeatedly
	 * @param wait Units to wait before start
	 * @param sleep Cooldown between runs
	 * @param stop Count of repeats before stop
	 * @param runnable Runnable to run
	 */
	void repeatSync(long wait, long sleep, int stop, Runnable runnable);

	/**
	 * Check if method is used in async thread
	 * @return Is async thread
	 */
	boolean isAsync();
}
