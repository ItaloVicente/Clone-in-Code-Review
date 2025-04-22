		try (Git git = new Git(db)) {
			File file = new File(db.getWorkTree()
			FileUtils.createNewFile(file);
			PrintWriter writer = new PrintWriter(file);
			writer.print("content1");
			writer.close();

			git.add().addFilepattern("a.txt").call();
			git.commit().setMessage("commit1").setCommitter(committer).call();

			file = new File(db.getWorkTree()
			FileUtils.createNewFile(file);
			writer = new PrintWriter(file);
			writer.print("content2");
			writer.close();

			git.add().addFilepattern("b.txt").call();
			git.commit().setMessage("commit2").setCommitter(committer).call();

			int count = 0;
			for (RevCommit c : git.log().addPath("a.txt").call()) {
				assertEquals("commit1"
				count++;
			}
			assertEquals(1

			count = 0;
			for (RevCommit c : git.log().addPath("b.txt").call()) {
				assertEquals("commit2"
				count++;
			}
			assertEquals(1

			count = 0;
			for (RevCommit c : git.log().call()) {
				assertEquals(committer
				count++;
			}
			assertEquals(2
