		}
		if (this.userFiltersTab != null) {
			this.commonViewer.setData(NavigatorPlugin.RESOURCE_REGEXP_FILTER_DATA, this.userFiltersTab.getUserFilters());
			if (!this.userFiltersTab.getUserFilters().isEmpty()) {
				filterIdsToActivate.add(NavigatorPlugin.RESOURCE_REGEXP_FILTER_FILTER_ID);
			}
		}
		if (this.userFiltersTab != null || this.commonFiltersTab != null) {
