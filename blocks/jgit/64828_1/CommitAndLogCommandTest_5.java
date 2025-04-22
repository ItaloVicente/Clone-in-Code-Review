		try (Git git = new Git(db)) {
			File file = new File(db.getWorkTree()
			FileUtils.createNewFile(file);
			PrintWriter writer = new PrintWriter(file);
			writer.print("content1");
			writer.close();

			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("commit1").setCommitter(committer).call();

			FS fs = db.getFS();
			fs.setExecute(file
			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("mode change").setCommitter(committer).call();

			fs.setExecute(file
			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("mode change").setCommitter(committer)
					.setOnly("a.txt").call();
		}
