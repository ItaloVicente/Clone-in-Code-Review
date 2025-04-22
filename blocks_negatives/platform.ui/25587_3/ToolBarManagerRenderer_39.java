	/**
	 * @param manager
	 * @param control
	 */
	private void addCleanupDisposeListener(final MToolBar toolbarModel,
			ToolBar control) {

		final Map<String, Object> transientData = toolbarModel
				.getTransientData();
		if (!transientData.containsKey(DISPOSE_ADDED)) {
			transientData.put(DISPOSE_ADDED, Boolean.TRUE);
			control.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp(toolbarModel);
					transientData.remove(DISPOSE_ADDED);
				}
			});
		}

	}

