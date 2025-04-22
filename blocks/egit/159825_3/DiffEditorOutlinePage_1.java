	static void openQuickOutline(IDocument document,
			ISelectionProvider selectionProvider) {
		new QuickOutlinePopup(document, selectionProvider).open();
	}

	private static class QuickOutlinePopup extends PopupDialog {
		private DiffEditorOutlinePage delegate;

		private ISelectionProvider selectionProvider;

		private Text filterText;


		public QuickOutlinePopup(IDocument document,
				ISelectionProvider selectionProvider) {
			this(null, document, selectionProvider);
		}

		public QuickOutlinePopup(Shell parent, IDocument document,
				ISelectionProvider selectionProvider) {
			super(parent, SWT.RESIZE, true, false, true, true, true,
					UIText.DiffEditor_QuickOutlineAction,
					UIText.DiffEditor_QuickOutlineFilterDescription);
			delegate = new DiffEditorOutlinePage();
			delegate.setInput(document);
			this.selectionProvider = selectionProvider;
		}

		@Override
		protected Control createTitleControl(Composite parent) {
			filterText = createFilterText(parent);
			return filterText;
		}

		@Override
		protected Control getFocusControl() {
			return filterText;
		}

		protected Text createFilterText(Composite parent) {
			filterText = new Text(parent, SWT.NONE);
			Dialog.applyDialogFont(filterText);

			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.CENTER;
			filterText.setLayoutData(data);
