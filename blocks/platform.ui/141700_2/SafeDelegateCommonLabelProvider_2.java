
	@Override
	public String getToolTipText(Object element) {
		if (delegateLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) delegateLabelProvider).getToolTipText(element);
		}
		return null;
	}
