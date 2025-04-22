	@Test
	public void shouldNotLogRemoteRefs() throws Exception {
		String remoteRef = Constants.R_REMOTES + "foo";
		ReflogWriter writer = new ReflogWriter(
				(RefDirectory) db.getRefDatabase());
		PersonIdent ident = new PersonIdent("John Doe"
				1243028200000L
		ObjectId oldId = ObjectId
				.fromString("da85355dfc525c9f6f3927b876f379f46ccf826e");
		ObjectId newId = ObjectId
				.fromString("3e7549db262d1e836d9bf0af7e22355468f1717c");

		writer.log(remoteRef

		assertFalse(new File(db.getDirectory()
	}

