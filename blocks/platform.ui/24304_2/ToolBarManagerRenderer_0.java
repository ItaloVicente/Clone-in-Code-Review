	public void processContribution(final MToolBar toolbarModel,
			String elementId) {

		ToolBarManager manager = getManager(toolbarModel);
		if (manager != null && manager.getControl() != null) {
			manager.getControl().addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent e) {
					cleanUp((MToolBar) toolbarModel);
				}
			});
		}

