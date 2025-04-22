
	protected ICommonFilterDescriptor[] getFilterDescriptorChangeHistory() {
		if (commonFiltersTab != null) {
			return commonFiltersTab.getFilterDescriptorChangeHistory();
		}
		return new ICommonFilterDescriptor[0];
	}
