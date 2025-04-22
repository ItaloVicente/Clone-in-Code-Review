		bundleContext = bundle.getBundleContext();

		List<MPartDescriptor> descriptorsToBeRemoved = new ArrayList<>(app.getDescriptors());
		List<MAddon> addonsToBeRemoved = new ArrayList<>(app.getAddons());
		List<MHandler> handlersToBeRemoved = new ArrayList<>(app.getHandlers());

		ExecutorService executor = Executors.newFixedThreadPool(3);

		CompletableFuture.supplyAsync(() -> removeAvailableAppElements(descriptorsToBeRemoved), executor)
				.thenAccept(d -> uiSync.asyncExec(() -> iteratorRemove(app.getDescriptors(), d)));
		CompletableFuture.supplyAsync(() -> removeAvailableAppElements(addonsToBeRemoved), executor)
				.thenAccept(a -> uiSync.asyncExec(() -> iteratorRemove(app.getAddons(), a)));
		CompletableFuture.supplyAsync(() -> removeAvailableAppElements(handlersToBeRemoved), executor)
				.thenAccept(h -> uiSync.asyncExec(() -> iteratorRemove(app.getHandlers(), h)));
	}

	private List<? extends MApplicationElement> removeAvailableAppElements(
			List<? extends MApplicationElement> appElements) {
		for (Iterator<? extends MApplicationElement> iterator = appElements.iterator(); iterator.hasNext();) {
			MApplicationElement appElement = iterator.next();
			boolean validAppElement = isValidAppElement(appElement);
			if (validAppElement) {
