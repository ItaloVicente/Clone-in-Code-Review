		ensureFiltersActivated();
	}

	private void ensureFiltersActivated() {
		INavigatorFilterService filterService = this.viewer.getNavigatorContentService().getFilterService();
		Set<String> filters = new HashSet<>();
		for (ICommonFilterDescriptor desc : filterService.getVisibleFilterDescriptors()) {
			if (filterService.isActive(desc.getId())) {
				filters.add(desc.getId());
			}
		}
		if (!filters.contains(HideTopLevelProjectIfNested.EXTENSION_ID)
				|| !filters.contains(HideFolderWhenProjectIsShownAsNested.EXTENTSION_ID)) {
			filters.add(HideTopLevelProjectIfNested.EXTENSION_ID);
			filters.add(HideFolderWhenProjectIsShownAsNested.EXTENTSION_ID);
			filterService.activateFilterIdsAndUpdateViewer(filters.toArray(new String[filters.size()]));
		}
