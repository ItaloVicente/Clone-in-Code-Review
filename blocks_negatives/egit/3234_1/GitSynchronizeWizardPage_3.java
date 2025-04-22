				if (element instanceof IProject) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping((IResource) element);
					Repository repository = mapping.getRepository();
					selectedBranches.put(repository, branch);
					treeViewer.refresh(repository, true);
				} else {
					selectedBranches.put((Repository) element, branch);
					treeViewer.refresh(element, true);
				}
