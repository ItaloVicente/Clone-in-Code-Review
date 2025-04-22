						if (!allowBare && newRepository.isBare()) {
							if (!previousSelection.isEmpty()) {
								tv.setSelection(previousSelection);
							}
						} else {
							RepositoryNode node = new RepositoryNode(null,
									newRepository);
							tv.setSelection(new StructuredSelection(node));
						}
