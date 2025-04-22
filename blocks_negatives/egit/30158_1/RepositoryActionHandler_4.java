	/**
	 * @param selection
	 * @return the resources in the selection
	 */
	private IResource[] getSelectedResources(IStructuredSelection selection) {
		Set<IResource> result = new LinkedHashSet<IResource>();
		for (Object o : selection.toList()) {
			IResource resource = AdapterUtils.adapt(o, IResource.class);
			if (resource != null)
				result.add(resource);
			else
				result.addAll(extractResourcesFromMapping(o));
		}
		return result.toArray(new IResource[result.size()]);
	}

	private static IPath[] getSelectedLocations(IStructuredSelection selection) {
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

	private static List<IResource> extractResourcesFromMapping(Object o) {
		ResourceMapping mapping = AdapterUtils.adapt(o,
				ResourceMapping.class);
		if (mapping != null) {
			ResourceTraversal[] traversals;
			try {
				traversals = mapping.getTraversals(null, null);
				for (ResourceTraversal traversal : traversals) {
					IResource[] resources = traversal.getResources();
					return Arrays.asList(resources);
				}
			} catch (CoreException e) {
				Activator.logError(e.getMessage(), e);
			}
		}
		return Collections.emptyList();
	}

