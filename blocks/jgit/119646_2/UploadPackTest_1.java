		RevBlob longBlob = remote2.blob("foobar");
		RevBlob shortBlob = remote2.blob("fooba");
		RevTree tree = remote2.tree(remote2.file("1"
				remote2.file("2"
		RevCommit commit = remote2.commit(tree);
		remote2.update("master"
