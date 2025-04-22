				IMarkerResolution selected = (IMarkerResolution) resolutionsList.getStructuredSelection()
						.getFirstElement();
				if (selected == null) {
					return new Object[0];
				}

				if (resolutions.containsKey(selected)) {
					return resolutions.get(selected).toArray();
