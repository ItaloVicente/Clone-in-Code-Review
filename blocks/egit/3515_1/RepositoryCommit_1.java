	public RepositoryCommitNote[] getNotes() {
		if (notes == null) {
			List<RepositoryCommitNote> noteList = new ArrayList<RepositoryCommitNote>();
			try {
				Repository repo = getRepository();
				Git git = Git.wrap(repo);
				RevCommit revCommit = getRevCommit();
				for (Ref ref : repo.getRefDatabase().getRefs(Constants.R_NOTES)
						.values()) {
					Note note = git.notesShow().setNotesRef(ref.getName())
							.setObjectId(revCommit).call();
					if (note != null)
						noteList.add(new RepositoryCommitNote(this, ref, note));
				}
				notes = noteList.toArray(new RepositoryCommitNote[noteList
						.size()]);
			} catch (IOException e) {
				Activator.logError("Error showing notes", e); //$NON-NLS-1$
				notes = new RepositoryCommitNote[0];
			}
		}
		return notes;
	}

