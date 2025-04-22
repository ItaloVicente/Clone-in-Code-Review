	@Override
	public String getToolTipText(Object element) {
		if (styledLabelProvider instanceof IToolTipProvider) {
			return ((IToolTipProvider) this.styledLabelProvider).getToolTipText(element);
		}
		return super.getToolTipText(element);
	}

