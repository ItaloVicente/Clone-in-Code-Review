	private ViewerCell getFirstCell(ViewerRow row) {
		ViewerRow rowAbove = row.getNeighbor(ViewerRow.ABOVE, false);
		ViewerRow topMostRow = rowAbove;
		if (rowAbove == null) {
			return row.getCell(row.getCreationIndex(0));
		}
		while (rowAbove != null) {
			topMostRow = rowAbove;
			rowAbove = rowAbove.getNeighbor(ViewerRow.ABOVE, false);
		}

		return topMostRow.getCell(row.getCreationIndex(0));
	}

