	}

	protected final IWorkbenchAdapter getAdapter(Object o) {
		if (!(o instanceof IAdaptable)) {
			return null;
		}
