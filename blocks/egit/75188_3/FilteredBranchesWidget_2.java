	private void setSortedColumn(final TreeColumn column, ColumnLabelProvider labelProvider) {
		comparator.setColumn(column, labelProvider);
		int dir = comparator.getDirection();
		branchesViewer.getTree().setSortDirection(dir);
		branchesViewer.getTree().setSortColumn(column);
		branchesViewer.refresh();
	}

