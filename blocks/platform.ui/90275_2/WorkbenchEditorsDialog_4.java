    private SelectionListener headerListener = widgetSelectedAdapter(e -> {
		TableColumn column = (TableColumn) e.widget;
		int index = editorsTable.indexOf(column);
	    if (index == sortColumn) {
			reverse = !reverse;
		} else {
			sortColumn = index;
		}
		editorsTable.setSortDirection(reverse ? SWT.DOWN : SWT.UP);
		editorsTable.setSortColumn(column);
	    updateItems();
	});
