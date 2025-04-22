	protected class FilterResourcesByLocation extends ViewerFilter	 {

		private boolean enabled;

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;

		}

		@Override
		public Object[] filter(Viewer viewer, Object parent, Object[] elements) {
			if (!this.enabled) {
				return elements;
			}
			Map<IPath, IResource> bestResourceForPath = new LinkedHashMap<IPath, IResource>();
			for (Object item : elements) {
				if (item instanceof IResource) {
					IResource currentResource = (IResource) item;
					IResource otherResource = bestResourceForPath.get(currentResource.getLocation());
					if (otherResource == null || otherResource.getFullPath().segmentCount() > currentResource
							.getFullPath().segmentCount()) {
						bestResourceForPath.put(currentResource.getLocation(), currentResource);
					}
				}
			}
			return bestResourceForPath.values().toArray(new IResource[bestResourceForPath.size()]);
		}

		@Override
		public boolean select(Viewer viewer, Object parentElement, Object element) {
			throw new UnsupportedOperationException(
					"Individual selection not supported, use select(viewer, parent, Object[] elements)"); //$NON-NLS-1$
		}

	}

