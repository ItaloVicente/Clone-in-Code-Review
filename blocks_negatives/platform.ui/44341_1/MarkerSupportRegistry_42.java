					MarkerGroupingEntry next = (MarkerGroupingEntry) nextEntriesIterator
							.next();
					IDEWorkbenchPlugin
							.log(NLS
									.bind(
											"markerGroupingEntry {0} defines invalid group {1}",//$NON-NLS-1$
											new String[] { next.getId(),
													nextGroupId }));
