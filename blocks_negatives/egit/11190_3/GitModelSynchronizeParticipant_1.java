		} else {
			IResource resource = AdapterUtils.adapt(object, IResource.class);
			if (resource.getType() == IResource.FILE) {
				GitSynchronizeData gsd = gsds.getData(resource.getProject());
				return getFileFromGit(gsd, resource.getLocation());
			}
