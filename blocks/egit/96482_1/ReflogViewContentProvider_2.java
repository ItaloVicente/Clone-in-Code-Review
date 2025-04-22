		@Override
		public Object[] getChildren(Object o) {
			if (refLog != null) {
				return refLog.toArray();
			}
			return new Object[0];
		}

		@Override
		public ImageDescriptor getImageDescriptor(Object object) {
			return null;
		}

		@Override
		public String getLabel(Object o) {
			return null;
		}

		@Override
		public Object getParent(Object o) {
			return null;
		}

		@Override
		public void fetchDeferredChildren(Object object,
				IElementCollector collector, IProgressMonitor monitor) {
			try (Git git = new Git(repository)) {
