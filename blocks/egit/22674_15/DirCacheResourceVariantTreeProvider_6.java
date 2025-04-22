				if (useWorkspace) {
					sourceCache.setVariant(resource, new GitLocalResourceVariant(
							resource));
				} else {
					sourceCache.setVariant(resource,
							IndexResourceVariant.create(repository, entry));
				}
