		public synchronized Collection<Change> getResult() {
			if (isFinished()) {
				return result;
			}
			throw new IllegalStateException(
					"Fetching change list is not finished"); //$NON-NLS-1$
		}

