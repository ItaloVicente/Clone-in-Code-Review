	public boolean isWorkTreeRoot(@NonNull
	final IResource resource, @Nullable Repository subRepo) {
		if (isNonWorkspace(resource)) {
			return false;
		}
		IPath resourceLocation = resource.getLocation();
		if (resourceLocation == null) {
			return false;
		}
		File workTree = subRepo != null ? subRepo.getWorkTree() : getWorkTree();
		if (workTree == null) {
			return false;
		}
		IPath workTreeLocation = new Path(workTree.toString());
		return workTreeLocation.equals(resourceLocation);
	}

