	public void createColumn(Tree tr, String text) {
		TreeColumn column = new TreeColumn(tr, SWT.NONE);
		column.setWidth(100);
		column.setText(text);
		tr.setHeaderVisible(true);
	}

