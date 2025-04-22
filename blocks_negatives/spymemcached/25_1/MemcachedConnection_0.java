	private static AtomicInteger continuousTimeout = new AtomicInteger(0);

	/**
	 * whenever timeout exception occur, timeout counter increase by 1 until timeout exception threshold
	 * but, if isIncrease is false, timeout counter will be reset
	 * @param isIncrease
	 */
	public static void setContinuousTimeout(boolean isIncrease) {
		if (isIncrease) {
			continuousTimeout.getAndAdd(1);
		} else {
			continuousTimeout.set(0);
		}
	}
