		List<Entry> entries = new ArrayList<Entry>(resources.length);

		boolean inheritableResourceFilter = addFilteredEntries(
				hasInheritedResourceFilters, resources, entries);

		for (IResource resource : resources)
			entries.add(new ResourceEntry(resource, inheritableResourceFilter));

		return entries.toArray(new Entry[entries.size()]);
	}

	private boolean addFilteredEntries(
			final boolean hasInheritedResourceFilters,
			final IResource[] memberResources, final List<Entry> entries) {
		boolean inheritableResourceFilter = hasInheritedResourceFilters;
		IResourceFilterDescription[] filters;
		try {
			filters = node.getFilters();
		} catch (CoreException e) {
			filters = new IResourceFilterDescription[] {};
		}

		if (filters.length != 0 || hasInheritedResourceFilters) {
			if (!inheritableResourceFilter) {
				for (IResourceFilterDescription filter : filters) {
					boolean inheritable = (filter.getType() & IResourceFilterDescription.INHERITABLE) != 0;
					if (inheritable)
						inheritableResourceFilter = true;
				}
			}

			Set<File> resourceEntries = new HashSet<File>();
			for (IResource resource : memberResources) {
				IPath location = resource.getLocation();
				if (location != null)
					resourceEntries.add(location.toFile());
			}

			IPath containerLocation = node.getLocation();
			if (containerLocation != null) {
				File folder = containerLocation.toFile();
				File[] children = folder.listFiles();
				for (File child : children) {
					if (resourceEntries.contains(child))
						continue;

					IPath childLocation = new Path(child.getAbsolutePath());
					IWorkspaceRoot root = node.getWorkspace().getRoot();
					IContainer container = root.getContainerForLocation(childLocation);
					if (container != null && container.isAccessible())
						entries.add(new ResourceEntry(container, false));
					else
						entries.add(new FileEntry(child, FS.DETECTED));
				}
			}
		}
		return inheritableResourceFilter;
