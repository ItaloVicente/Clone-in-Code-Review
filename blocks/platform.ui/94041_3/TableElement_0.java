	public void setTableHeaderColor(Color color) {
		getTable().setHeaderForeground(color);
	}

	public void setTableHeaderBackgroundColor(Color color) {
		getTable().setHeaderBackground(color);
	}

	public Table getTable() {
		return (Table) getNativeWidget();
	}

