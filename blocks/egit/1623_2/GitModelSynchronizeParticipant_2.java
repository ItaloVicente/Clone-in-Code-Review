	@Override
	public ICompareInput asCompareInput(Object object) {
		if (object instanceof IFile) {
			IFile file = (IFile) object;
			GitSynchronizeData gsd = gsds.getData(file.getProject());
			if (!gsd.shouldIncludeLocal())
				return getFileFromGit(gsd, file.getLocation());
		}

		return super.asCompareInput(object);
	}

