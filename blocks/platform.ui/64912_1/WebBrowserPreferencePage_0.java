	class BrowserTableContentProvider implements IStructuredContentProvider {
		private BrowserManager input;

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			input = (BrowserManager) newInput;
		}

		@Override
		public Object[] getElements(Object inputElement) {
			return input.getWebBrowsers().toArray();
		}

		@Override
		public void dispose() {
		}
	}

