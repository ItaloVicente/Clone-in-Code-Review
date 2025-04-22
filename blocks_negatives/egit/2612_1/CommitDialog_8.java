					Activator.logError(UIText.CommitDialog_ErrorAddingFiles, e);
					return;
				}
			}
		});

		return menu;
	}

	/** Retrieve file status from an already calculated IndexDiff
	 * @param path
	 * @param indexDiff
	 * @return file status
	 */
	private static Status getFileStatus(String path, IndexDiff indexDiff) {
		if (indexDiff.getAssumeUnchanged().contains(path)) {
			return Status.ASSUME_UNCHANGED;
		} else if (indexDiff.getAdded().contains(path)) {
			if (indexDiff.getModified().contains(path))
				return Status.ADDED_INDEX_DIFF;
			else
				return Status.ADDED;
		} else if (indexDiff.getChanged().contains(path)) {
			if (indexDiff.getModified().contains(path))
				return Status.MODIFIED_INDEX_DIFF;
			else
				return Status.MODIFIED;
		} else if (indexDiff.getUntracked().contains(path)) {
			if (indexDiff.getRemoved().contains(path))
				return Status.REMOVED_UNTRACKED;
			else
				return Status.UNTRACKED;
		} else if (indexDiff.getRemoved().contains(path)) {
			return Status.REMOVED;
		} else if (indexDiff.getMissing().contains(path)) {
			return Status.REMOVED_NOT_STAGED;
		} else if (indexDiff.getModified().contains(path)) {
			return Status.MODIFIED_NOT_STAGED;
		}
		return Status.UNKNOWN;
	}

	/** Retrieve file status
	 * @param file
	 * @return file status
	 * @throws IOException
	 */
	private static Status getFileStatus(IFile file) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(file);
		String path = mapping.getRepoRelativePath(file);
		Repository repo = mapping.getRepository();
		AdaptableFileTreeIterator fileTreeIterator = new AdaptableFileTreeIterator(
				repo, ResourcesPlugin.getWorkspace().getRoot());
		IndexDiff indexDiff = new IndexDiff(repo, Constants.HEAD, fileTreeIterator);
		Set<String> repositoryPaths = Collections.singleton(path);
		indexDiff.setFilter(PathFilterGroup.createFromStrings(repositoryPaths));
		indexDiff.diff(null, 0, 0, ""); //$NON-NLS-1$
		return getFileStatus(path, indexDiff);
	}

	/**
	 * @return The message the user entered
	 */
	public String getCommitMessage() {
		return commitMessage;
	}

	/**
	 * Preset a commit message. This might be for amending a commit.
	 * @param s the commit message
	 */
	public void setCommitMessage(String s) {
		this.commitMessage = s;
	}

	private String commitMessage = null;
	private String author = null;
	private String committer = null;
	private String previousAuthor = null;

	private boolean signedOff = org.eclipse.egit.ui.Activator.getDefault()
			.getPreferenceStore()
			.getBoolean(UIPreferences.COMMIT_DIALOG_SIGNED_OFF_BY);

	private boolean amending = false;
	private boolean amendAllowed = true;
	private boolean showUntracked = true;
	private boolean createChangeId = false;
	private boolean allowToChangeSelection = true;

	private ArrayList<IFile> selectedFiles = new ArrayList<IFile>();
	private IPreviousValueProposalHandler authorHandler;
	private IPreviousValueProposalHandler committerHandler;


	/**
	 * Pre-select suggested set of resources to commit
	 *
	 * @param items
	 */
	public void setSelectedFiles(IFile[] items) {
		Collections.addAll(selectedFiles, items);
	}

	/**
	 * @return the resources selected by the user to commit.
	 */
	public IFile[] getSelectedFiles() {
		return selectedFiles.toArray(new IFile[0]);
	}

	/**
	 * Sets the files that should be checked in this table.
	 *
	 * @param preselectedFiles
	 *            the files to be checked in the dialog's table, must not be
	 *            <code>null</code>
	 */
	public void setPreselectedFiles(Set<IFile> preselectedFiles) {
		Assert.isNotNull(preselectedFiles);
		this.preselectedFiles = preselectedFiles;
	}

	class HeaderSelectionListener extends SelectionAdapter {

		private CommitItem.Order order;

		private Boolean reversed;

		public HeaderSelectionListener(CommitItem.Order order) {
			this.order = order;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			TableColumn column = (TableColumn) e.widget;
			Table table = column.getParent();

			if (column == table.getSortColumn()) {
				int currentDirection = table.getSortDirection();
				switch (currentDirection) {
				case SWT.NONE:
					reversed = Boolean.FALSE;
					break;
				case SWT.UP:
					reversed = Boolean.TRUE;
					break;
				case SWT.DOWN:
				default:
					reversed = null;
					break;
				}
			} else
				reversed = Boolean.FALSE;

			if (reversed == null) {
				table.setSortColumn(null);
				table.setSortDirection(SWT.NONE);
				filesViewer.setComparator(null);
				return;
			}
			table.setSortColumn(column);

			Comparator<CommitItem> comparator;
			if (reversed.booleanValue()) {
				comparator = order.descending();
				table.setSortDirection(SWT.DOWN);
			} else {
				comparator = order;
				table.setSortDirection(SWT.UP);
			}

			filesViewer.setComparator(new CommitViewerComparator(comparator));
		}

	}

	class CommitItemSelectionListener extends SelectionAdapter {

		public void widgetDefaultSelected(SelectionEvent e) {
			IStructuredSelection selection = (IStructuredSelection) filesViewer.getSelection();

			CommitItem commitItem = (CommitItem) selection.getFirstElement();
			if (commitItem == null) {
				return;
			}
			if (commitItem.status == Status.UNTRACKED)
				return;

			IProject project = commitItem.file.getProject();
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping == null) {
				return;
			}
			Repository repository = mapping.getRepository();

			try {
				ObjectId id = repository.resolve(Constants.HEAD);
				if (id == null
						|| repository.open(id, Constants.OBJ_COMMIT).getType() != Constants.OBJ_COMMIT) {
