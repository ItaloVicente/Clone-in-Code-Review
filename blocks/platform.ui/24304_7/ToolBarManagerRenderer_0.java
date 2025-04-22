
		ToolBarManager manager = getManager(toolbarModel);
		if (manager != null && manager.getControl() != null) {
			addCleanupDisposeListener(toolbarModel, manager.getControl());
		}

