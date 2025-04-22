	public GitCreateGeneralProjectPage() {
		super(GitCreateGeneralProjectPage.class.getName());
		setPageComplete(false);
		setTitle(UIText.WizardProjectsImportPage_ImportProjectsTitle);
		setDescription(UIText.WizardProjectsImportPage_ImportProjectsDescription);
	}

	public void setPath(String path) {
		if (path != null)
			myDirectory = new File(path);
		else
			myDirectory = null;
	}

