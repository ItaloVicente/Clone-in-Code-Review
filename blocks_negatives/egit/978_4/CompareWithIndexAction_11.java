
	@Override
	public void execute(IAction action) throws InvocationTargetException {
		final IResource resource = getSelectedResources()[0];

		final IFile baseFile = (IFile) resource;
		final ITypedElement base = SaveableCompareEditorInput
				.createFileElement(baseFile);

		final ITypedElement next = getHeadTypedElement(baseFile);

		final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
				base, next, null);
		CompareUI.openCompareEditor(in);
	}

	private ITypedElement getHeadTypedElement(final IFile baseFile)
			throws InvocationTargetException {
		final RepositoryMapping mapping = RepositoryMapping.getMapping(baseFile
				.getProject());
		final Repository repository = mapping.getRepository();
		String gitPath = mapping.getRepoRelativePath(baseFile);

		try {
			GitIndex index = repository.getIndex();
			if (index.getEntry(gitPath) == null) {
				return new GitCompareFileRevisionEditorInput.EmptyTypedElement(
						NLS.bind(UIText.CompareWithIndexAction_FileNotInIndex,
								baseFile.getName()));
			}
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		}

		IFileRevision nextFile = GitFileRevision.inIndex(repository, gitPath);
		final EditableRevision next = new EditableRevision(nextFile);

		IContentChangeListener listener = new IContentChangeListener() {
			public void contentChanged(IContentChangeNotifier source) {
				final byte[] newContent = next.getModifiedContent();
				try {
					final GitIndex index = repository.getIndex();
					final File file = new File(baseFile.getLocation().toString());
					index.add(mapping.getWorkDir(), file, newContent);
					index.write();
				} catch (IOException e) {
					handle(
							new TeamException(
									UIText.CompareWithIndexAction_errorOnAddToIndex,
									e),
							UIText.CompareWithIndexAction_errorOnAddToIndex,
							UIText.CompareWithIndexAction_errorOnAddToIndex);
					return;
				}
			}
		};

		next.addContentChangeListener(listener);
		return next;
