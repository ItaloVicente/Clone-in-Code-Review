		/**
		 * Tries to cancel the future. {@code cancel(false)} tries a normal job
		 * cancellation, which may or may not terminated the job (it may decide
		 * not to react to cancellation requests).
		 *
		 * @param cancellation
		 *            {@link CancelMode} defining how to cancel
		 *
		 * @return {@code true} if the future was canceled (its job is not
		 *         running anymore), {@code false} otherwise.
		 */
		public synchronized boolean cancel(CancelMode cancellation) {
			CancelMode mode = cancellation == null ? CancelMode.CANCEL
					: cancellation;
			switch (state) {
			case PRISTINE:
				finish(false);
				return true;
			case SCHEDULED:
				state = State.CANCELING;
				boolean canceled = job.cancel();
				if (canceled) {
					state = State.CANCELED;
				} else if (mode == CancelMode.INTERRUPT) {
					interrupt();
				} else if (mode == CancelMode.ABANDON) {
					notifyAll();
				}
				return canceled;
			case CANCELING:
				if (mode == CancelMode.INTERRUPT) {
					interrupt();
				} else if (mode == CancelMode.ABANDON) {
					notifyAll();
				}
				return false;
			case INTERRUPT:
				if (mode != CancelMode.CANCEL) {
					notifyAll();
				}
				return false;
			case CANCELED:
				return true;
			default:
				return false;
			}
		}

		public synchronized boolean isFinished() {
			return state == State.CANCELED || state == State.DONE;
		}

		public synchronized boolean isDone() {
			return state == State.DONE;
		}

		/**
		 * Retrieves the result. If the result is not yet available, the method
		 * blocks until it is or {@link #cancel(CancelMode)} is called with
		 * {@link CancelMode#ABANDON} or {@link CancelMode#INTERRUPT}.
		 *
		 * @return the result, which may be {@code null} if the future was
		 *         canceled
		 * @throws InterruptedException
		 *             if waiting was interrupted
		 * @throws InvocationTargetException
		 *             if the future's job cannot be created
		 */
		public synchronized Collection<Change> get()
				throws InterruptedException, InvocationTargetException {
			switch (state) {
			case DONE:
			case CANCELED:
				return result;
			case PRISTINE:
				fetch();
				return get();
			default:
				wait();
				if (state == State.CANCELING || state == State.INTERRUPT) {
					throw new InterruptedException();
				}
				return get();
			}
		}

		public synchronized Collection<Change> getResult() {
			if (isFinished()) {
				return result;
			}
			throw new IllegalStateException(
		}

		private synchronized void finish(boolean done) {
			state = done ? State.DONE : State.CANCELED;
			job = null;
		}

		private synchronized void interrupt() {
			state = State.INTERRUPT;
			job.interrupt();
