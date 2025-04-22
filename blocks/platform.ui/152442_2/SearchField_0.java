	private void changeShowText(boolean showText, ToolItem quickAccessToolItem) {
		if (showText) {
			try {
				quickAccessToolItem.setText(quickAccessCommand.getName());
			} catch (NotDefinedException e) {
				e.printStackTrace();
			}
		} else {
			quickAccessToolItem.setText(""); //$NON-NLS-1$
		}
	}

