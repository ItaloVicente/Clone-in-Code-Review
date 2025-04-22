						for (Iterator j = getElements(
								PreferenceManager.POST_ORDER).iterator(); j
								.hasNext();) {
							((WorkbenchPreferenceNode) j.next())
									.clearKeywords();
						}
