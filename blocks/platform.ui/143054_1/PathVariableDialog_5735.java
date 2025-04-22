				}
			}
			return resolveValue;
		}
		return variableValue;
	}

	private void variableNameModified() {
		variableName = variableNameField.getText();
		validationStatus = IMessageProvider.NONE;
		okButton.setEnabled(validateVariableName() && validateVariableValue() && variableValue.length() != 0);
		nameEntered = true;
	}

	private void variableValueModified() {
		variableValue = variableValueField.getText().trim();
		validationStatus = IMessageProvider.NONE;
		okButton.setEnabled(validateVariableValue() && validateVariableName());
		locationEntered = true;
		if (variableResolvedValueField != null)
			variableResolvedValueField.setText(TextProcessor.process(getVariableResolvedValue()));
	}

	private void selectFolder() {
		DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
		dialog.setText(IDEWorkbenchMessages.PathVariableDialog_selectFolderTitle);
		dialog.setMessage(IDEWorkbenchMessages.PathVariableDialog_selectFolderMessage);
		String filterPath = getVariableResolvedValue();
		dialog.setFilterPath(filterPath);
		String res = dialog.open();
		if (res != null) {
			variableValue = new Path(res).makeAbsolute().toOSString();
			variableValueField.setText(variableValue);
		}
	}

	private void selectFile() {
		FileDialog dialog = new FileDialog(getShell(), SWT.SHEET);
		dialog.setText(IDEWorkbenchMessages.PathVariableDialog_selectFileTitle);
		String filterPath = getVariableResolvedValue();
		dialog.setFilterPath(filterPath);
		String res = dialog.open();
		if (res != null) {
			variableValue = new Path(res).makeAbsolute().toOSString();
			variableValueField.setText(variableValue);
		}
	}

	private void selectVariable() {
		PathVariableSelectionDialog dialog = new PathVariableSelectionDialog(
				getShell(), IResource.FILE | IResource.FOLDER);
		dialog.setResource(currentResource);
		if (dialog.open() == IDialogConstants.OK_ID) {
			String[] variableNames = (String[]) dialog.getResult();
			if (variableNames != null && variableNames.length == 1) {
				String newValue = variableNames[0];
				IPath path = Path.fromOSString(newValue);
				if (operationMode != EDIT_LINK_LOCATION && currentResource != null && !path.isAbsolute() && path.segmentCount() > 0) {
					path = buildVariableMacro(path);
					newValue = path.toOSString();
				}
				variableValue = newValue;
				variableValueField.setText(newValue);
			}
		}
	}
