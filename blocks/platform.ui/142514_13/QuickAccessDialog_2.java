		filterText.removeKeyListener(getKeyAdapter());
		if (toRemoveTextListeners != null) {
			for (ModifyListener listener : toRemoveTextListeners) {
				filterText.removeModifyListener(listener);
				if (linkedText != null) {
					linkedText.removeModifyListener(listener);
				}
			}
		}
		if (linkedText != null) {
			linkedText.setText(""); //$NON-NLS-1$
		}
