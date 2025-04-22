		try (TestRepository<?> tr = new TestRepository<>(client)) {
			for (int i = 9; i >= 0; i--) {
				String name = "refs/heads/b" + i;
				tr.branch(name).commit().create();
				RemoteRefUpdate rru = new RemoteRefUpdate(client
						false
				updates.add(rru);
			}
