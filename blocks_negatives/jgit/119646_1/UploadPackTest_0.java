		RevBlob blob1 = remote.blob("foobar");
		RevBlob blob2 = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1", blob1), remote.file("2", blob2));
		RevCommit commit = remote.commit(tree);
		remote.update("master", commit);
