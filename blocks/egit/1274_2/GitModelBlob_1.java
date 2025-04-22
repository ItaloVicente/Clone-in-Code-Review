	public SaveableComparison getSaveable() {
		return null;
	}

	public void prepareInput(CompareConfiguration configuration,
			IProgressMonitor monitor) throws CoreException {
		configuration.setLeftLabel(getFileRevisionLabel(getLeft()));
		configuration.setRightLabel(getFileRevisionLabel(getRight()));

	}

	private String getFileRevisionLabel(ITypedElement element) {
		if (element instanceof FileRevisionTypedElement) {
			FileRevisionTypedElement castElement = (FileRevisionTypedElement)element;
			return NLS.bind(UIText.GitCompareFileRevisionEditorInput_RevisionLabel,
					new Object[]{element.getName(),
					CompareUtils.truncatedRevision(castElement.getContentIdentifier()),
					castElement.getAuthor()});

		}
		else
			return element.getName();
	}

	public String getFullPath() {
		return null;
	}

	public boolean isCompareInputFor(Object object) {
		return false;
	}

