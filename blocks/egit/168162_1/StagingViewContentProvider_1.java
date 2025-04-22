					if (sortByState) {
						int result = getStateSortKey(e1) - getStateSortKey(e2);
						if (result != 0) {
							return result;
						}
					}
