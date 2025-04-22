		java.nio.file.Path gitDir = repository != null
				? repository.getDirectory().toPath() : null;
		for (IResource resource : resources) {
			if (resource.isLinked()) {
				continue;
			}
			if (gitDir != null && resource.getLocation().toFile().toPath()
					.startsWith(gitDir)) {
				continue;
			}
			entries.add(new ResourceEntry(resource, inheritableResourceFilter));
		}
