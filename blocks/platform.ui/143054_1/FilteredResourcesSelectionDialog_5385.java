				if (workingSet != null && !(workingSet.isAggregateWorkingSet() && workingSet.isEmpty())) {
					workingSetFilter.setWorkingSet(workingSet);
					setSubtitle(workingSet.getLabel());
				} else {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

					if (window != null) {
						IWorkbenchPage page = window.getActivePage();
						workingSet = page.getAggregateWorkingSet();

						if (workingSet.isAggregateWorkingSet() && workingSet.isEmpty()) {
							workingSet = null;
						}
