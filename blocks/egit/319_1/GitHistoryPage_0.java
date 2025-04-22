	private ITypedElement getEditableRevision(final IFile resource,
			final String gitPath, SWTCommit commit) {
		ITypedElement right = new EmptyElement(NLS.bind(UIText.GitHistoryPage_FileNotInCommit,
				resource.getName(), commit));

		try {
			TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
			if (w != null) {
				final IFileRevision nextFile = GitFileRevision.inCommit(
						db,
						commit,
						gitPath,
						null);
				right = new EditableRevision(nextFile);
			}
		} catch (IOException e) {
			Activator.error("IO error looking up path" + gitPath + " in "
					+ commit.getId() + ".", e);
		}
		return right;
	}

