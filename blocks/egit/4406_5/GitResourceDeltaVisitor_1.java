		Collection<IFile> result = new ArrayList<IFile>();
		for (IResource resource : resourcesToUpdate)
			if (resource instanceof IFile)
				result.add((IFile) resource);
		return result;
	}

	public Collection<IResource> getResourcesToUpdate() {
		return resourcesToUpdate;
