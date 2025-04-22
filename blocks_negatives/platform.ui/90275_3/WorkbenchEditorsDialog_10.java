    private SelectionListener headerListener = new SelectionAdapter() {
        @Override
		public void widgetSelected(SelectionEvent e) {
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
        }
    };
