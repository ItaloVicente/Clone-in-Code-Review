	/**
	 * Operation callback for the tap dump request.
	 */
	interface Callback extends OperationCallback {
		/**
		 * Callback for each result from a get.
		 *
		 * @param message the response message sent from the server
		 */
		public void gotData(ResponseMessage message);
