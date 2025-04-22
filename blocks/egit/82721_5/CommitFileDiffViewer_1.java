		selectAll = ActionUtils.createGlobalAction(ActionFactory.SELECT_ALL,
				() -> doSelectAll());
		selectAll.setEnabled(true);
		copy = ActionUtils.createGlobalAction(ActionFactory.COPY,
				() -> doCopy());
		copy.setEnabled(true);
		ActionUtils.setGlobalActions(getControl(), copy, selectAll);
		mgr.add(selectAll);
		mgr.add(copy);
