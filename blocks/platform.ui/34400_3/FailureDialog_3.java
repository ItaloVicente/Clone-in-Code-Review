	private Text _text;

	private String _log;

	private int SIZING_TEXT_WIDTH = 400;

	private int SIZING_TEXT_HEIGHT = 200;

	public FailureDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Dialog Test Failed");
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "&OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Label label = new Label(composite, SWT.WRAP);
		label.setText("&Enter a note regarding the failure:");

		_text = new Text(composite, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		_text.setFont(JFaceResources.getFontRegistry().get(JFaceResources.TEXT_FONT));
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = SIZING_TEXT_WIDTH;
		data.heightHint = SIZING_TEXT_HEIGHT;
		_text.setLayoutData(data);

		return composite;
	}

	@Override
	protected void okPressed() {
		_log = _text.getText();
		super.okPressed();
	}

	String getText() {
		return (_log == null) ? "Empty entry." : _log;
	}

	void setText(String text) {
		_text.setText(text);
	}

	@Override
	public String toString() {
		return getText();
	}
