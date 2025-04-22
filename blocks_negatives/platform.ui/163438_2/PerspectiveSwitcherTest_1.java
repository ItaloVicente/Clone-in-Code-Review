		String originalPerspectiveBarPosition = apiPreferenceStore
				.getString(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR);
		String targetDockPosition = null;
		if (IWorkbenchPreferenceConstants.TOP_RIGHT.equals(originalPerspectiveBarPosition)
				|| IWorkbenchPreferenceConstants.TOP_LEFT.equals(originalPerspectiveBarPosition)) {
			targetDockPosition = IWorkbenchPreferenceConstants.LEFT;
		} else if (IWorkbenchPreferenceConstants.LEFT.equals(originalPerspectiveBarPosition)) {
			targetDockPosition = IWorkbenchPreferenceConstants.TOP_RIGHT;
		} else {
					+ originalPerspectiveBarPosition);
		}
