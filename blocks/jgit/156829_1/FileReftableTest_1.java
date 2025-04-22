	@Test
	public void reflogTombstoneCompaction() throws Exception {
		FileReftableDatabase refDb = (FileReftableDatabase) db.getRefDatabase();
		PersonIdent person = new PersonIdent("jane"

		ObjectId aId  = db.exactRef("refs/heads/a").getObjectId();
		ObjectId bId  = db.exactRef("refs/heads/b").getObjectId();

		String refName = "refs/heads/log";
		List<String> messages = new ArrayList<>();
		int N = 6;
		for (long i = 0; i < 10; i++) {
			if (i == N) {
				ReflogEntry entry = refDb.getReflogReader(refName).getReverseEntry(N-2);
				assertEquals(messages.get(1)
				assertTrue(refDb.deleteLog(refName
				entry = refDb.getReflogReader(refName).getReverseEntry(N-2);
				assertEquals(messages.get(0)
			}

			String msg = String.format("msg %d"
			messages.add(msg);
			RefUpdate ru = refDb.newUpdate(refName
			ru.setNewObjectId(i % 2 == 0 ? aId : bId);
			ru.setForceUpdate(true);
			ru.setRefLogMessage(msg
			ru.setRefLogIdent(person);

			RefUpdate.Result res = ru.update();
			assertTrue(res == Result.NEW || res == FORCED);
		}

		messages.remove(1);
		List<String> entries =
			refDb.getReflogReader(refName).getReverseEntries().stream().map(x -> x.getComment()).collect(Collectors.toList());
		Collections.reverse(entries);

		assertEquals(messages
	}

