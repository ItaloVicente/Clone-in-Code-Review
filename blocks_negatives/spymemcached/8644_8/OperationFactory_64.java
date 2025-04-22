	/**
	 * Create a getl operation. A getl gets the value for a key and then
	 * locks the value for a given amount of time. The maximum default lock
	 * time is 30 seconds.
	 *
	 * @param key the key to get and lock
	 * @param exp the amount of time the lock should be valid for in seconds.
	 * @param callback the callback that will contain the results
	 * @return a new GetOperation
	 */
	GetlOperation getl(String key, int exp, GetlOperation.Callback callback);
