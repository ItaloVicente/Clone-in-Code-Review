		storeValues();
		ReadmePlugin.getDefault().savePluginPreferences();
		return true;
	}

	private void storeValues() {
		IPreferenceStore store = getPreferenceStore();
		store.setValue(IReadmeConstants.PRE_CHECK1, checkBox1.getSelection());
		store.setValue(IReadmeConstants.PRE_CHECK2, checkBox2.getSelection());
		store.setValue(IReadmeConstants.PRE_CHECK3, checkBox3.getSelection());

		int choice = 1;

		if (radioButton2.getSelection())
			choice = 2;
		else if (radioButton3.getSelection())
			choice = 3;

		store.setValue(IReadmeConstants.PRE_RADIO_CHOICE, choice);
		store.setValue(IReadmeConstants.PRE_TEXT, textField.getText());
	}

	private void tabForward(Composite parent) {
		Label vfiller = new Label(parent, SWT.LEFT);
		GridData gridData = new GridData();
		gridData = new GridData();
		gridData.horizontalAlignment = GridData.BEGINNING;
		gridData.grabExcessHorizontalSpace = false;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessVerticalSpace = false;
		vfiller.setLayoutData(gridData);
	}

	@Override
