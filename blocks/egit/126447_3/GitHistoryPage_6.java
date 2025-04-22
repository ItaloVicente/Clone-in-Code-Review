		IMenuManager columnsMenuMgr = new MenuManager(
				UIText.GitHistoryPage_ColumnsSubMenuLabel);
		viewMenuMgr.add(columnsMenuMgr);
		TableColumn[] columns = graph.getTableView().getTable().getColumns();
		for (int i = 0; i < columns.length; i++) {
			if (i != 1) {
				ColumnAction action = new ColumnAction(columns[i].getText(), i);
				columnsMenuMgr.add(action);
				columns[i].addListener(SWT.Resize, event -> {
					action.update();
				});
			}
		}
