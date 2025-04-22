			int overwrite = d.open();
			switch (overwrite) {
			case 0: // Yes
				break;
			case 1: // No
				return;
			case 2: // Cancel
			default:
				cancelPressed();
				return;
			}
		}

		result = path;
		close();
	}

	protected void setDialogComplete(boolean value) {
		okButton.setEnabled(value);
	}

	public void setOriginalFile(IFile originalFile) {
		this.originalFile = originalFile;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	private boolean validatePage() {
		if (!resourceGroup.areAllValuesValid()) {
			if (!resourceGroup.getResource().equals("")) { //$NON-NLS-1$
