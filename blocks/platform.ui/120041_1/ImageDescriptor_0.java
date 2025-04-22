	private static ImageDescriptor getReplacement(Class<?> location, String filename) {
		ImageDescriptor replacement = null;
		if (filename.equals("copy_edit.png")) //$NON-NLS-1$
			return createFromFile(location, "cut_edit.png"); //$NON-NLS-1$
		return replacement;
	}

