	public boolean await(long wait, TimeUnit unit) throws InterruptedException {
		synchronized (state) {
			if (state.get().isDone()) {
				return true;
			}
			state.wait(unit.toMillis(wait));
			return state.get().isDone();
