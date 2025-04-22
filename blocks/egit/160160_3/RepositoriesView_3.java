
		public ViewerCell getCell(Object element, int column) {
			Widget item = findItem(element);
			if (item != null) {
				ViewerRow row = getViewerRowFromItem(item);
				if (row != null) {
					return row.getCell(column);
				}
			}
			return null;
		}
