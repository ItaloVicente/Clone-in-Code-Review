	public SaveableComparison getSaveable() {
		return null;
	}

	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		configuration.setLeftLabel(getFileRevisionLabel((FileRevisionTypedElement)getLeft()));
		configuration.setRightLabel(getFileRevisionLabel((FileRevisionTypedElement)getRight()));

	}

	private String getFileRevisionLabel(FileRevisionTypedElement element) {
		return NLS.bind(UIText.GitCompareFileRevisionEditorInput_RevisionLabel,
				new Object[]{element.getName(),
				CompareUtils.truncatedRevision(element.getContentIdentifier()), element.getAuthor()});
	}

	public String getFullPath() {
		return null;
	}

	public boolean isCompareInputFor(Object object) {
		return false;
	}

