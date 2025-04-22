			IContainer container = AdapterUtils.adapt(element,
					IContainer.class);
			RepositoryMapping mapping = null;
			if (container != null) {
				mapping = RepositoryMapping.getMapping(container);
			}
			if (container != null && mapping != null
					&& container.equals(mapping.getContainer())) {
				Repository r = mapping.getRepository();
				if (single && r != null && repo != null && r != repo) {
