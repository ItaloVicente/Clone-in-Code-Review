		if (hiddenResourcesInitializer != null) {
			hiddenResourcesInitializer.cancel();
			try {
				hiddenResourcesInitializer.join();
			} catch (InterruptedException e) {
				Activator.logError(e.getLocalizedMessage(), e);
			}
			hiddenResourcesInitializer = null;
		}
