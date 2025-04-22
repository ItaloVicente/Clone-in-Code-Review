		CompletableFuture<T> future = new CompletableFuture<>();
		executor.execute(() -> {
			try {
				future.complete(action.call());
			} catch (Exception e) {
				future.completeExceptionally(e);
			}
		});
		if (isUIThread(Thread.currentThread())) {
