				} else {
					ids.add(commit.getId());
				}
			}
		}
		if (input instanceof File) {
			File fileInput = (File) input;
			gitPath = getRepoRelativePath(repo, fileInput);
			Iterator<?> it = selection.iterator();
			while (it.hasNext()) {
				RevCommit commit = (RevCommit) it.next();
				IFileRevision rev = null;
				try {
					rev = CompareUtils.getFileRevision(gitPath, commit, repo,
							null);
