		List<IAction> globalActions = new ArrayList<>();
		if (editable) {
			globalActions.add(cutAction);
			globalActions.add(pasteAction);
			globalActions.add(undoAction);
			globalActions.add(redoAction);
			globalActions.add(quickFixAction);
		}
		globalActions.add(copyAction);
		globalActions.add(selectAllAction);
		if (contentAssistAction != null) {
			globalActions.add(contentAssistAction);
		}
		ActionUtils.setGlobalActions(textWidget, globalActions,
				getHandlerService());
