			next = new GitCompareFileRevisionEditorInput.EmptyTypedElement(NLS.bind(UIText.GitHistoryPage_FileNotInCommit,
					resource.getName(), commit));
			TreeWalk w = TreeWalk.forPath(repository, gitPath, commit.getTree());
			if (w != null) {
				final IFileRevision nextFile = GitFileRevision.inCommit(repository, commit, gitPath, null);
				next = new FileRevisionTypedElement(nextFile);
			}
