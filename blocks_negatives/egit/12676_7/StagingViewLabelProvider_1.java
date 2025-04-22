	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0)
			return getImage(element);
		return null;
	}

	public String getColumnText(Object element, int columnIndex) {
		if (columnIndex == 0)
			return getStyledText(element).toString();
	}

