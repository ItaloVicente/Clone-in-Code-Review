	@Override
	protected void checkStateChanged(CheckStateChangedEvent event) {
		super.checkStateChanged(event);
		ICommonFilterDescriptor filterDescriptor = (ICommonFilterDescriptor) event.getElement();
		filterDescriptorChangeHistory.remove(filterDescriptor);
		filterDescriptorChangeHistory.push(filterDescriptor);
	}

	protected ICommonFilterDescriptor[] getFilterDescriptorChangeHistory() {
		return filterDescriptorChangeHistory.toArray(new ICommonFilterDescriptor[filterDescriptorChangeHistory.size()]);
	}

