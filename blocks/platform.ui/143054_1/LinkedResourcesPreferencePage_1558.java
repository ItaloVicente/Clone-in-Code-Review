	public LinkedResourcesPreferencePage() {
		pathVariablesGroup = new PathVariablesGroup(true, IResource.FILE
				| IResource.FOLDER);

		this.noDefaultAndApplyButton();
	}

	@Override
