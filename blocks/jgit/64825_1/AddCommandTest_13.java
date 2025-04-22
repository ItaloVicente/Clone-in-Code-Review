		try (Git git = new Git(db)) {
			git.add().addFilepattern("sub").call();

			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					indexState(CONTENT));

			git.commit().setMessage("commit").call();

			File file3 = new File(db.getWorkTree()
			FileUtils.createNewFile(file3);
			writer = new PrintWriter(file3);
			writer.print("content c");
			writer.close();

			writer = new PrintWriter(file);
			writer.print("modified content");
			writer.close();

			FileUtils.delete(file2);

			git.add().addFilepattern("sub").call();
			assertEquals(
					"[sub/a.txt
					"[sub/b.txt
					"[sub/c.txt
					indexState(CONTENT));
		}
