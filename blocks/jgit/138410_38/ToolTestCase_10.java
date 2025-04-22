		git.checkout().setName("branch_2").call();
		git.rm().addFilepattern("a").call();
		git.rm().addFilepattern("b").call();
		git.commit().setMessage("files a & b deleted commit 2").call();
		git.cherryPick().include(commit1).call();
		String[] conflictingFilenames = { "a"
		return conflictingFilenames;
