
				if (matchingMarkTreeFilter != null) {
					try {
						d.marked = matchingMarkTreeFilter.include(walk);
					} catch (StopWalkException e) {
						matchingMarkTreeFilter = null;
						d.marked = false;
					}
				}

