		viewer.setContentProvider(new WorkbenchContentProvider() {

			public Object[] getElements(Object element) {
				if (getLayout() == FLAG_LAYOUT_TREE) {
					Map<Repository, RepositoryMatch> repos = new HashMap<Repository, RepositoryMatch>();
					for (Object inputElement : getInput().getElements()) {
						RepositoryCommit commit = (RepositoryCommit) inputElement;
						RepositoryMatch match = repos.get(commit
								.getRepository());
						if (match == null) {
							match = new RepositoryMatch(commit.getRepository());
							repos.put(commit.getRepository(), match);
						}
						match.addCommit(commit);
					}
					return repos.values().toArray();
				}
				return super.getElements(element);
			}

		});
