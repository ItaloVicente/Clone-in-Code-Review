			TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
			if (w != null) {
				final IFileRevision nextFile = GitFileRevision.inCommit(
						db,
						commit,
						gitPath,
						null);
				right = new EditableRevision(nextFile);
			}
