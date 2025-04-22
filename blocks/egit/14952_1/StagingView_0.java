				IResource resource = AdapterUtils.adapt(element,
						IResource.class);
				if (resource != null) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(resource);
					if (mapping != null
							&& mapping.getRepository() == currentRepository) {
						String path = mapping.getRepoRelativePath(resource);
						if ("".equals(path)) //$NON-NLS-1$
							addPaths.add("."); //$NON-NLS-1$
						else
							addPaths.add(path);
