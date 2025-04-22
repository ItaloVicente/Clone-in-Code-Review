
	private static class RepositoriesCommonViewer extends CommonViewer {

		public RepositoriesCommonViewer(String viewId, Composite parent,
				int style) {
			super(viewId, parent, style);
		}

		@Override
		protected void init() {
			super.init();
			IBaseLabelProvider labelProvider = getLabelProvider();
			if (labelProvider instanceof DecoratingStyledCellLabelProvider) {
				((DecoratingStyledCellLabelProvider) labelProvider)
						.setLabelDecorator(null);
			}
		}
	}
