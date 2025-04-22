		final Collection<Runnable> runnables = new ArrayList<Runnable>();
		final SortedSet<IResource> resourcesToRefresh = new TreeSet<IResource>(new Comparator<IResource>() {
			private PathComparator pathComparator = new PathComparator();
			@Override
			public int compare(IResource arg0, IResource arg1) {
				return pathComparator.compare(arg0.getFullPath(), arg1.getFullPath());
			}
		});
		processDelta(delta, runnables, resourcesToRefresh);

		IResource currentTopLevelResource = null;
		for (IResource resource : resourcesToRefresh) {
			if (resource == null) {
				continue;
			}
			if (currentTopLevelResource == null
					|| !currentTopLevelResource.getFullPath().isPrefixOf(resource.getFullPath())) {
				currentTopLevelResource = resource;
				runnables.add(getRefreshRunnable(resource));
			}
		}
