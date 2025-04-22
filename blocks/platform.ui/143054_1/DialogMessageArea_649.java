	private Text messageText;

	private Label messageImageLabel;

	private Composite messageComposite;

	private String lastMessageText;

	private int lastMessageType;

	private CLabel titleLabel;

	public DialogMessageArea() {
	}

	public void createContents(Composite parent) {

		titleLabel = new CLabel(parent, SWT.NONE);
		titleLabel.setFont(JFaceResources.getBannerFont());
		messageComposite = new Composite(parent, SWT.NONE);
		GridLayout messageLayout = new GridLayout();
		messageLayout.numColumns = 2;
		messageLayout.marginWidth = 0;
		messageLayout.marginHeight = 0;
		messageLayout.makeColumnsEqualWidth = false;
		messageComposite.setLayout(messageLayout);
		messageImageLabel = new Label(messageComposite, SWT.NONE);
		messageImageLabel.setImage(JFaceResources
				.getImage(Dialog.DLG_IMG_MESSAGE_INFO));
		messageImageLabel.setLayoutData(new GridData(
				GridData.VERTICAL_ALIGN_CENTER));

		messageText = new Text(messageComposite, SWT.NONE);
		messageText.setEditable(false);

		GridData textData = new GridData(GridData.GRAB_HORIZONTAL
				| GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
		messageText.setLayoutData(textData);

	}

	public void setTitleLayoutData(Object layoutData) {
		titleLabel.setLayoutData(layoutData);
	}

	public void setMessageLayoutData(Object layoutData) {
		messageComposite.setLayoutData(layoutData);
	}

	public void showTitle(String titleMessage, Image titleImage) {
		titleLabel.setImage(titleImage);
		titleLabel.setText(titleMessage);
		restoreTitle();
		return;
	}

	public void restoreTitle() {
		titleLabel.setVisible(true);
		messageComposite.setVisible(false);
		lastMessageText = null;
		lastMessageType = IMessageProvider.NONE;
	}

	public void updateText(String newMessage, int newType) {
		Image newImage = null;
		switch (newType) {
		case IMessageProvider.NONE:
			if (newMessage == null) {
