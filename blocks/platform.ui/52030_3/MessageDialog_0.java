		init(dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, defaultIndex, dialogButtonLabels);
	}

	public MessageDialog(Shell parentShell, String dialogTitle, Image dialogTitleImage, String dialogMessage,
			int dialogImageType, int defaultIndex, String... dialogButtonLabels) {
		super(parentShell);
		init(dialogTitle, dialogTitleImage, dialogMessage, dialogImageType, defaultIndex, dialogButtonLabels);
	}

	private void init(String dialogTitle, Image dialogTitleImage, String dialogMessage, int dialogImageType,
			int defaultIndex, String... dialogButtonLabels) {
		this.title = dialogTitle;
