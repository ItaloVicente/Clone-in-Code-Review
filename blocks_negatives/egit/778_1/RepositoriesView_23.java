		} else {
			Set<String> dirs = new HashSet<String>();
			dirs.addAll(dirStrings);
			dirs.add(dirString);
			saveDirs(dirs);
			return true;
		}
	}

	/**
	 * Converts the directories as configured for this view into a list of
	 * {@link Repository} objects suitable for the tree content provider
	 * <p>
	 * TODO move to some utility class
	 *
	 * @param monitor
	 * @return a list of nodes
	 * @throws InterruptedException
	 */
	public static List<RepositoryTreeNode<Repository>> getRepositoriesFromDirs(
			IProgressMonitor monitor) throws InterruptedException {

		List<String> gitDirStrings = getDirs();
		List<RepositoryTreeNode<Repository>> input = new ArrayList<RepositoryTreeNode<Repository>>();

		for (String dirString : gitDirStrings) {
			if (monitor != null && monitor.isCanceled()) {
				throw new InterruptedException(
						UIText.RepositoriesView_ActionCanceled_Message);
			}
			try {
				File dir = new File(dirString);
				if (dir.exists() && dir.isDirectory()) {
					Repository repo = new Repository(dir);
					repo.scanForRepoChanges();
					RepositoryNode node = new RepositoryNode(null, repo);
					input.add(node);
				}
			} catch (IOException e) {
				IStatus error = new Status(IStatus.ERROR, Activator
						.getPluginId(), e.getMessage(), e);
				Activator.getDefault().getLog().log(error);
			}
		}
		Collections.sort(input);
		return input;
	}

	private static void saveDirs(Set<String> gitDirStrings) {
		StringBuilder sb = new StringBuilder();
		for (String gitDirString : gitDirStrings) {
			sb.append(gitDirString);
			sb.append(File.pathSeparatorChar);
		}

		IEclipsePreferences prefs = getPrefs();
		prefs.put(PREFS_DIRECTORIES, sb.toString());
		try {
			prefs.flush();
		} catch (BackingStoreException e) {
			IStatus error = new Status(IStatus.ERROR, Activator.getPluginId(),
					e.getMessage(), e);
			Activator.getDefault().getLog().log(error);
		}
	}

	@Override
	public void setFocus() {
		tv.getTree().setFocus();
	}

	@SuppressWarnings("boxing")
	private boolean confirmProjectDeletion(List<IProject> projectsToDelete) {
		boolean confirmed;
		confirmed = MessageDialog
				.openConfirm(
						getSite().getShell(),
						UIText.RepositoriesView_ConfirmProjectDeletion_WindowTitle,
						NLS
								.bind(
										UIText.RepositoriesView_ConfirmProjectDeletion_Question,
										projectsToDelete.size()));
		return confirmed;
	}

	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}

	public ISelection getSelection() {
		return currentSelection;
	}

	public void removeSelectionChangedListener(
			ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
