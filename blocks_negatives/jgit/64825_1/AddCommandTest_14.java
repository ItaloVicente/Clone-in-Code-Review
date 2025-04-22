		Git git = new Git(db);
		String path = "a.txt";
		writeTrashFile(path, "content");
		git.add().addFilepattern(path).call();
		String path2 = "b.txt";
		writeTrashFile(path2, "content");
		git.add().addFilepattern(path2).call();
		git.commit().setMessage("commit").call();
		assertEquals("[a.txt, mode:100644, content:"
				+ "content, assume-unchanged:false]"
				+ "[b.txt, mode:100644, content:content, "
				+ "assume-unchanged:false]", indexState(CONTENT
				| ASSUME_UNCHANGED));
		assumeUnchanged(path2);
		assertEquals("[a.txt, mode:100644, content:content, "
				+ "assume-unchanged:false][b.txt, mode:100644, "
				+ "content:content, assume-unchanged:true]", indexState(CONTENT
				| ASSUME_UNCHANGED));
		writeTrashFile(path, "more content");
		writeTrashFile(path2, "more content");

		git.add().addFilepattern(".").call();

		assertEquals("[a.txt, mode:100644, content:more content,
-				+  assume-unchanged:false][b.txt, mode:100644,
- + " + "
-				+  content:content, assume-unchanged:true]",
				indexState(CONTENT
				| ASSUME_UNCHANGED));
