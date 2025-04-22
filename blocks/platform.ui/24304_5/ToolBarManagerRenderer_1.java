	private void addCleanupDisposeListener(final MToolBar toolbarModel,
			ToolBar control) {

		if (!disposeAdded.contains(toolbarModel)) {
			control.addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp(toolbarModel);
					disposeAdded.remove(toolbarModel);
				}
			});
			disposeAdded.add(toolbarModel);
		}

	}

