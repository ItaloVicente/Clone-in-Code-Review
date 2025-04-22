		projectNameField.addListener(SWT.Modify, nameModifyListener);
		BidiUtils.applyBidiProcessing(projectNameField, BidiUtils.BTD_DEFAULT);
	}

	public IPath getLocationPath() {
		return new Path(locationArea.getProjectLocation());
	}

	public URI getLocationURI() {
		return locationArea.getProjectLocationURI();
	}

