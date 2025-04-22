			throws IOException, CoreException {
		if (!resolveLinks && resource.isLinked(IResource.DEPTH_INFINITE)) {
			return;
		}
		ZipEntry newEntry = new ZipEntry(destinationPath);
		write(newEntry, resource);
	}
