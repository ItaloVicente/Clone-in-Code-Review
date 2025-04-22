	private final IPropertyChangeListener propertyListener = event -> {
		if (WorkbenchWindow.PROP_COOLBAR_VISIBLE
				.equals(event.getProperty())) {
			Object newValue1 = event.getNewValue();
			if (newValue1 == null || !(newValue1 instanceof Boolean))
				return;
			if (!lastCoolbarVisibility.equals(newValue1)) {
				fireSourceChanged(
						ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE,
						ISources.ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME,
						newValue1);
				lastCoolbarVisibility = (Boolean) newValue1;
			}
		} else if (WorkbenchWindow.PROP_PERSPECTIVEBAR_VISIBLE.equals(event
				.getProperty())) {
			Object newValue2 = event.getNewValue();
			if (newValue2 == null || !(newValue2 instanceof Boolean))
				return;
			if (!lastPerspectiveBarVisibility.equals(newValue2)) {
				fireSourceChanged(
						ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE,
						ISources.ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME,
						newValue2);
				lastPerspectiveBarVisibility = (Boolean) newValue2;
			}
		} else if (WorkbenchWindow.PROP_STATUS_LINE_VISIBLE.equals(event
				.getProperty())) {
			Object newValue3 = event.getNewValue();
			if (newValue3 == null || !(newValue3 instanceof Boolean))
				return;
			if (!lastStatusLineVisibility.equals(newValue3)) {
				fireSourceChanged(
						ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE,
						ISources.ACTIVE_WORKBENCH_WINDOW_NAME
								+ ".isStatusLineVisible", newValue3); //$NON-NLS-1$
				lastStatusLineVisibility = (Boolean) newValue3;
