		Location instanceLocation = WorkbenchSWTActivator.getDefault()
				.getInstanceLocation();

		if (instanceLocation != null && instanceLocation.getURL() != null) {
			eclipseContext.set(E4Workbench.INSTANCE_LOCATION, instanceLocation);
		} else {
			eclipseContext.set(IWorkbench.PERSIST_STATE, false);
		}

