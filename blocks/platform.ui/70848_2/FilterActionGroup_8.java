	private void addLRUFilterActions(IMenuManager manager) {
		if (lruFilterDescriptorStack.isEmpty()) {
			return;
		}

		NavigatorFilterService filterService = (NavigatorFilterService) commonViewer.getNavigatorContentService()
				.getFilterService();
		ICommonFilterDescriptor[] filterDescriptors = lruFilterDescriptorStack
				.toArray(new ICommonFilterDescriptor[lruFilterDescriptorStack.size()]);
		Arrays.sort(filterDescriptors, new Comparator<ICommonFilterDescriptor>() {
