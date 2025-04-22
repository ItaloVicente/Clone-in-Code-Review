	public IProject[] getDirectChildrenProjects(IContainer container) {
		Set<IProject> res = new HashSet<>();
		IPath containerLocation = container.getLocation();
		boolean considerNextProjects = false;
		for (Entry<IPath, IProject> entry : locationsToProjects.entrySet()) {
			if (entry.getValue().equals(container.getProject())) {
				considerNextProjects = true;
			} else if (considerNextProjects) {
				if (containerLocation.isPrefixOf(entry.getKey())) {
					if (entry.getKey().segmentCount() == containerLocation.segmentCount() + 1) {
						res.add(entry.getValue());
					}
				} else { // moved to another branch, not worth continuing
					break;
				}
			}
		}
		return res.toArray(new IProject[res.size()]);
	}

