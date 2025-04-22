
	/**
	 * Adds a directory to the list if it is not already there
	 *
	 * @param file
	 * @return see {@link Collection#add(Object)}
	 */
	public static boolean addDir(File file) {

		String dirString;
		try {
			dirString = file.getCanonicalPath();
		} catch (IOException e) {
			dirString = file.getAbsolutePath();
		}

		List<String> dirStrings = getDirs();
		if (dirStrings.contains(dirString)) {
			return false;
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
					Repository repo = org.eclipse.egit.core.Activator
							.getDefault().getRepositoryCache()
							.lookupRepository(dir);
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

	}

	public void setSelection(ISelection selection) {
		currentSelection = selection;
		for (ISelectionChangedListener listener : selectionListeners) {
			listener.selectionChanged(new SelectionChangedEvent(
					RepositoriesView.this, selection));
		}
	}

	/**
	 * Opens the tree and marks the folder to which a project is pointing
	 *
	 * @param resource
	 *            TODO exceptions?
	 */
	@SuppressWarnings("unchecked")
	public void showResource(final IResource resource) {
		IProject project = resource.getProject();
		RepositoryMapping mapping = RepositoryMapping.getMapping(project);
		if (mapping == null)
			return;

		if (addDir(mapping.getRepository().getDirectory())) {
			scheduleRefresh();
		}

		boolean doSetSelection = false;

		if (this.scheduledJob != null) {
			int state = this.scheduledJob.getState();
			if (state == Job.WAITING || state == Job.RUNNING) {
				this.scheduledJob.addJobChangeListener(new JobChangeAdapter() {

					@Override
					public void done(IJobChangeEvent event) {
						showResource(resource);
					}
				});
			} else {
				doSetSelection = true;
			}
		}

		if (doSetSelection) {
			RepositoriesViewContentProvider cp = (RepositoriesViewContentProvider) tv
					.getContentProvider();
			RepositoryTreeNode currentNode = null;
			Object[] repos = cp.getElements(tv.getInput());
			for (Object repo : repos) {
				RepositoryTreeNode node = (RepositoryTreeNode) repo;
				if (mapping.getRepository().getDirectory().equals(
						((Repository) node.getObject()).getDirectory())) {
					for (Object child : cp.getChildren(node)) {
						RepositoryTreeNode childNode = (RepositoryTreeNode) child;
						if (childNode.getType() == RepositoryTreeNodeType.WORKINGDIR) {
							currentNode = childNode;
							break;
						}
					}
					break;
				}
			}

			IPath relPath = new Path(mapping.getRepoRelativePath(resource));

			for (String segment : relPath.segments()) {
				for (Object child : cp.getChildren(currentNode)) {
					RepositoryTreeNode<File> childNode = (RepositoryTreeNode<File>) child;
					if (childNode.getObject().getName().equals(segment)) {
						currentNode = childNode;
						break;
					}
				}
			}

			final RepositoryTreeNode selNode = currentNode;

			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					tv.setSelection(new StructuredSelection(selNode), true);
				}
			});

		}

	}

