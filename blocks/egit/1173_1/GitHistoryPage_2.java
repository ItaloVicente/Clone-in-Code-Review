					final RepositoryMapping mapping = RepositoryMapping
							.getMapping(resource.getProject());
					final String gitPath = mapping
							.getRepoRelativePath(resource);
					IStructuredSelection selection = (IStructuredSelection) event
							.getSelection();
