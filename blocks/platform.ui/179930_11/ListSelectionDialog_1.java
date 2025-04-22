		this(parentShell, input, contentProvider, labelProvider, message, null, null, true, null);
	}

	private ListSelectionDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String message, String okButtonText, String okButtonTextWhenNoSelection,
			boolean canCancel, String optionalCheckboxText) {
