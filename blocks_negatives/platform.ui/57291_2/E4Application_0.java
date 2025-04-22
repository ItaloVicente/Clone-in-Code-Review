		boolean deltaRestore;
		value = getArgValue(E4Workbench.DELTA_RESTORE, appContext, false);
		deltaRestore = value == null || Boolean.parseBoolean(value);
		eclipseContext.set(E4Workbench.DELTA_RESTORE, Boolean.valueOf(deltaRestore));

		String resourceHandler = getArgValue(IWorkbench.MODEL_RESOURCE_HANDLER, appContext, false);
