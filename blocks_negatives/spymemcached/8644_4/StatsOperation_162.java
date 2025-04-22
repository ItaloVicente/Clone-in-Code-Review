	/**
	 * Callback for stats operation.
	 */
	interface Callback extends OperationCallback {
		/**
		 * Invoked once for every stat returned from the server.
		 *
		 * @param name the name of the stat
		 * @param val the stat value.
		 */
		void gotStat(String name, String val);
	}
}
