
			filtersMenu = new MenuManager(CommonNavigatorMessages.FilterActionGroup_RecentFilters);
			menuListener = new IMenuListener() {

				@Override
				public void menuAboutToShow(IMenuManager manager) {
					filtersMenu.removeAll();
					addLRUFilterActions(filtersMenu);
				}

			};
		}
	}

	private void addLRUFilterActions(IMenuManager manager) {
		if (lruFilterDescriptorStack.isEmpty()) {
			return;
		}

		NavigatorFilterService filterService = (NavigatorFilterService) commonViewer.getNavigatorContentService()
				.getFilterService();
		ICommonFilterDescriptor[] filterDescriptors = lruFilterDescriptorStack
				.toArray(new ICommonFilterDescriptor[lruFilterDescriptorStack.size()]);
		Arrays.sort(filterDescriptors, new Comparator<ICommonFilterDescriptor>() {

			@Override
			public int compare(ICommonFilterDescriptor o1, ICommonFilterDescriptor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		for (ICommonFilterDescriptor filterDescriptor : filterDescriptors) {
			manager.add(new ToggleFilterAction(commonViewer, filterService, filterDescriptor));
