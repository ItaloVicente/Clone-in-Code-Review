	private void cleanUnavailableAddons(MApplication app, UISynchronize uiSync) {
		List<MAddon> addons = new ArrayList<>(app.getAddons());

		ExecutorService executor = Executors.newFixedThreadPool(1);

		CompletableFuture.supplyAsync(() -> getObsoleteAddons(addons), executor)
				.thenAccept(d -> uiSync.asyncExec(() -> iteratorRemove(app.getHandlers(), d)));
	}

