		return null;
	}

	protected abstract ITreeContentProvider getFileProvider();

	protected abstract ITreeContentProvider getFolderProvider();

	protected IPath getResourcePath() {
		if (this.containerNameField != null) {
			return getPathFromText(this.containerNameField);
		}

		if (this.initialContainerFieldValue != null && this.initialContainerFieldValue.length() > 0) {
			return new Path(this.initialContainerFieldValue).makeAbsolute();
		}

		return Path.EMPTY;
	}

	protected java.util.List getSelectedResources() {
		return this.selectionGroup.getAllCheckedListItems();
	}

	protected void getSelectedResources(IElementFilter filter, IProgressMonitor monitor) throws InterruptedException {
		this.selectionGroup.getAllCheckedListItems(filter, monitor);
	}

