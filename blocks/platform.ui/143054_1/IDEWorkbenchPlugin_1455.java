	}


	public ProjectImageRegistry getProjectImageRegistry() {
		if (projectImageRegistry == null) {
			projectImageRegistry = new ProjectImageRegistry();
			projectImageRegistry.load();
		}
		return projectImageRegistry;
	}

	public MarkerImageProviderRegistry getMarkerImageProviderRegistry() {
		if (markerImageProviderRegistry == null) {
			markerImageProviderRegistry = new MarkerImageProviderRegistry();
		}
		return markerImageProviderRegistry;
	}
