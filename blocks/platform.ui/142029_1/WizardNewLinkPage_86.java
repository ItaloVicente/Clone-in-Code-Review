			}
			selection = dialog.open();
		} else {
			DirectoryDialog dialog = new DirectoryDialog(getShell(), SWT.SHEET);
			if (store != null) {
				if (!store.fetchInfo().isDirectory()) {
					linkTargetName = store.getParent().getName();
				}
				if (linkTargetName != null) {
					dialog.setFilterPath(linkTargetName);
				}
			}
			dialog.setMessage(IDEWorkbenchMessages.WizardNewLinkPage_targetSelectionLabel);
			selection = dialog.open();
		}
		if (selection != null) {
			linkTargetField.setText(selection);
		}
	}

	private void handleVariablesButtonPressed() {
		PathVariableSelectionDialog dialog = new PathVariableSelectionDialog(getShell(), type);

		if (dialog.open() == IDialogConstants.OK_ID) {
			String[] variableNames = (String[]) dialog.getResult();

			if (variableNames != null) {
				IPathVariableManager pathVariableManager = ResourcesPlugin.getWorkspace().getPathVariableManager();
				IPath path = pathVariableManager.getValue(variableNames[0]);

				if (path != null) {
					linkTargetField.setText(path.toOSString());
				}
			}
		}
	}

	public void setContainer(IContainer container) {
		this.container = container;
	}

	public void setLinkTarget(String target) {
		initialLinkTarget = target;
		if (linkTargetField != null && linkTargetField.isDisposed() == false) {
			linkTargetField.setText(target);
		}
	}

	private boolean validateFileType(IFileStore linkTargetStore) {
		boolean valid = true;

		if (type == IResource.FILE && linkTargetStore.fetchInfo().isDirectory()) {
			setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetNotFile);
			valid = false;
		} else if (type == IResource.FOLDER && !linkTargetStore.fetchInfo().isDirectory()) {
			setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetNotFolder);
			valid = false;
		}
		return valid;
	}

	private boolean validateLinkTargetName(String linkTargetName) {
		boolean valid = true;

		if ("".equals(linkTargetName)) {//$NON-NLS-1$
			setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetEmpty);
			valid = false;
		} else {
			IPath path = new Path("");//$NON-NLS-1$
			if (path.isValidPath(linkTargetName) == false) {
				setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetInvalid);
				valid = false;
			}
		}
		return valid;
	}

	private boolean validatePage() {
		boolean valid = true;
		IWorkspace workspace = IDEWorkbenchPlugin.getPluginWorkspace();

		if (createLink) {
			String linkTargetName = linkTargetField.getText();

			valid = validateLinkTargetName(linkTargetName);
			if (valid) {
				IFileStore linkTargetFile = IDEResourceInfoUtils.getFileStore(linkTargetName);
				if (linkTargetFile == null || !linkTargetFile.fetchInfo().exists()) {
					setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetNonExistent);
					valid = false;
				} else {
					IStatus locationStatus = workspace.validateLinkLocation(container, new Path(linkTargetName));

					if (locationStatus.isOK() == false) {
						setErrorMessage(IDEWorkbenchMessages.WizardNewLinkPage_linkTargetLocationInvalid);
						valid = false;
					} else {
						valid = validateFileType(linkTargetFile);
					}
				}
			}
		}
		if (valid) {
			setMessage(null);
			setErrorMessage(null);
		}
		return valid;
	}
