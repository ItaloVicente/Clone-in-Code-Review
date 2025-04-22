	public static ISchedulingRule getRuleForContainers(Collection<IPath> paths) {
		Set<IContainer> containers = new HashSet<IContainer>();
		for (IPath path : paths) {
			IResource resource = ResourceUtil.getResourceForLocation(path);
			if (resource != null) {
				IContainer parent = resource.getParent();
				if (parent != null)
					containers.add(parent);
			}
		}
		return new MultiRule(containers.toArray(new IResource[containers.size()]));
	}

