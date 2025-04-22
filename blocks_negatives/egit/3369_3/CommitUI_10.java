					for (Repository repo : repos) {
						RepositoryMapping mapping = RepositoryMapping
								.findRepositoryMapping(repo);
						if (mapping != null)
							mapping.fireRepositoryChanged();
					}
