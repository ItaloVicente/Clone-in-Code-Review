		bundleContext = bundle.getBundleContext();

		cleanUnavailablePartDescriptors(app, uiSync);

		cleanHiddenCompatibilityEditors();
	}

	private void cleanUnavailablePartDescriptors(MApplication app, UISynchronize uiSync) {
		List<MPartDescriptor> descriptors = new ArrayList<>(app.getDescriptors());

		ExecutorService executor = Executors.newFixedThreadPool(1);

		CompletableFuture.supplyAsync(() -> getObsoletePartDescriptors(descriptors), executor)
				.thenAccept(d -> uiSync.asyncExec(() -> iteratorRemove(app.getDescriptors(), d)));
	}

	private List<MPartDescriptor> getObsoletePartDescriptors(List<MPartDescriptor> partDescriptors) {
		for (Iterator<MPartDescriptor> iterator = partDescriptors.iterator(); iterator.hasNext();) {
			MPartDescriptor appElement = iterator.next();
			boolean validAppElement = isValidPartDescriptor(appElement);
			if (validAppElement) {
