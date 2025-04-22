		super(parentShell);
		setTitle(WorkbenchMessages.ListSelection_title);
		inputElement = input;
		this.contentProvider = contentProvider;
		this.labelProvider = labelProvider;
		if (message != null) {
			setMessage(message);
		} else {
			setMessage(WorkbenchMessages.ListSelection_message);
		}
