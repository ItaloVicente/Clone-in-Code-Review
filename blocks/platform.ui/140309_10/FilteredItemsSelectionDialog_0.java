	private class TypeItemLabelProvider extends LabelProvider implements ILabelDecorator, IStyledLabelProvider {

		private BoldStylerProvider boldStylerProvider;

		@Override
		public void dispose() {
			super.dispose();

			if (boldStylerProvider != null) {
				boldStylerProvider.dispose();
				boldStylerProvider = null;
			}
		}

		@Override
		public Image decorateImage(Image image, Object element) {
			return image;
		}

		@Override
		public String decorateText(String text, Object element) {
			return text;
		}

		@Override
		public StyledString getStyledText(Object element) {
			String text = getText(element);
			String namePattern = filter != null ? filter.getPattern() : null;

			return getStyledStringHighlighter().highlight(text, namePattern, getBoldStylerProvider().getBoldStyler());
		}

		private BoldStylerProvider getBoldStylerProvider() {
			if (boldStylerProvider == null) {
				boldStylerProvider = new BoldStylerProvider(getDialogArea().getFont());
			}
			return boldStylerProvider;
		}

	}

	public IStyledStringHighlighter getStyledStringHighlighter() {
		if (styledStringHighlighter == null) {
			styledStringHighlighter = new StyledStringHighlighter();
		}

		return styledStringHighlighter;
	}

	public void setStyledStringHighlighter(IStyledStringHighlighter styledStringHighlighter) {
		this.styledStringHighlighter = styledStringHighlighter;
	}

