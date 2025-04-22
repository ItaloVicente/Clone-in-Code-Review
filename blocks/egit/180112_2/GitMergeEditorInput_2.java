		activations.values()
				.forEach(a -> a.getHandlerService().deactivateHandler(a));
		activations.clear();
		if (toggleCurrentChanges != null) {
			toggleCurrentChanges.dispose();
			toggleCurrentChanges = null;
		}
