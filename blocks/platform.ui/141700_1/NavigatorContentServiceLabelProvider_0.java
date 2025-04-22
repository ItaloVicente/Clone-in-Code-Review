	@Override
	public String getToolTipText(Object anElement) {
		ILabelProvider[] labelProviders = contentService.findRelevantLabelProviders(anElement);
		for (ILabelProvider provider : labelProviders) {
			if (provider instanceof IToolTipProvider) {
				IToolTipProvider tooltipProvider = (IToolTipProvider) provider;
				String tooltip = tooltipProvider.getToolTipText(anElement);
				if (tooltip != null && !tooltip.isEmpty()) {
					return tooltip;
				}
			}
		}
		return null;
	}

