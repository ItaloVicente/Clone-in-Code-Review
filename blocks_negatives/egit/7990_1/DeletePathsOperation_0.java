		Set<IContainer> containers = new HashSet<IContainer>();
		for (IPath path : paths) {
			IResource resource = ResourceUtil.getResourceForLocation(path);
			if (resource != null)
				containers.add(resource.getParent());
		}
		return new MultiRule(containers.toArray(new IResource[containers.size()]));
