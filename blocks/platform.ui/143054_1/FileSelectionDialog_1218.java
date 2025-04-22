				if (o instanceof FileSystemElement) {
					return ((FileSystemElement) o).getFiles().getChildren(o);
				}
				return new Object[0];
			}
		};
	}

	private ITreeContentProvider getFolderProvider() {
		return new WorkbenchContentProvider() {
			@Override
