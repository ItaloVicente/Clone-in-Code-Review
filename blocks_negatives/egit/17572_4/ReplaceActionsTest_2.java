		ObjectId masterId = repo.resolve("refs/heads/master");
		Ref newBranch = git.checkout().setCreateBranch(true)
				.setStartPoint(commitOfTag.name()).setName("toMerge").call();
		ByteArrayInputStream bis = new ByteArrayInputStream(
				"Modified".getBytes());
		ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE2)
				.setContents(bis, false, false, null);
		bis.close();
		PersonIdent committer = new PersonIdent("COMMITTER", "a.c@d",
				new Date(), TimeZone.getTimeZone("GMT-03:30"));
		git.commit().setAll(true).setCommitter(committer)
				.setMessage("To be merged").call();
		git.merge().include(masterId).call();
		String newContent = getTestFileContent();
