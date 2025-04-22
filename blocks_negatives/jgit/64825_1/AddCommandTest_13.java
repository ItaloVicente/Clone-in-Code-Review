		Git git = new Git(db);
		git.add().addFilepattern("sub").call();

		assertEquals(
				"[sub/a.txt, mode:100644, content:content]" +
				"[sub/b.txt, mode:100644, content:content b]",
				indexState(CONTENT));

		git.commit().setMessage("commit").call();

		File file3 = new File(db.getWorkTree(), "sub/c.txt");
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
				"[sub/a.txt, mode:100644, content:modified content]" +
				"[sub/b.txt, mode:100644, content:content b]" +
				"[sub/c.txt, mode:100644, content:content c]",
				indexState(CONTENT));
