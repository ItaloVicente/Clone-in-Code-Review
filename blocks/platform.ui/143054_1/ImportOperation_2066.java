		IPath sourcePath = new Path(provider.getFullPath(source));
		IPath destContainerPath = pathname.removeLastSegments(1);
		IPath relativePath = destContainerPath.removeFirstSegments(
				sourcePath.segmentCount()).setDevice(null);
		return createContainersFor(relativePath);

	}

	IFile getFile(IResource resource) {
