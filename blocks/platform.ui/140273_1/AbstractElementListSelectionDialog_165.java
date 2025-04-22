				handleWidgetSelected();
			}
		});

		fFilteredList = list;

		return list;
	}

	private void handleWidgetSelected() {
		Object[] newSelection = fFilteredList.getSelection();

		if (newSelection.length != fSelection.length) {
			fSelection = newSelection;
			handleSelectionChanged();
		} else {
			for (int i = 0; i != newSelection.length; i++) {
				if (!newSelection[i].equals(fSelection[i])) {
					fSelection = newSelection;
					handleSelectionChanged();
					break;
				}
			}
		}
	}

	protected Text createFilterText(Composite parent) {
		Text text = new Text(parent, SWT.BORDER);

		GridData data = new GridData();
		data.grabExcessVerticalSpace = false;
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		text.setLayoutData(data);
		text.setFont(parent.getFont());

		text.setText((fFilter == null ? "" : fFilter)); //$NON-NLS-1$

		Listener listener = e -> fFilteredList.setFilter(fFilterText.getText());
		text.addListener(SWT.Modify, listener);

		text.addKeyListener(new KeyListener() {
			@Override
