	public <T> T execAndGet(Supplier<T> supplier) {
		@SuppressWarnings("unchecked")
		T[] box = (T[]) new Object[1];
		Runnable runnable = () -> box[0] = supplier.get();

		if (isCurrent()) {
			safeRun(runnable);
		} else {
			syncExec(runnable);
		}
		return box[0];
	}

