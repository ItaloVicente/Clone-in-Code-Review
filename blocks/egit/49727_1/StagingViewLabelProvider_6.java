	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			return getImage(element);
		} else {
			return null;
		}
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return getText(element);
		case 1:
			try {
				if (element instanceof StagingFolderEntry) {
					return null;
				}
				StagingEntry stagingEntry = (StagingEntry) element;
				Date modified;

				if (isStaged) {
					IFileRevision fileRevision = GitFileRevision.inIndex(
							stagingEntry.getRepository(),
							stagingEntry.getPath());
					modified = new Date(fileRevision.getTimestamp());
				} else {
					File file = new File(stagingEntry.getRepository()
							.getWorkTree(), stagingEntry.getPath());
					modified = new Date(file.lastModified());
				}

				if (showRelativeDate)
					return RelativeDateFormatter.format(modified);
				else
					return absoluteFormatter.format(modified);
			} catch (Exception e) {
				Activator.handleError(e.getCause().getMessage(),
						e.getCause(), false);
				return null;
			}
		default:
			return null;
		}
	}

	public void setShowRelativeDate(boolean showRelativeDate) {
		this.showRelativeDate = showRelativeDate;
	}

