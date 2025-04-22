		variableTable.getTable().setToolTipText(null);
		variableTable.setContentProvider(new ContentProvider());
		variableTable.setInput(this);
		createButtonGroup(pageComponent);
		return pageComponent;
	}
