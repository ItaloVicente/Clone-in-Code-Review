    private Text messageText;

    private Label messageImageLabel;

    private Composite messageComposite;

    private String lastMessageText;

    private int lastMessageType;

    private CLabel titleLabel;

    /**
     * Create a new instance of the receiver.
     */
    public DialogMessageArea() {
    }

    /**
     * Create the contents for the receiver.
     *
     * @param parent
     *            the Composite that the children will be created in
     */
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

    /**
     * Set the layoutData for the title area. In most cases this will be a copy
     * of the layoutData used in setMessageLayoutData.
     *
     * @param layoutData
     *            the layoutData for the title
     * @see #setMessageLayoutData(Object)
     */
    public void setTitleLayoutData(Object layoutData) {
        titleLabel.setLayoutData(layoutData);
    }

    /**
     * Set the layoutData for the messageArea. In most cases this will be a copy
     * of the layoutData used in setTitleLayoutData.
     *
     * @param layoutData
     *            the layoutData for the message area composite.
     * @see #setTitleLayoutData(Object)
     */
    public void setMessageLayoutData(Object layoutData) {
        messageComposite.setLayoutData(layoutData);
    }

    /**
     * Show the title.
     *
     * @param titleMessage
     *            String for the titke
     * @param titleImage
     *            Image or <code>null</code>
     */
    public void showTitle(String titleMessage, Image titleImage) {
        titleLabel.setImage(titleImage);
        titleLabel.setText(titleMessage);
        restoreTitle();
        return;
    }

    /**
     * Enable the title and disable the message text and image.
     */
    public void restoreTitle() {
        titleLabel.setVisible(true);
        messageComposite.setVisible(false);
        lastMessageText = null;
        lastMessageType = IMessageProvider.NONE;
    }

    /**
     * Show the new message in the message text and update the image. Base the
     * background color on whether or not there are errors.
     *
     * @param newMessage
     *            The new value for the message
     * @param newType
     *            One of the IMessageProvider constants. If newType is
     *            IMessageProvider.NONE show the title.
     * @see IMessageProvider
     */
    public void updateText(String newMessage, int newType) {
        Image newImage = null;
        switch (newType) {
        case IMessageProvider.NONE:
            if (newMessage == null) {
