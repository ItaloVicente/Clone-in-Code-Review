		for (int i = 9; i >= 0; i--) {
			String name = "refs/heads/b" + i;
			tr.branch(name).commit().create();
			RemoteRefUpdate rru = new RemoteRefUpdate(client, name, name, false, null,
					ObjectId.zeroId());
			updates.add(rru);
