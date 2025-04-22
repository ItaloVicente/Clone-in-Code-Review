		if (toolBarManager != null) {
			toolBarManager.dispose();
			toolBarManager.removeAll();
			toolBarManager = null;
		}

		if ((coolItem != null) && (!coolItem.isDisposed())) {
			coolItem.dispose();
			coolItem = null;
		}
		if (chevronMenuManager != null) {
			chevronMenuManager.dispose();
		}
		disposed = true;
	}

	@Override
