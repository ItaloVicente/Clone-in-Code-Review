		try (Git git = new Git(repository1.getRepository())) {
			git.add().addFilepattern("file.txt").call();
			RevCommit commit = git.commit().setMessage("Initial commit").call();
			git.notesAdd().setNotesRef("refs/notes/review").setObjectId(commit)
					.setMessage("text").call();
		}
