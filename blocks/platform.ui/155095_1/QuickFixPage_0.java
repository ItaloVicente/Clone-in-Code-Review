				Optional<IMarkerResolution> selected = getSelectedMarkerResolution();
				if (selected.isPresent()) {
					IMarkerResolution resolution = selected.get();
					if (resolutions.containsKey(resolution)) {
						return resolutions.get(resolution).toArray();
					}
