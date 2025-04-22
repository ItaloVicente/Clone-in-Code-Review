	private List<RepositoryCommitNote> getNotes() {
		List<RepositoryCommitNote> notes = new ArrayList<RepositoryCommitNote>();
		try {
			Repository repo = commit.getRepository();
			Git git = Git.wrap(repo);
			RevCommit revCommit = commit.getRevCommit();
			for (Ref ref : repo.getRefDatabase().getRefs(Constants.R_NOTES)
					.values()) {
				Note note = git.notesShow().setNotesRef(ref.getName())
						.setObjectId(revCommit).call();
				if (note != null)
					notes.add(new RepositoryCommitNote(commit, ref, note));
			}
		} catch (IOException e) {
			Activator.logError("Error showing notes", e); //$NON-NLS-1$
		}
		return notes;
	}

