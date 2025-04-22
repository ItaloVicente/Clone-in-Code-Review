		Composite dialogAreaComposite = (Composite) super
				.createDialogArea(parent);
		setToggleButton(createToggleButton(dialogAreaComposite));
		return dialogAreaComposite;
	}

	protected Button createToggleButton(Composite parent) {
		final Button button = new Button(parent, SWT.CHECK | SWT.LEFT);

		GridData data = new GridData(SWT.NONE);
		data.horizontalSpan = 2;
		button.setLayoutData(data);
		button.setFont(parent.getFont());

		button.addSelectionListener(widgetSelectedAdapter(e -> toggleState = button.getSelection()));

		return button;
	}

	protected Button getToggleButton() {
		return toggleButton;
	}

	public IPreferenceStore getPrefStore() {
		return prefStore;
	}

	public String getPrefKey() {
		return prefKey;
	}

	public boolean getToggleState() {
		return toggleState;
	}

	public void setPrefKey(String prefKey) {
		this.prefKey = prefKey;
	}

	public void setPrefStore(IPreferenceStore prefStore) {
		this.prefStore = prefStore;
	}

	protected void setToggleButton(Button button) {
		if (button == null) {
			throw new NullPointerException(
					"A message dialog with toggle may not have a null toggle button.");} //$NON-NLS-1$

		if (!button.isDisposed()) {
			final String text;
			if (toggleMessage == null) {
				text = JFaceResources
						.getString("MessageDialogWithToggle.defaultToggleMessage"); //$NON-NLS-1$
			} else {
				text = toggleMessage;
			}
			button.setText(text);
			button.setSelection(toggleState);
		}

		this.toggleButton = button;
	}

	protected void setToggleMessage(String message) {
		this.toggleMessage = message;

		if ((toggleButton != null) && (!toggleButton.isDisposed())) {
			final String text;
			if (toggleMessage == null) {
				text = JFaceResources
						.getString("MessageDialogWithToggle.defaultToggleMessage"); //$NON-NLS-1$
			} else {
				text = toggleMessage;
			}
			toggleButton.setText(text);
		}
	}

	public void setToggleState(boolean toggleState) {
		this.toggleState = toggleState;

		if ((toggleButton != null) && (!toggleButton.isDisposed())) {
			toggleButton.setSelection(toggleState);
		}
	}

