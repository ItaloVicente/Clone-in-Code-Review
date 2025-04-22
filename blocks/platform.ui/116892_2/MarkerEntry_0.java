		if (adapter.equals(IWorkbenchAdapter.class)) {
			return adapter.cast(new WorkbenchMarker() {

				@Override
				public Object getParent(Object o) {
					return super.getParent(marker);
				}

				@Override
				public String getLabel(Object o) {
					return getMarkerTypeName();
				}

				@Override
				public ImageDescriptor getImageDescriptor(Object object) {
					return super.getImageDescriptor(marker);
				}

			});
		}
