
	private void injectTextActionHandles() {
		if (autoMode) {
			IAction action = actionBars.getGlobalActionHandler(ActionFactory.CUT.getId());
			if (action != textCutAction) {
				setCutAction(action);
			}
			action = actionBars.getGlobalActionHandler(ActionFactory.COPY.getId());
			if (action != textCopyAction) {
				setCopyAction(action);
			}
			action = actionBars.getGlobalActionHandler(ActionFactory.PASTE.getId());
			if (action != textPasteAction) {
				setPasteAction(action);
			}
			action = actionBars.getGlobalActionHandler(ActionFactory.SELECT_ALL.getId());
			if (action != textSelectAllAction) {
				setSelectAllAction(action);
			}
			action = actionBars.getGlobalActionHandler(ActionFactory.DELETE.getId());
			if (action != textDeleteAction) {
				setDeleteAction(action);
			}

			updateActionBars();
		}
	}
