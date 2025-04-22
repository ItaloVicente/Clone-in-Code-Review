	private static class SearchComposite extends Composite {

		public SearchComposite(Composite parent, int style) {
			super(parent, style);
		}

		public void setLayoutData(Object layoutData) {
			if (getLayoutData() == null)
				super.setLayoutData(layoutData);
		}
	}

