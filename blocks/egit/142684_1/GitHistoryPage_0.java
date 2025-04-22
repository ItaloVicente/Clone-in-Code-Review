		ActionUtils.UpdateableAction selectAll = ActionUtils.createGlobalAction(
				ActionFactory.SELECT_ALL,
				() -> diffViewer.doOperation(ITextOperationTarget.SELECT_ALL),
				() -> diffViewer
						.canDoOperation(ITextOperationTarget.SELECT_ALL));
		ActionUtils.UpdateableAction copy = ActionUtils.createGlobalAction(
				ActionFactory.COPY,
				() -> diffViewer.doOperation(ITextOperationTarget.COPY),
				() -> diffViewer.canDoOperation(ITextOperationTarget.COPY));
		ActionUtils.setGlobalActions(diffViewer.getControl(), copy, selectAll);
		ShowWhitespaceAction showWhitespaceAction = new ShowWhitespaceAction(
				diffViewer);
		diffViewer.addSelectionChangedListener(e -> copy.update());
		MenuManager contextMenu = new MenuManager();
		contextMenu.setRemoveAllWhenShown(true);
		contextMenu.addMenuListener(manager -> {
			if (diffViewer.getDocument().getLength() > 0) {
				manager.add(copy);
				manager.add(selectAll);
				manager.add(new Separator());
				manager.add(showWhitespaceAction);
			}
		});
		StyledText diffWidget = diffViewer.getTextWidget();
		diffWidget.setMenu(contextMenu.createContextMenu(diffWidget));
		diffWidget.addDisposeListener(e -> showWhitespaceAction.dispose());

