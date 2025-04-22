	public CompletableFuture<Void> busyExec(Runnable action) {
		Objects.requireNonNull(action);
		return busyCall(() -> {
			action.run();
			return null;
		});
