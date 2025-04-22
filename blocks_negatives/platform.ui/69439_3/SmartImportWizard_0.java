	private IWorkingSet toWorkingSet(Object o) {
		if (o instanceof IWorkingSet) {
			return (IWorkingSet)o;
		} else if (o instanceof IAdaptable) {
			return ((IAdaptable)o).getAdapter(IWorkingSet.class);
		}
		return null;
	}

