	private ITypedElement getFileRevisionTypedElement(final IFile resource,
			final String gitPath, SWTCommit commit) {
		ITypedElement right = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
				NLS.bind(UIText.GitHistoryPage_FileNotInCommit, resource
						.getName(), commit));

		try {
			IFileRevision nextFile = getFileRevision(resource, gitPath, commit);
			if (nextFile != null)
				right = new FileRevisionTypedElement(nextFile);
		} catch (IOException e) {
			Activator.error(NLS.bind(UIText.GitHistoryPage_errorLookingUpPath,
					gitPath, commit.getId()), e);
		}
		return right;
	}

	private IFileRevision getFileRevision(final IFile resource,
			final String gitPath, SWTCommit commit) throws IOException {

		TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
		if (w != null) {
			final IFileRevision fileRevision = GitFileRevision.inCommit(db,
					commit, gitPath, null);
			return fileRevision;
		}
		return null;
	}

