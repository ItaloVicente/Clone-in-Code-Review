	private class PreviousPicksProvider extends QuickAccessProvider {

		public QuickAccessElement getElementForId(String id) {
			return null;
		}

		public QuickAccessElement[] getElements() {
			return previousPicksList.toArray(new QuickAccessElement[previousPicksList.size()]);
		}

		public QuickAccessElement[] getElementsSorted() {
			return getElements();
		}

		public String getId() {
		}

		public ImageDescriptor getImageDescriptor() {
			return WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_OBJ_NODE);
		}

		public String getName() {
			return QuickAccessMessages.QuickAccess_Previous;
		}

		protected void doReset() {
		}

		public boolean isAlwaysPresent() {
			return true;
		}
	}

