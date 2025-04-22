		TestRepository<InMemoryRepository> remote =
				new TestRepository<>(server);
		commit0 = remote.commit().message("0").create();
		commit1 = remote.commit().message("1").parent(commit0).create();
		tip = remote.commit().message("2").parent(commit1).create();
		remote.update("master", tip);
