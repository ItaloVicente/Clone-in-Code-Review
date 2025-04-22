				radio.setSelection(true);
			}
		}
	}

	private void createCheckListGroup(Composite parent) {
		Group group = new Group(parent, SWT.SHADOW_NONE);
		group.setText("Verify that:");
		group.setLayout(new GridLayout());
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(data);

		int checkListSize = 0;
		for (IDialogTestPass _dialogTest : _dialogTests) {
			int size = _dialogTest.checkListTexts().size();
			if (size > checkListSize) {
				checkListSize = size;
			}
		}
		_checkList = new Button[checkListSize];
		SelectionAdapter selectionAdapter = new SelectionAdapter() {
			@Override
