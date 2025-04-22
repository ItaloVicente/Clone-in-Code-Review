			if (!onBottom) {
				backgroundPattern = new Pattern(gc.getDevice(), 0, 0, 0, bounds.height + 1, selectedTabFillColors[0],
						selectedTabFillColors[1]);
			} else {
				backgroundPattern = new Pattern(gc.getDevice(), 0, 0, 0, bounds.height + 1, selectedTabFillColors[1],
						selectedTabFillColors[0]);
			}

