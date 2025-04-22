
		if (element instanceof IWorkingSet) {
			fWorkingSet= (IWorkingSet)element;
		} else {
			fWorkingSet= element.getAdapter(IWorkingSet.class);
		}
