	private void runTasks(ExecutorService pool
			List<Throwable> errors) throws IOException {
		List<Future<?>> futures = new ArrayList<Future<?>>(tasks.size());
		for (DeltaTask task : tasks)
			futures.add(pool.submit(task));

		try {
			for (Future<?> f : futures) {
				try {
					f.get();
				} catch (ExecutionException failed) {
					errors.add(failed.getCause());
				}
			}
		} catch (InterruptedException ie) {
			for (Future<?> f : futures)
				f.cancel(true);
			throw new IOException(
					JGitText.get().packingCancelledDuringObjectsWriting);
		}
	}

