		if (e1 instanceof IWorkingSet && e2 instanceof IWorkingSet) {
			IWorkingSet set1 = (IWorkingSet) e1;
			IWorkingSet set2 = (IWorkingSet) e2;
			List<IWorkingSet> allWorkingSets = Arrays.asList(PlatformUI.getWorkbench().getWorkingSetManager().getAllWorkingSets());
			return allWorkingSets.indexOf(set1) - allWorkingSets.indexOf(set2);
		} else if (viewer instanceof StructuredViewer) {			
