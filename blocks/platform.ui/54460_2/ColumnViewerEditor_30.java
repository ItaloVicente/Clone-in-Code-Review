			ViewerColumn<E,I> column = viewer.getViewerColumn(columnIndex);
			if (column != null && column.getEditingSupport() != null) {
				@SuppressWarnings("unchecked")
				E element = (E) newRow.getItem().getData();
				if (column.getEditingSupport().canEdit(element)) {
					rv = newRow.getCell(columnIndex);
				} else {
					rv = searchCellAboveBelow(newRow, viewer, columnIndex,
							above);
				}
