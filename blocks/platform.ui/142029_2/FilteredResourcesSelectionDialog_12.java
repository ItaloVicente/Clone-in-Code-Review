			if (WorkingSetFilterActionGroup.CHANGE_WORKING_SET.equals(property)) {

				IWorkingSet workingSet = (IWorkingSet) event.getNewValue();

				if (workingSet != null && !(workingSet.isAggregateWorkingSet() && workingSet.isEmpty())) {
					workingSetFilter.setWorkingSet(workingSet);
					setSubtitle(workingSet.getLabel());
				} else {
					IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

					if (window != null) {
						IWorkbenchPage page = window.getActivePage();
						workingSet = page.getAggregateWorkingSet();
