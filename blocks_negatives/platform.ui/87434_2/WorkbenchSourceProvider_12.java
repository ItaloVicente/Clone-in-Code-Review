	private final IPropertyChangeListener propertyListener = new IPropertyChangeListener() {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (WorkbenchWindow.PROP_COOLBAR_VISIBLE
					.equals(event.getProperty())) {
				Object newValue = event.getNewValue();
				if (newValue == null || !(newValue instanceof Boolean))
					return;
				if (!lastCoolbarVisibility.equals(newValue)) {
					fireSourceChanged(
							ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE,
							ISources.ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME,
							newValue);
					lastCoolbarVisibility = (Boolean) newValue;
				}
			} else if (WorkbenchWindow.PROP_PERSPECTIVEBAR_VISIBLE.equals(event
					.getProperty())) {
				Object newValue = event.getNewValue();
				if (newValue == null || !(newValue instanceof Boolean))
					return;
				if (!lastPerspectiveBarVisibility.equals(newValue)) {
					fireSourceChanged(
							ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE,
							ISources.ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME,
							newValue);
					lastPerspectiveBarVisibility = (Boolean) newValue;
				}
			} else if (WorkbenchWindow.PROP_STATUS_LINE_VISIBLE.equals(event
					.getProperty())) {
				Object newValue = event.getNewValue();
				if (newValue == null || !(newValue instanceof Boolean))
					return;
				if (!lastStatusLineVisibility.equals(newValue)) {
					fireSourceChanged(
							ISources.ACTIVE_WORKBENCH_WINDOW_SUBORDINATE,
							ISources.ACTIVE_WORKBENCH_WINDOW_NAME
									+ ".isStatusLineVisible", newValue); //$NON-NLS-1$
					lastStatusLineVisibility = (Boolean) newValue;
				}
