			RepositoryCommit commit = getCommit();
			if (commit != null) {
				diffPage = new DiffEditorPage(this);
				addPage(diffPage, new DiffEditorInput(commit));
				if (commit.getNotes().length > 0) {
					notePage = new NotesEditorPage(this);
					addPage(notePage);
				}
