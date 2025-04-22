		return NLS.bind("{0} ({1})", projectName, path); //$NON-NLS-1$
	}

	public IProjectDescription getProjectDescription() {
		return description;
	}

	public void setProjectDescription(IProjectDescription description) {
		this.description = description;
	}

	public File getProjectSystemFile() {
		return projectSystemFile;
