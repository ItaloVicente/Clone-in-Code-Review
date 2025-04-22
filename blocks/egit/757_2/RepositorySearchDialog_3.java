	private HashSet<String> getCheckedItems() {
		HashSet<String> ret = new HashSet<String>();
		for (TreeItem item : fTreeViewer.getTree().getItems())
			if (item.getChecked())
				ret.add((String) item.getData());

		return ret;
	}

	private boolean hasCheckedItems() {
		for (TreeItem item : fTreeViewer.getTree().getItems())
			if (item.getChecked())
				return true;

		return false;
	}

	private void setAllSelected(boolean selectionState) {
		for (TreeItem item : fTreeViewer.getTree().getItems())
			item.setChecked(selectionState);
		getButton(OK).setEnabled(hasCheckedItems());
	}

	private void doSearch() {

		final Set<String> directories = new HashSet<String>();
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
						throws InvocationTargetException, InterruptedException {

					try {
						findGitDirsRecursive(file, directories, monitor,
								lookForNested);
					} catch (Exception ex) {
						Activator.getDefault().getLog().log(
								new Status(IStatus.ERROR, Activator
										.getPluginId(), ex.getMessage(), ex));
					}
				}
			};
			try {
				ProgressMonitorDialog pd = new ProgressMonitorDialog(getShell());
				pd
						.getProgressMonitor()
						.setTaskName(
								UIText.RepositorySearchDialog_ScanningForRepositories_message);
				pd.run(true, true, action);

			} catch (InvocationTargetException e1) {
				org.eclipse.egit.ui.Activator.handleError(
						UIText.RepositorySearchDialog_errorOccurred, e1, true);
			} catch (InterruptedException e1) {
			}

			boolean foundNew = false;
			int foundOld = 0;

			TreeSet<String> validDirs = new TreeSet<String>();

			for (String foundDir : directories) {
				if (!fExistingDirectories.contains(foundDir)) {
					validDirs.add(foundDir);
					foundNew = true;
				} else {
					foundOld++;
				}
			}

			if (foundOld > 0) {
				String message = NLS
						.bind(
								"{0} directories are hidden as they have alredy been added", Integer.valueOf(foundOld)); //$NON-NLS-1$
				setMessage(message, IMessageProvider.WARNING);
			} else
				setMessage(null);
			fSelectAllButton.setEnabled(foundNew);
			fDeselectAllButton.setEnabled(foundNew);
			fTree.setEnabled(validDirs.size() > 0);
			fTreeViewer.setInput(validDirs);
		}

	}
