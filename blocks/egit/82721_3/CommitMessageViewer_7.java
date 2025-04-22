		final IAction selectAll = ActionUtils.createGlobalAction(
				ActionFactory.SELECT_ALL,
				() -> doOperation(ITextOperationTarget.SELECT_ALL),
				() -> canDoOperation(ITextOperationTarget.SELECT_ALL));
		final IAction copy = ActionUtils.createGlobalAction(ActionFactory.COPY,
				() -> doOperation(ITextOperationTarget.COPY),
				() -> canDoOperation(ITextOperationTarget.COPY));
		globalActions.add(selectAll);
		globalActions.add(copy);
