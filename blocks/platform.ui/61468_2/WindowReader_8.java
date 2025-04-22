	public Integer getDefaultFastViewSide() {
		Integer defaultFastViewSide = null;
		IMemento fastViewData = getChild("fastViewData"); //$NON-NLS-1$
		if (fastViewData != null) {
			defaultFastViewSide = fastViewData.getInteger(IWorkbenchConstants.TAG_FAST_VIEW_SIDE);
		}
		return defaultFastViewSide;
	}

