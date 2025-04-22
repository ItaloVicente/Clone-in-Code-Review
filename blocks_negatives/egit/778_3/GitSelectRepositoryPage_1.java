					try {
						Set<String> dirs = dlg.getDirectories();
						for (String dir : dirs)
							RepositoriesView.addDir(new File(dir));

						tv.setInput(RepositoriesView
								.getRepositoriesFromDirs(null));
						checkPage();
					} catch (InterruptedException e1) {
					}
