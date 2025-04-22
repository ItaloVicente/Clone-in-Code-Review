		Git git = new Git(db);
		writeTrashFile("file.txt", "content");
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file2.txt", "content");
		RefUpdate updateRemoteRef = db.updateRef("refs/remotes/origin/main");
		updateRemoteRef.setNewObjectId(c1);
		updateRemoteRef.update();
		db.getConfig().setString("branch", "master", "remote", "origin");
		db.getConfig()
				.setString("branch", "master", "merge", "refs/heads/main");
		db.getConfig().setString("remote", "origin", "url",
		db.getConfig().setString("remote", "origin", "fetch",
				"+refs/heads/*:refs/remotes/origin/*");
		git.add().addFilepattern("file2.txt").call();
		git.commit().setMessage("create file").call();
		assertEquals("refs/remotes/origin/main", db.simplify("@{upstream}"));
