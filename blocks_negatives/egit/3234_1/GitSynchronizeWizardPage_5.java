				if (element instanceof IProject) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping((IResource) element);
					Set<String> refs = mapping.getRepository().getAllRefs()
							.keySet();
					branchesEditor.setItems(refs
							.toArray(new String[refs.size()]));
				} else {
					Set<String> refs = ((Repository) element).getAllRefs()
							.keySet();
					branchesEditor.setItems(refs
							.toArray(new String[refs.size()]));
				}
				return branchesEditor;
