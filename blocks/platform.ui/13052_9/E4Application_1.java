
		String forcedPerspectiveId = getArgValue("perspective",
				applicationContext, false);
		if (forcedPerspectiveId != null) {
			appContext.set(E4Workbench.FORCED_PERSPECTIVE_ID,
					forcedPerspectiveId);
		}

