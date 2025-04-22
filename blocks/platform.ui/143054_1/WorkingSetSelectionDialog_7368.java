	public WorkingSetSelectionDialog(Shell parentShell, boolean multi, String[] workingSetIds) {
		super(parentShell, workingSetIds, true);
		initWorkbenchWindow();

		contentProvider = new ArrayContentProvider();
		labelProvider = new WorkingSetLabelProvider();
		multiSelect = multi;
		if (multiSelect) {
			setTitle(WorkbenchMessages.WorkingSetSelectionDialog_title_multiSelect);
			setMessage(WorkbenchMessages.WorkingSetSelectionDialog_message_multiSelect);
		} else {
			setTitle(WorkbenchMessages.WorkingSetSelectionDialog_title);
			setMessage(WorkbenchMessages.WorkingSetSelectionDialog_message);
		}

	}

