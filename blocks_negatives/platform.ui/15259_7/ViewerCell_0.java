				ViewerCell cell = row.getCellAtVisualIndex(columnIndex);
				if (cell != null) {
					while (cell != null
							&& columnIndex < row.getColumnCount() - 1
							&& columnIndex > 0) {
						if (cell.isVisible()) {
							break;
						}

						columnIndex += modifier;
						cell = row.getCellAtVisualIndex(columnIndex);
						if (cell == null) {
							break;
						}
