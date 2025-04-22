	private IPath[] getSelectedLocations(IStructuredSelection selection) {
		Set<IPath> result = new LinkedHashSet<IPath>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adapt(o, IResource.class);
			if (resource != null) {
				IPath location = resource.getLocation();
				if (location != null)
					result.add(location);
			} else {
				IPath location = AdapterUtils.adapt(o, IPath.class);
				if (location != null)
					result.add(location);
				else
					for (IResource r : extractResourcesFromMapping(o)) {
						IPath l = r.getLocation();
						if (l != null)
							result.add(l);
					}
			}
		}
		return result.toArray(new IPath[result.size()]);
	}

	private List<IResource> extractResourcesFromMapping(Object o) {
