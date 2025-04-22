	public boolean isWorkTreeRoot(@NonNull
	final IResource resource) {
		return resource.getType() == IResource.PROJECT
				|| resource.equals(getContainer());
	}

