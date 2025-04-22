			writeTrashFile("file_1.txt", "content_1_1");
			git.add().addFilepattern("file_1.txt").call();
			git.commit().setMessage("create file").call();

			writeTrashFile("file_1.txt", "content_1_2");
			writeTrashFile("file_2.txt", "content_2_2");
			git.add().addFilepattern(".").call();
			git.commit().setMessage("updated file").call();
