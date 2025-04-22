		IFileRevision nextFile = GitFileRevision.inCommit(repository, commit,
				gitPath, null);

		FileRevisionTypedElement element = new FileRevisionTypedElement(
				nextFile);
		return element;
