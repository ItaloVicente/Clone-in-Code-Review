				final TreeSet<String> directories = new TreeSet<String>();
				final File file = new File(dir.getText());
				final boolean lookForNested = lookForNestedButton.getSelection();
				if (file.exists()) {
					try {
						prefs.put(PREF_PATH, file.getCanonicalPath());
						try {
							prefs.flush();
						} catch (BackingStoreException e1) {
						}
					} catch (IOException e2) {
					}

					IRunnableWithProgress action = new IRunnableWithProgress() {

						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {

							try {
								findGitDirsRecursive(file, directories,
										monitor, lookForNested);
							} catch (Exception ex) {
								Activator.getDefault().getLog().log(
										new Status(IStatus.ERROR, Activator
												.getPluginId(),
												ex.getMessage(), ex));
							}
						}
					};
					try {
						ProgressMonitorDialog pd = new ProgressMonitorDialog(
								getShell());
						pd
								.getProgressMonitor()
								.setTaskName(
										UIText.RepositorySearchDialog_ScanningForRepositories_message);
						pd.run(true, true, action);

					} catch (InvocationTargetException e1) {
						org.eclipse.egit.ui.Activator.handleError(
								UIText.RepositorySearchDialog_errorOccurred,
								e1, true);
					} catch (InterruptedException e1) {
					}

					boolean foundNew = false;

					for (String foundDir : directories) {
						if (!fExistingDirectories.contains(foundDir)) {
							foundNew = true;
							break;
						}
					}

					fSelectAllButton.setEnabled(foundNew);
					fDeselectAllButton.setEnabled(foundNew);
					fTree.setEnabled(directories.size() > 0);
					fTreeViewer.setInput(directories);
				}
