				ViewerCell<E> cell = row.getCellAtVisualIndex(columnIndex);
				while (cell != null && columnIndex < row.getColumnCount() - 1
						&& columnIndex > 0) {
					if (cell.isVisible()) {
						break;
					}

					columnIndex += modifier;
					cell = row.getCellAtVisualIndex(columnIndex);
					if (cell == null) {
						break;
