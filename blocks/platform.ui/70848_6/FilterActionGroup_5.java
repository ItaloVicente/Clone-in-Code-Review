	@Override
	public void restoreState(IMemento aMemento) {
		IMemento lruFilters = aMemento.getChild(TAG_LRU_FILTERS);
		lruFilterDescriptorStack.clear();
		if (lruFilters != null) {
			NavigatorFilterService filterService = (NavigatorFilterService) commonViewer.getNavigatorContentService()
					.getFilterService();
			ICommonFilterDescriptor[] visibleFilterDescriptors = filterService.getVisibleFilterDescriptorsForUI();
			for (IMemento child : lruFilters.getChildren(TAG_CHILD)) {
				String id = child.getString(TAG_FILTER_ID);
				if (id != null) {
					for (ICommonFilterDescriptor visibleFilterDescriptor : visibleFilterDescriptors) {
						if (visibleFilterDescriptor.getId().equals(id)) {
							lruFilterDescriptorStack.push(visibleFilterDescriptor);
							break;
						}
					}
				}
			}
