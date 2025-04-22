		try (TestRepository<InMemoryRepository> local = new TestRepository<>(
				client)) {
			RevBlob localParentBlob = local.blob(commonInBlob + "a");
			RevCommit localParent = local
					.commit(local.tree(local.file("foo"
			RevBlob localChildBlob = local.blob(commonInBlob + "b");
			RevCommit localChild = local.commit(
					local.tree(local.file("foo"
			local.update("branch1"
		}
