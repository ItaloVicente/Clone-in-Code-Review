					ids.add(commit.getId());
				}
			}
		} else {
			File fileInput = getLocalFileInput(event);
			if (fileInput != null) {
				Repository repo = getRepository(event);
				gitPath = getRepoRelativePath(repo, fileInput);
				Iterator<?> it = selection.iterator();
				while (it.hasNext()) {
					RevCommit commit = (RevCommit) it.next();
					IFileRevision rev = null;
