			setSelectionResult(variableNames);
		} else {
			setSelectionResult(null);
		}
		super.okPressed();
	}

	private void setExtensionResult(
			PathVariablesGroup.PathVariableElement variable, IFileStore extensionFile) {
		IPath extensionPath = new Path(extensionFile.toString());
