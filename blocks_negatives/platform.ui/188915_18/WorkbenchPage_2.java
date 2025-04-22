		try {
			IPath path = ((IPathEditorInput) editorInput).getPath();
			File file = new File(path.toOSString());
			return file.length() > maxFileSize;
		} catch (Exception e) {
			return false;
		}
	}
