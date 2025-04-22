		if (isDefaultPresented) {
			preferenceStore.setToDefault(preferenceName);
		} else {
			doStore();
		}
	}


	protected void setButtonLayoutData(Button button) {

		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

		GC gc = new GC(button);
		gc.setFont(button.getFont());
		FontMetrics fontMetrics = gc.getFontMetrics();
		gc.dispose();

		int widthHint = org.eclipse.jface.dialogs.Dialog
				.convertVerticalDLUsToPixels(fontMetrics,
						IDialogConstants.BUTTON_WIDTH);
		data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
				SWT.DEFAULT, true).x);
		button.setLayoutData(data);
	}

	public void setEnabled(boolean enabled, Composite parent) {
		getLabelControl(parent).setEnabled(enabled);
	}
