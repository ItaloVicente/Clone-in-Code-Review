				IContainer container = AdapterUtils.adapt(element, IContainer.class);
				RepositoryMapping mapping = null;
				if (container != null) {
					mapping = RepositoryMapping.getMapping(container);
				}
				if (container != null && mapping != null
						&& container.equals(mapping.getContainer())) {
					Repository r = mapping.getRepository();
					if (single && r != null && repo != null && r != repo)
						return null;
					else if (r != null)
						repo = r;
				} else {
					IWorkingSet workingSet = AdapterUtils.adapt(element,
							IWorkingSet.class);
					if (workingSet != null) {
						for (IAdaptable adaptable : workingSet.getElements()) {
							Repository r = getRepositoryOfProject(adaptable);
							if (single && r != null && repo != null && r != repo)
								return null;
							else if (r != null)
								repo = r;
						}
