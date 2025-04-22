		setMessage(message == null ? WorkbenchMessages.ListSelection_message : message);
		this.okButtonText = okButtonText;
		this.okButtonTextWhenNoSelection = okButtonTextWhenNoSelection;
		this.canCancel = canCancel;
		this.optionalCheckboxText = optionalCheckboxText;
	}

	public static Builder of(Object input) {
		return new Builder(input);
	}

	public static final class Builder {

		private final Object input;
		private IStructuredContentProvider contentProvider;
		private ILabelProvider labelProvider;
		private Object[] initialSelections;
		private String title;
		private String message;
		private String okButtonLabelWhenNoSelection;
		private String okButtonLabelWhenAnySelection;
		private boolean canCancel = true;
		private boolean asSheet = false;
		private String checkboxText;
		boolean checkboxValue = false;

		private Builder(Object input) {
			this.input = input;
		}

		public Builder contentProvider(IStructuredContentProvider contentProvider) {
			this.contentProvider = contentProvider;
			return this;
		}

		public Builder labelProvider(ILabelProvider labelProvider) {
			this.labelProvider = labelProvider;
			return this;
		}

		public Builder preselect(Object... initialSelections) {
			this.initialSelections = initialSelections;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder okButtonText(String text) {
			this.okButtonLabelWhenAnySelection = text;
			return this;
		}

		public Builder okButtonTextWhenNoSelection(String text) {
			this.okButtonLabelWhenNoSelection = text;
			return this;
		}

		public Builder canCancel(boolean canCancel) {
			this.canCancel = canCancel;
			return this;
		}

		public Builder asSheet(boolean asSheet) {
			this.asSheet = asSheet;
			return this;
		}

		public Builder checkboxText(String checkboxText) {
			this.checkboxText = checkboxText;
			return this;
		}

		public Builder checkboxValue(boolean checkboxValue) {
			this.checkboxValue = checkboxValue;
			return this;
		}

		public ListSelectionDialog create(Shell parentShell) {
			ListSelectionDialog dialog = new ListSelectionDialog(parentShell, input,
					contentProvider == null ? ArrayContentProvider.getInstance() : contentProvider,
					labelProvider == null ? new LabelProvider() : labelProvider, message,
					okButtonLabelWhenNoSelection, okButtonLabelWhenAnySelection, canCancel,
					checkboxText);
			int shellStyle = dialog.getShellStyle();
			if (!canCancel) {
				shellStyle &= ~SWT.CLOSE;
			}
			if (asSheet) {
				shellStyle |= SWT.SHEET;
			}
			dialog.setShellStyle(shellStyle);
			dialog.setTitle(title == null ? WorkbenchMessages.ListSelection_title : title);
			if (initialSelections != null) {
				dialog.setInitialSelections(initialSelections);
			}
			dialog.optionalCheckboxValue = checkboxValue;
			return dialog;
