
			IContainer container = AdapterUtils.adapt(firstElement, IContainer.class);
			RepositoryMapping mapping = null;
			if (container != null) {
				mapping = RepositoryMapping.getMapping(container);
			}
			if (container != null && mapping != null) {
				if (container.equals(mapping.getContainer())) {
					return false;
				} else {
					return testRepositoryProperties(mapping.getRepository(),
							args);
				}
			}

