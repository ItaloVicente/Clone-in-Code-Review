		}
		if (input instanceof File) {
			Repository repo = getRepository(event);
			File fileInput = (File) input;
			gitPath = getRepoRelativePath(repo, fileInput);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				IFileRevision rev = null;
