
		for (MarkerFieldFilterGroup marker : declaredFilters) {
			if (marker.isEnabled()) {
				configsTable.setChecked(marker, true);
			}
		}
