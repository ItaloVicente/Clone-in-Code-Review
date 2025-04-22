	public ListSelectionDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String title, String message, String okButtonLabelWhenNoSelection,
			String okButtonLabelWhenAnySelection) {
		this(parentShell, input, contentProvider, labelProvider, title, message, okButtonLabelWhenNoSelection,
				okButtonLabelWhenAnySelection, true, false, null, false);
	}

	public ListSelectionDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String title, String message, String okButtonLabelWhenNoSelection,
			String okButtonLabelWhenAnySelection, boolean canCancel, boolean asSheet, String optionalCheckboxLabel,
			boolean optionalCheckboxDefaultValue) {
		super(parentShell);
		int shellStyle = getShellStyle();
		if (!canCancel) {
			shellStyle &= ~SWT.CLOSE;
		}
		if (asSheet) {
			shellStyle |= SWT.SHEET;
		}
		setShellStyle(shellStyle);
		setTitle(title == null ? WorkbenchMessages.ListSelection_title : title);
		setMessage(message == null ? WorkbenchMessages.ListSelection_message : message);
		inputElement = input;
		this.contentProvider = contentProvider;
		this.labelProvider = labelProvider;
		this.okButtonLabelWhenNoSelection = okButtonLabelWhenNoSelection;
		this.okButtonLabelWhenAnySelection = okButtonLabelWhenAnySelection;
		this.canCancel = canCancel;
		this.optionalCheckboxLabel = optionalCheckboxLabel;
		this.optionalCheckboxValue = optionalCheckboxDefaultValue;
	}

