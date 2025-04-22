		if (!appContext
				.containsKey("org.eclipse.e4.ui.workbench.modeling.EPartService")) {
			throw new IllegalStateException(
					"Core services not available. Please make sure that a declarative service implementation (such as the bundle 'org.eclipse.equinox.ds') is available!");
		}

