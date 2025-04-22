		final IAction selectAll = ActionUtils.createGlobalAction(
				ActionFactory.SELECT_ALL,
				() -> doOperation(ITextOperationTarget.SELECT_ALL),
				() -> canDoOperation(ITextOperationTarget.SELECT_ALL));
		final IAction copy = ActionUtils.createGlobalAction(ActionFactory.COPY,
				() -> doOperation(ITextOperationTarget.COPY),
				() -> canDoOperation(ITextOperationTarget.COPY));
		ActionUtils.setGlobalActions(getControl(), copy, selectAll);
		final MenuManager mgr = new MenuManager();
		mgr.setRemoveAllWhenShown(true);
		mgr.addMenuListener(manager -> {
			mgr.add(selectAll);
			final IAction copy2 = ActionUtils.createGlobalAction(
					ActionFactory.COPY,
					() -> doOperation(ITextOperationTarget.COPY),
					() -> canDoOperation(ITextOperationTarget.COPY));
			mgr.add(copy2);
			mgr.add(new Separator());
			mgr.add(showBranchSequencePrefAction);
			mgr.add(showTagSequencePrefAction);
			mgr.add(wrapCommentsPrefAction);
			mgr.add(fillParagraphsPrefAction);
		});

		Control c = getControl();
		c.setMenu(mgr.createContextMenu(c));
