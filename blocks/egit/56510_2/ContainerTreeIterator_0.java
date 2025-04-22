		for (IResource resource : resources) {
			if (resource.isLinked()) {
				continue;
			}
			if (resource.getName().equals(Constants.DOT_GIT)) {
				continue;
			}
			entries.add(new ResourceEntry(resource, inheritableResourceFilter));
		}
