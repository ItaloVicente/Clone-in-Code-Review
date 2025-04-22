		RevBlob longBlob = remote.blob("foobar");
		RevBlob shortBlob = remote.blob("fooba");
		RevTree tree = remote.tree(
				remote.file("1", longBlob), remote.file("2", shortBlob));
		RevCommit commit = remote.commit(tree);
		remote.update("master", commit);
