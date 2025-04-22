		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, IReadmeConstants.PREFERENCE_PAGE_CONTEXT);

		Composite composite_textField = createComposite(parent, 2);
		createLabel(composite_textField, MessageUtil.getString("Text_Field")); //$NON-NLS-1$
		textField = createTextField(composite_textField);
		createPushButton(composite_textField, MessageUtil.getString("Change")); //$NON-NLS-1$

		Composite composite_tab = createComposite(parent, 2);
		createLabel(composite_tab, MessageUtil.getString("Radio_Button_Options")); //$NON-NLS-1$

		tabForward(composite_tab);
		Composite composite_radioButton = createComposite(composite_tab, 1);
		radioButton1 = createRadioButton(composite_radioButton, MessageUtil.getString("Radio_button_1")); //$NON-NLS-1$
		radioButton2 = createRadioButton(composite_radioButton, MessageUtil.getString("Radio_button_2")); //$NON-NLS-1$
		radioButton3 = createRadioButton(composite_radioButton, MessageUtil.getString("Radio_button_3")); //$NON-NLS-1$

		Composite composite_tab2 = createComposite(parent, 2);
		createLabel(composite_tab2, MessageUtil.getString("Check_Box_Options")); //$NON-NLS-1$

		tabForward(composite_tab2);
		Composite composite_checkBox = createComposite(composite_tab2, 1);
		checkBox1 = createCheckBox(composite_checkBox, MessageUtil.getString("Check_box_1")); //$NON-NLS-1$
		checkBox2 = createCheckBox(composite_checkBox, MessageUtil.getString("Check_box_2")); //$NON-NLS-1$
		checkBox3 = createCheckBox(composite_checkBox, MessageUtil.getString("Check_box_3")); //$NON-NLS-1$

		initializeValues();

		return new Composite(parent, SWT.NULL);
	}

	private Label createLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		label.setLayoutData(data);
		return label;
	}

	private Button createPushButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.addSelectionListener(this);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		button.setLayoutData(data);
		return button;
	}

	private Button createRadioButton(Composite parent, String label) {
		Button button = new Button(parent, SWT.RADIO | SWT.LEFT);
		button.setText(label);
		button.addSelectionListener(this);
		GridData data = new GridData();
		button.setLayoutData(data);
		return button;
	}

	private Text createTextField(Composite parent) {
		Text text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		text.addModifyListener(this);
		GridData data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		data.verticalAlignment = GridData.CENTER;
		data.grabExcessVerticalSpace = false;
		text.setLayoutData(data);
		return text;
	}

	@Override
