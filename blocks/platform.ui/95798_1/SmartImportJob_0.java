		for (IProject other : container.getWorkspace().getRoot().getProjects()) {
			if (other.getLocation() != null && !container.getLocation().equals(other.getLocation())
					&& container.getLocation().isPrefixOf(other.getLocation())) {
				excludedPaths.add(other.getLocation());
			}
		}
