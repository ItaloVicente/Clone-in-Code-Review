		boolean containsAtLeastOnePath = false;
		for (final String p : resourcePaths) {
			if (p != null) {
				containsAtLeastOnePath = true;
				break;
			}
		}
		if (!containsAtLeastOnePath)
			return decoratableResources;

		final TreeWalk treeWalk = createThreeWayTreeWalk(mapping, resourcePaths);
		if (treeWalk != null)
			while (treeWalk.next()) {
				i = resourcePaths.indexOf(treeWalk.getPathString());
				if (i != -1) {
					try {
						if (decoratableResources[i] == null)
							decoratableResources[i] = decorateResource(
									new DecoratableResource(resources[i]),
									treeWalk);
					} catch (IOException e) {
					}
				}
			}
