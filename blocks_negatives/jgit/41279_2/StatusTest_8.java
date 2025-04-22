				}, execute("git status --porcelain")); //
		writeTrashFile("unmerged", "unmerged");
		git.add().addFilepattern("unmerged").call();
		git.add().addFilepattern("trackedModified").call();
		git.rm().addFilepattern("trackedDeleted").call();
		git.commit().setMessage("commit before branching").call();
						"?? untracked", //
