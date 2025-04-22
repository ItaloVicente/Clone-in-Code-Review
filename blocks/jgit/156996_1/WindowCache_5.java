	private static interface PageRef<T> {
		T get();

		boolean kill();

		PackFile getPack();

		long getPosition();

		int getSize();

		long getLastAccess();

		void setLastAccess(long time);

		boolean isStrongRef();
	}

