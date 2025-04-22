		final ActionUtils.UpdateableAction selectAll = ActionUtils
				.createGlobalAction(ActionFactory.SELECT_ALL,
				() -> doOperation(ITextOperationTarget.SELECT_ALL),
				() -> canDoOperation(ITextOperationTarget.SELECT_ALL));
		final ActionUtils.UpdateableAction copy = ActionUtils
				.createGlobalAction(ActionFactory.COPY,
				() -> doOperation(ITextOperationTarget.COPY),
				() -> canDoOperation(ITextOperationTarget.COPY));
		ActionUtils.setGlobalActions(getControl(), copy, selectAll);
		addSelectionChangedListener(event -> {
			selectAll.update();
			copy.update();
		});
		final MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(manager -> {
			mgr.add(selectAll);
			mgr.add(copy);
			mgr.add(new Separator());
			mgr.add(showBranchSequencePrefAction);
			mgr.add(showTagSequencePrefAction);
			mgr.add(wrapCommentsPrefAction);
			mgr.add(fillParagraphsPrefAction);
		});

		Control c = getControl();
		c.setMenu(mgr.createContextMenu(c));
