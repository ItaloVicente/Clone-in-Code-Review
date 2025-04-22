		selectAll = ActionUtils.createGlobalAction(ActionFactory.SELECT_ALL,
				() -> doSelectAll());
		selectAll.setEnabled(true);
		copy = ActionUtils.createGlobalAction(ActionFactory.COPY,
				() -> doCopy());
		copy.setEnabled(true);
		globalActions.add(selectAll);
		globalActions.add(copy);
		mgr.add(selectAll);
		mgr.add(copy);
