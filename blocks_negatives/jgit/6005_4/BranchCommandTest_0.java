		assertEquals(".", localGit.getRepository().getConfig().getString(
				"branch", "newFromMaster", "remote"));
		localGit.branchRename().setOldName("newFromMaster").setNewName(
				"renamed").call();
		assertNull(".", localGit.getRepository().getConfig().getString(
				"branch", "newFromMaster", "remote"));
		assertEquals(".", localGit.getRepository().getConfig().getString(
				"branch", "renamed", "remote"));
