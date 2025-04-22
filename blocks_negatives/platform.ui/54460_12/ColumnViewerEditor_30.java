			ViewerColumn column = viewer.getViewerColumn(columnIndex);
			if (column != null
					&& column.getEditingSupport() != null
					&& column.getEditingSupport().canEdit(
							newRow.getItem().getData())) {
				rv = newRow.getCell(columnIndex);
