		Dialog.applyDialogFont(main);
		setControl(main);
	}

	protected void setRelativePath(String directory) {
		IPath folderPath = new Path(directory).setDevice(null);
		IPath workdirPath = new Path(this.selectedRepository.getWorkTree()
				.getPath()).setDevice(null);
		if (!workdirPath.isPrefixOf(folderPath)) {
			MessageDialog
					.openError(
							getShell(),
							"Wrong path", "The selected path is not a child of the Repository working tree"); //$NON-NLS-1$ //$NON-NLS-2$
			return;
		}
		relPath.setText(folderPath.removeFirstSegments(
				workdirPath.segmentCount()).toString());
	}

	protected void setRepository(Repository repository) {
		if (repository == this.selectedRepository)
			return;
		this.selectedRepository = repository;
		relPath.setText(""); //$NON-NLS-1$
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			updateControls();
