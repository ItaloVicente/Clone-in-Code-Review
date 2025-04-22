	private void cleanUnavailableHandlers(MApplication app, UISynchronize uiSync) {
		List<MHandler> handlers = new ArrayList<>(app.getHandlers());

		ExecutorService executor = Executors.newFixedThreadPool(1);

		CompletableFuture.supplyAsync(() -> getObsoleteHandlers(handlers), executor)
				.thenAccept(d -> uiSync.asyncExec(() -> iteratorRemove(app.getHandlers(), d)));
	}

