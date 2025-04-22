		/**
		 * On the first call, starts a background job to fetch the result.
		 * Subsequent calls do nothing and return immediately.
		 *
		 * @throws InvocationTargetException
		 *             if starting the job fails
		 */
		public synchronized void fetch() throws InvocationTargetException {
			if (job != null || state != State.PRISTINE) {
				return;
			}
			ListRemoteOperation listOp;
