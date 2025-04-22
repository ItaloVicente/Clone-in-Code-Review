		IFileRevision rev;
		if (!conflicting || useWorkspace)
			rev = new LocalFileRevision(file);
		else
			rev = GitFileRevision.inCommit(map.getRepository(), headCommit,
					gitPath, null);
