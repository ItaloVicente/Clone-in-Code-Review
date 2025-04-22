	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element != null)
			return element.toString() + " column " + columnIndex;
		return null;
	}
