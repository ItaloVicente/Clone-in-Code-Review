			if (markTreeFilter != null) {
				try {
					entry.marked = markTreeFilter.include(walk);
				} catch (StopWalkException e) {
					markTreeFilter = null;
					entry.marked = false;
				}
			}

