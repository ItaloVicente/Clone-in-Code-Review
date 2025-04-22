		boolean groupByLocation = true;
		if (settings.get(FILTER_BY_LOCATION) != null) {
			groupByLocation = settings.getBoolean(FILTER_BY_LOCATION);
		}
		this.groupResourcesByLocationAction.setChecked(groupByLocation);
		this.filterResourceByLocation.setEnabled(groupByLocation);
