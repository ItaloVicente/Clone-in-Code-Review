
		final String masterChanges = "some changes\n";
		setContentsAndCommit(repoRelativePath1, iFile1, initialContent1
				+ masterChanges, "master commit");
		setContentsAndCommit(repoRelativePath2, iFile2, initialContent2
				+ masterChanges, "master commit");
