			String[] content1 = new String[] { "abc" };
			writeTrashFile("something_else.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();

			String[] content2 = new String[] { "abc"
			writeTrashFile("something_else.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("update file").call();

			String[] content3 = new String[] { "first"
			writeTrashFile("file.txt"
