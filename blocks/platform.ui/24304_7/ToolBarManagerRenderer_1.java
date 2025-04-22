	private void addCleanupDisposeListener(final MToolBar toolbarModel,
			ToolBar control) {

		if (!toolbarModel.getTags().contains(DISPOSE_ADDED)) {
			toolbarModel.getTags().add(DISPOSE_ADDED);
			control.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp(toolbarModel);
					toolbarModel.getTags().remove(DISPOSE_ADDED);
				}
			});
		}

	}

