		ICompareInput compareInput = super.asCompareInput(object);

		if(compareInput != null) {

			ITypedElement left = compareInput.getLeft();
			if(left instanceof ResourceNode) {
				IResource resource = ((ResourceNode) left).getResource();
				if (resource.getType() == IResource.FILE) {
					GitSynchronizeData gsd = gsds
							.getData(resource.getProject());
					if (!gsd.shouldIncludeLocal())
						return getFileFromGit(gsd, resource.getLocation());
				}
			}
		} else {
			IResource resource = AdapterUtils.adapt(object, IResource.class);
			if (resource.getType() == IResource.FILE) {
				GitSynchronizeData gsd = gsds.getData(resource.getProject());
				return getFileFromGit(gsd, resource.getLocation());
			}
