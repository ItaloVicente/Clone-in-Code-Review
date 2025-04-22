				checkYesEnable();
			}
		};
		for (int i = 0; i < checkListSize; i++) {
			_checkList[i] = new Button(group, SWT.CHECK);
			_checkList[i].addSelectionListener(selectionAdapter);
			data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			data.grabExcessHorizontalSpace = true;
			_checkList[i].setLayoutData(data);
		}
	}

	private void checkYesEnable() {
		boolean enable = true;
