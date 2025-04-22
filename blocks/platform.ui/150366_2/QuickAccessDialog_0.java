		if (!filterText.isDisposed()) {
			filterText.removeKeyListener(getKeyAdapter());
			if (toRemoveTextListeners != null) {
				for (ModifyListener listener : toRemoveTextListeners) {
					filterText.removeModifyListener(listener);
				}
