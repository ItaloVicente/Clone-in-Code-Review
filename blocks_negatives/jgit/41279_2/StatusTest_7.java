				}, execute("git status --porcelain")); //
		writeTrashFile("stagedModified", "stagedModified modified");
		deleteTrashFile("stagedDeleted");
		writeTrashFile("trackedModified", "trackedModified modified");
		deleteTrashFile("trackedDeleted");
		git.add().addFilepattern("stagedModified").call();
		git.rm().addFilepattern("stagedDeleted").call();
		git.add().addFilepattern("stagedNew").call();
