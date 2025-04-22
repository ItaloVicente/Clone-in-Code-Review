			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				activeTextControl.paste();
				updateActionsEnableState();
				return;
			}
			if (pasteAction != null) {
				pasteAction.runWithEvent(event);
				return;
			}
		}

		public void updateEnabledState() {
			if (activeTextControl != null && !activeTextControl.isDisposed()) {
				boolean canPaste = false;
				if (activeTextControl.getEditable()) {
					Clipboard clipboard = new Clipboard(activeTextControl.getDisplay());
