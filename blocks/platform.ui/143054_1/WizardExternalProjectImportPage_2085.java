				handleLocationBrowseButtonPressed();
			}
		});

		locationPathField.addListener(SWT.Modify, locationModifyListener);
	}

	public IPath getLocationPath() {

		return new Path(getProjectLocationFieldValue());
	}

	public IProject getProjectHandle() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName());
	}

	public String getProjectName() {
		return getProjectNameFieldValue();
	}

	private String getProjectNameFieldValue() {
		if (projectNameField == null) {
