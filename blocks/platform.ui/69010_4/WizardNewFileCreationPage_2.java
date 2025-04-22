			IFile newFileHandle;
			try {
				newFileHandle = createFileHandle(newFolderPath);
			} catch (Exception e) {
				return false;
			}
