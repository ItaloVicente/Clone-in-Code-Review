				if (element instanceof IProject) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping((IResource) element);
					String branch = selectedBranches.get(mapping
							.getRepository());
				} else {
					String branch = selectedBranches.get(element);
				}
