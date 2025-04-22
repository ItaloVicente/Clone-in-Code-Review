	/**
	 * Adds all markers in the given set of resources to the given list
	 *
	 * @param resultList
	 * @param resources
	 * @param markerTypeId
	 * @param depth
	 * @throws CoreException
	 */
	private List findMarkers(IResource[] resources, int depth, int limit,
			IProgressMonitor mon, boolean ignoreExceptions)
			throws CoreException {
		if (resources == null) {
			return Collections.EMPTY_LIST;
		}

		List resultList = new ArrayList(resources.length * 2);


		HashSet typesToSearch = new HashSet(selectedTypes.size());

		HashSet includeAllSubtypes = new HashSet(selectedTypes.size());

		typesToSearch.addAll(selectedTypes);

		Iterator iter = selectedTypes.iterator();

		while (iter.hasNext()) {
			MarkerType type = (MarkerType) iter.next();

			Collection subtypes = Arrays.asList(type.getAllSubTypes());

			if (selectedTypes.containsAll(subtypes)) {
				typesToSearch.removeAll(subtypes);

				includeAllSubtypes.add(type);
			}
		}

		mon.beginTask(MarkerMessages.MarkerFilter_searching, typesToSearch
				.size()
				* resources.length);

		HashSet resourcesToSearch = new HashSet();

		for (int idx = 0; idx < resources.length; idx++) {
			IResource next = resources[idx];

			if (!next.exists()) {
				continue;
			}

			if (resourcesToSearch.contains(next)) {
				mon.worked(typesToSearch.size());
			} else {
				resourcesToSearch.add(next);
			}
		}

		for (int resourceIdx = 0; resourceIdx < resources.length; resourceIdx++) {
			iter = typesToSearch.iterator();

			IResource resource = resources[resourceIdx];

			if (!resource.isAccessible()) {
				continue;
			}

			if (depth == IResource.DEPTH_INFINITE) {
				IResource parent = resource.getParent();
				boolean found = false;
				while (parent != null) {
					if (resourcesToSearch.contains(parent)) {
						found = true;
					}

					parent = parent.getParent();
				}

				if (found) {
					continue;
				}
			}

			while (iter.hasNext()) {
				MarkerType markerType = (MarkerType) iter.next();

				IMarker[] markers = resource.findMarkers(markerType.getId(),
						includeAllSubtypes.contains(markerType), depth);

				mon.worked(1);

				for (int idx = 0; idx < markers.length; idx++) {
					ConcreteMarker marker;
					try {
						marker = MarkerList.createMarker(markers[idx]);
					} catch (CoreException e) {
						if (ignoreExceptions) {
							continue;
						}
						throw e;

					}

					if (limit != -1 && resultList.size() >= limit) {
						return resultList;
					}

					if (selectMarker(marker)) {
						resultList.add(marker);
					}
				}
			}
		}

		mon.done();

		return resultList;
	}

