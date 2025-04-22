			if ((feature & TABBING_CYCLE_IN_VIEWER) == TABBING_CYCLE_IN_VIEWER) {
				ViewerRow rowAbove = row.getNeighbor(ViewerRow.ABOVE, false);
				if (rowAbove == null) {
					return getLastCell(row);
				}

			}
