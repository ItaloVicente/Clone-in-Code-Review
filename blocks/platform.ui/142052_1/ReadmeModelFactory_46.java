		Object object = structured.getFirstElement();
		if (object instanceof IFile) {
			IFile file = (IFile) object;
			String extension = file.getFileExtension();
			if (extension != null && extension.equals(IReadmeConstants.EXTENSION)) {
				return getSections(file);
			}
		}
