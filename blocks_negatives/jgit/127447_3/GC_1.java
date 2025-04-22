		Future<Collection<PackFile>> result = executor.submit(gcTask);
		if (background) {
			return Collections.emptyList();
		}
		try {
			return result.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IOException(e);
		}
