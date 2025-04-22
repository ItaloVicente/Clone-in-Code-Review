	private class PreviousPicksProvider extends QuickAccessProvider {

		public QuickAccessElement getElementForId(String id) {
			return null;
		}

		public QuickAccessElement[] getElements() {
			return (QuickAccessElement[]) previousPicksList
					.toArray(new QuickAccessElement[previousPicksList.size()]);
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

		public boolean isAlwaysPresent() {
			return true;
		}

		protected void doReset() {
		}
	}

