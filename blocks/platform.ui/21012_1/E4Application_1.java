		String forcedPerspectiveId = getArgValue(PERSPECTIVE_ARG_NAME,
				applicationContext, false);
		if (forcedPerspectiveId != null) {
			appContext.set(E4Workbench.FORCED_PERSPECTIVE_ID,
					forcedPerspectiveId);

		}
