					checkFile(file);
				}
			}

			private void checkFile(Object fileElement) {
				MinimizedFileSystemElement file = (MinimizedFileSystemElement) fileElement;
				if (isExportableExtension(file.getFileNameExtension())) {
					List elements = new ArrayList();
					FileSystemElement parent = file.getParent();
					if (selectionMap.containsKey(parent)) {
