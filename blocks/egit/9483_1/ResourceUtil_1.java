		URI uri = URIUtil.toURI(location);
		IFile[] files = root.findFilesForLocationURI(uri);
		for (IFile file : files)
			if (file.exists())
				return file;
		return null;
