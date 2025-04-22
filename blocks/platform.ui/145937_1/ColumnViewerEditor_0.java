			if ((feature & TABBING_CYCLE_IN_VIEWER) == TABBING_CYCLE_IN_VIEWER) {
				ViewerRow rowBelow = row.getNeighbor(ViewerRow.BELOW, false);
				if (rowBelow == null) {
					return getFirstCell(row);
				}


			}
