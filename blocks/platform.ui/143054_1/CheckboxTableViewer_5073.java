	public boolean getGrayed(Object element) {
		Widget widget = findItem(element);
		if (widget instanceof TableItem) {
			return ((TableItem) widget).getGrayed();
		}
		return false;
	}

