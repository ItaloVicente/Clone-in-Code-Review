		init(dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, defaultIndex, null, dialogButtonLabels);
	}

	public MessageDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage,
			int dialogImageType, int defaultIndex, String[] dialogButtonLabels, SelectionListener[] linkListeners) {
		super(parentShell);
		init(dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, defaultIndex, linkListeners,
				dialogButtonLabels);
