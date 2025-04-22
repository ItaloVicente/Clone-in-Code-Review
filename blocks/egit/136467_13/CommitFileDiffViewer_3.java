		if ((rawTable.getStyle() & SWT.MULTI) != 0) {
			selectAll = ActionUtils.createGlobalAction(ActionFactory.SELECT_ALL,
					() -> doSelectAll());
			selectAll.setEnabled(true);
			ActionUtils.setGlobalActions(rawTable, copy, selectAll);
			mgr.add(selectAll);
		} else {
			ActionUtils.setGlobalActions(rawTable, copy);
		}
