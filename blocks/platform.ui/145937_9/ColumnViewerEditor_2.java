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

	private ViewerCell getLastCell(ViewerRow row) {
		ViewerRow rowBelow = row.getNeighbor(ViewerRow.BELOW, false);
		ViewerRow bottomMostRow = rowBelow;
		if (rowBelow == null) {
			return row.getCell(row.getCreationIndex(row.getColumnCount() - 1));
		}
		while (rowBelow != null) {
			bottomMostRow = rowBelow;
			rowBelow = rowBelow.getNeighbor(ViewerRow.BELOW, false);
		}

		return bottomMostRow.getCell(row.getCreationIndex(bottomMostRow.getColumnCount() - 1));
	}


