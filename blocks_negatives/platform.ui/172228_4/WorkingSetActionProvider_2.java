				if (!workingSet.isAggregateWorkingSet()) {
					IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
					viewer.setInput(workingSetManager.createAggregateWorkingSet(
							"", "", new IWorkingSet[] { workingSet })); //$NON-NLS-1$ //$NON-NLS-2$
				} else {
					viewer.setInput(workingSet);
				}
