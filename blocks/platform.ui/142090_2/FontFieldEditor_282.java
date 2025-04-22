	private Button changeFontButton = null;

	private String changeButtonText;

	private String previewText;

	private FontData[] chosenFont;

	private Label valueControl;

	private DefaultPreviewer previewer;

	private static class DefaultPreviewer {
		private Text text;

		private String string;

		private Font font;

		public DefaultPreviewer(String s, Composite parent) {
			string = s;
			text = new Text(parent, SWT.READ_ONLY | SWT.BORDER);
			text.addDisposeListener(e -> {
				if (font != null) {
