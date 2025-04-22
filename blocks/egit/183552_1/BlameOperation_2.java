		ObjectId headId = currentHead;
		shell.getDisplay().asyncExec(() -> openEditor(info, headId));
	}

	private static RevisionInformation computeRevisions(Repository repo,
			ObjectId start, String path, IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, 2);
		RevisionInformation info = new RevisionInformation();
		BlameCommand command = new BlameCommand(repo).setFollowFileRenames(true)
				.setFilePath(path).setStartCommit(start);
