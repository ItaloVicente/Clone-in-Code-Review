		TestRepository<InMemoryRepository> local = new TestRepository<>(client);
		RevBlob localParentBlob = local.blob(commonInBlob + "a");
		RevCommit localParent = local.commit(local.tree(local.file("foo", localParentBlob)));
		RevBlob localChildBlob = local.blob(commonInBlob + "b");
		RevCommit localChild = local.commit(
				local.tree(local.file("foo", localChildBlob)), localParent);
		local.update("branch1", localChild);
