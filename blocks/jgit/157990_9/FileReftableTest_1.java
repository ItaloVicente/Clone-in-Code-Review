	@Test
	public void testConvertToRefdirReflog() throws Exception {
		Ref a = db.exactRef("refs/heads/a");
		String aCommit = a.getObjectId().getName();
		RefUpdate u = db.updateRef("refs/heads/master");
		u.setForceUpdate(true);
		u.setNewObjectId(ObjectId.fromString(aCommit));
		u.setForceRefLog(true);
		u.setRefLogMessage("apple"
		u.update();

		RefUpdate v = db.updateRef("refs/heads/master");
		v.setForceUpdate(true);
		v.setNewObjectId(ObjectId.fromString(bCommit));
		v.setForceRefLog(true);
		v.setRefLogMessage("banana"
		v.update();

		db.convertToPackedRefs(true
		List<ReflogEntry> logs = db.getReflogReader("refs/heads/master").getReverseEntries(2);
		assertEquals(logs.get(0).getComment()
		assertEquals(logs.get(1).getComment()
	}

