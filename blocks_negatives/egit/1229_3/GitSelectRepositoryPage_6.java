						new GitCloneWizard());

				if (dlg.open() == Window.OK) {
					List<String> dirsAfter = util.getConfiguredRepositories();
					if (!dirsBefore.containsAll(dirsAfter)) {
						tv.setInput(dirsAfter);
						for (String dir : dirsAfter) {
							if (!dirsBefore.contains(dir)) {
								try {
									RepositoryNode node = new RepositoryNode(
											null, new FileRepository(new File(dir)));
									tv.setSelection(new StructuredSelection(
											node));
								} catch (IOException e1) {
									Activator.handleError(e1.getMessage(), e1,
											false);
								}
							}
						}
					}
					checkPage();
				}
