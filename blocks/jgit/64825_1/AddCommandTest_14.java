		try (Git git = new Git(db)) {
			String path = "a.txt";
			writeTrashFile(path
			git.add().addFilepattern(path).call();
			String path2 = "b.txt";
			writeTrashFile(path2
			git.add().addFilepattern(path2).call();
			git.commit().setMessage("commit").call();
			assertEquals("[a.txt
					+ "content
					+ "[b.txt
					+ "assume-unchanged:false]"
					| ASSUME_UNCHANGED));
			assumeUnchanged(path2);
			assertEquals("[a.txt
					+ "assume-unchanged:false][b.txt
					+ "content:content
					| ASSUME_UNCHANGED));
			writeTrashFile(path
			writeTrashFile(path2

			git.add().addFilepattern(".").call();

			assertEquals("[a.txt
					indexState(CONTENT
					| ASSUME_UNCHANGED));
		}
