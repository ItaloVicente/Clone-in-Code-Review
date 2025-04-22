	private void secondCommit() throws Exception {
		writeTrashFile("greeting"
		git.add().addFilepattern("greeting").call();
		git.commit().setMessage("2nd commit").call();
	}

