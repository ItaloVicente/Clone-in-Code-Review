		@Override
		public void fetchDeferredChildren(Object object,
				IElementCollector collector, IProgressMonitor monitor) {
			if (children != null) {
				return;
			}
