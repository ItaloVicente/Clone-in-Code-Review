	public ViewerCell<E> getCell(int column) {
		if (column >= 0){
			@SuppressWarnings("unchecked")
			ViewerRow<E> viewerRow = (ViewerRow<E>) clone();
			return new ViewerCell<>(viewerRow, column, getElement());
		}
