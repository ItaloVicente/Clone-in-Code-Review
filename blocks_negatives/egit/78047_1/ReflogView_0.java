		if (selectedRepo == null) {
			IResource adapted = AdapterUtils.adapt(first, IResource.class);
			if (adapted != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(adapted);
				if (mapping != null) {
					selectedRepo = mapping.getRepository();
				}
			}
		}
