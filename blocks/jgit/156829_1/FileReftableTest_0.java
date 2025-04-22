	@Test
	public void compactFully() throws Exception {
		FileReftableDatabase refDb = (FileReftableDatabase) db.getRefDatabase();
		PersonIdent person = new PersonIdent("jane"

		ObjectId aId  = db.exactRef("refs/heads/a").getObjectId();
		ObjectId bId  = db.exactRef("refs/heads/b").getObjectId();

		SecureRandom random = new SecureRandom();
		List<String> strs = new ArrayList<>();
		for (int i = 0; i < 1024; i++) {
			strs.add(String.format("%02x"
		}

		String randomStr = String.join(""
		String refName = "branch";
		for (long i = 0; i < 2; i++) {
			RefUpdate ru = refDb.newUpdate(refName
			ru.setNewObjectId(i % 2 == 0 ? aId : bId);
			ru.setForceUpdate(true);
			ru.setRefLogMessage(i == 0 ? randomStr : "short"
			ru.setRefLogIdent(person);

			RefUpdate.Result res = ru.update();
			assertTrue(res == Result.NEW || res == FORCED);
		}

		assertTrue(randomStr.equals(refDb.getReflogReader(refName).getReverseEntry(1).getComment()));
		refDb.compactFully();
		assertTrue(randomStr.equals(refDb.getReflogReader(refName).getReverseEntry(1).getComment()));
	}

