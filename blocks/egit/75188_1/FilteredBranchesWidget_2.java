	private void setSortedColumn(final TreeColumn column) {
		comparator.setColumn(column);
		int dir = comparator.getDirection();
		branchesViewer.getTree().setSortDirection(dir);
		branchesViewer.getTree().setSortColumn(column);
		branchesViewer.refresh();
	}

