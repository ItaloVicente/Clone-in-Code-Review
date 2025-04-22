	public void saveState(IMemento aMemento) {
		IMemento lruFilters = aMemento.createChild(TAG_LRU_FILTERS);
		if (!lruFilterDescriptorStack.isEmpty()) {
			for (ICommonFilterDescriptor filterDescriptor : lruFilterDescriptorStack) {
				IMemento child = lruFilters.createChild(TAG_CHILD);
				child.putString(TAG_FILTER_ID, filterDescriptor.getId());
			}
		}
