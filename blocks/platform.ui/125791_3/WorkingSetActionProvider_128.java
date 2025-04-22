				if (newWorkingSet.isAggregateWorkingSet()) {
					IAggregateWorkingSet agWs = (IAggregateWorkingSet) newWorkingSet;
					IWorkingSet[] comps = agWs.getComponents();
					if (comps.length > 1) {
						viewer.getCommonNavigator().setWorkingSetLabel(
								WorkbenchNavigatorMessages.WorkingSetActionProvider_multipleWorkingSets);
					} else if (comps.length > 0) {
						viewer.getCommonNavigator().setWorkingSetLabel(comps[0].getLabel());
					} else {
						viewer.getCommonNavigator().setWorkingSetLabel(null);
					}
				} else
					viewer.getCommonNavigator().setWorkingSetLabel(workingSet.getLabel());
			} else {
				viewer.getCommonNavigator().setWorkingSetLabel(null);
			}
