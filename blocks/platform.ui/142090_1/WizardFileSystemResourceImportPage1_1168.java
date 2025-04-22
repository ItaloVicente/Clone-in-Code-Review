				if (o instanceof MinimizedFileSystemElement) {
					MinimizedFileSystemElement element = (MinimizedFileSystemElement) o;
					return element.getFiles(
							fileSystemStructureProvider).getChildren(
							element);
				}
				return new Object[0];
			}
		};
	}

	protected MinimizedFileSystemElement getFileSystemTree() {

		File sourceDirectory = getSourceDirectory();
		if (sourceDirectory == null) {
