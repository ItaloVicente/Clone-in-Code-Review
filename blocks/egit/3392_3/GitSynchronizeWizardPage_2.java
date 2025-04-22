				RepositoryMapping mapping = RepositoryMapping
						.getMapping((IResource) element);
				String branch = selectedBranches.get(mapping
						.getRepository());
				CCombo combo = (CCombo) branchesEditor.getControl();
				int index = branch == null ? 0 : combo.indexOf(branch);

				return Integer.valueOf(index);
