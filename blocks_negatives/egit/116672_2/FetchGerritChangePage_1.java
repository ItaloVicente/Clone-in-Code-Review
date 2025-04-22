	private static class ChangeList {

		/**
		 * Determines how to cancel a not-yet-completed future. Irrespective of
		 * the mechanism, the job may actually terminate normally, and
		 * subsequent calls to get() may return a result.
		 */
		public static enum CancelMode {
			/**
			 * Tries to cancel the job, which may decide to ignore the request.
			 * Callers to get() will remain blocked until the job terminates.
			 */
			CANCEL,
			/**
			 * Tries to cancel the job, which may decide to ignore the request.
			 * Outstanding get() calls will be woken up and may throw
			 * InterruptedException or return a result if the job terminated in
			 * the meantime.
			 */
			ABANDON,
			/**
			 * Tries to cancel the job, and if that doesn't succeed immediately,
			 * interrupts the job's thread. Outstanding calls to get() will be
			 * woken up and may throw InterruptedException or return a result if
			 * the job terminated in the meantime.
			 */
			INTERRUPT
		}

		private static enum State {
			PRISTINE, SCHEDULED, CANCELING, INTERRUPT, CANCELED, DONE
		}
