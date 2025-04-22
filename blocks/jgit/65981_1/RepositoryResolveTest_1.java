		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();
			writeTrashFile("file2.txt"
			RefUpdate updateRemoteRef = db.updateRef("refs/remotes/origin/main");
			updateRemoteRef.setNewObjectId(c1);
			updateRemoteRef.update();
			db.getConfig().setString("branch"
			db.getConfig()
					.setString("branch"
			db.getConfig().setString("remote"
			db.getConfig().setString("remote"
			git.add().addFilepattern("file2.txt").call();
			git.commit().setMessage("create file").call();
			assertEquals("refs/remotes/origin/main"
		}
