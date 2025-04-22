		@Override
		public QuickAccessElement getElementForId(String id) {
			getElementsSorted(null, new NullProgressMonitor()); // initialize content
			return super.getElementForId(id);
		}

