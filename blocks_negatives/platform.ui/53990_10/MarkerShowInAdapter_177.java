		return adapterType.cast(new IShowInSource() {
			@Override
			public ShowInContext getShowInContext() {
				IMarker[] markers = view.getSelectedMarkers();
				Collection<IResource> resources = new HashSet<>();
				for (int i = 0; i < markers.length; i++) {
					resources.add(markers[i].getResource());
				}
				return new ShowInContext(view.getViewerInput(), new StructuredSelection(resources.toArray()));
