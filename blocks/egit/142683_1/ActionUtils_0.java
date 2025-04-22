	public static UpdateableAction createTextAction(
			ITextOperationTarget target, ActionFactory factory,
			int operationCode) {
		if (operationCode == ITextOperationTarget.REDO) {
			return createGlobalAction(factory,
					() -> target.doOperation(operationCode), () -> true);
		}
		return createGlobalAction(factory,
				() -> target.doOperation(operationCode),
				() -> target.canDoOperation(operationCode));
	}

	private static UpdateableAction[] createStandardTextActions(
			ITextOperationTarget target, boolean editable) {
		UpdateableAction[] actions = new UpdateableAction[ITextOperationTarget.SELECT_ALL
				+ 1];
		if (editable) {
			actions[ITextOperationTarget.UNDO] = createTextAction(target,
					ActionFactory.UNDO, ITextOperationTarget.UNDO);
			actions[ITextOperationTarget.REDO] = createTextAction(target,
					ActionFactory.REDO, ITextOperationTarget.REDO);
			actions[ITextOperationTarget.CUT] = createTextAction(target,
					ActionFactory.CUT, ITextOperationTarget.CUT);
			actions[ITextOperationTarget.PASTE] = createTextAction(
					target, ActionFactory.PASTE, ITextOperationTarget.PASTE);
			actions[ITextOperationTarget.DELETE] = createTextAction(
					target, ActionFactory.DELETE, ITextOperationTarget.DELETE);
		}
		actions[ITextOperationTarget.COPY] = createTextAction(target,
				ActionFactory.COPY, ITextOperationTarget.COPY);
		actions[ITextOperationTarget.SELECT_ALL] = createTextAction(
				target, ActionFactory.SELECT_ALL,
				ITextOperationTarget.SELECT_ALL);
		return actions;
	}

	public static UpdateableAction[] fillStandardTextActions(
			ITextOperationTarget target, boolean editable,
			MenuManager manager) {
		UpdateableAction[] actions = createStandardTextActions(target,
				editable);
		if (manager != null) {
			if (editable) {
				manager.add(actions[ITextOperationTarget.UNDO]);
				manager.add(actions[ITextOperationTarget.REDO]);
				manager.add(new Separator());
				manager.add(actions[ITextOperationTarget.CUT]);
			}
			manager.add(actions[ITextOperationTarget.COPY]);
			if (editable) {
				manager.add(actions[ITextOperationTarget.PASTE]);
				manager.add(new Separator());
				manager.add(actions[ITextOperationTarget.DELETE]);
			}
			manager.add(actions[ITextOperationTarget.SELECT_ALL]);
		}
		return actions;
	}

