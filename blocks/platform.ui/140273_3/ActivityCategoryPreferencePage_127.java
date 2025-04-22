					newSet.removeAll(activitySet);
					activitySet = newSet;
				}

				workingCopy.setEnabledActivityIds(activitySet);
				updateCategoryCheckState(); // even though we're reacting to
			}
		}));
		categoryViewer = new CheckboxTableViewer(table);
