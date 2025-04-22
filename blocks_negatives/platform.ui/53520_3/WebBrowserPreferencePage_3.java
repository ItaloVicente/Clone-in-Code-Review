	class BrowserContentProvider implements IStructuredContentProvider {
		@Override
		public Object[] getElements(Object inputElement) {
			List<IBrowserDescriptor> list = new ArrayList<>();
			Iterator<IBrowserDescriptor> iterator = BrowserManager.getInstance().getWebBrowsers()
					.iterator();
			while (iterator.hasNext()) {
				IBrowserDescriptor browser = iterator
						.next();
				list.add(browser);
			}
			return list.toArray();
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}
	}

