						List<String> dirsAfter = RepositoriesView.getDirs();
						if (!dirsBefore.containsAll(dirsAfter)) {
							for (String dir : dirsAfter) {
								if (!dirsBefore.contains(dir)) {
									try {
										RepositoryNode node = new RepositoryNode(
												null, new Repository(new File(
														dir)));
										tv
												.setSelection(new StructuredSelection(
														node));
									} catch (IOException e1) {
										Activator.handleError(e1.getMessage(),
												e1, false);
									}
								}
							}
						}
