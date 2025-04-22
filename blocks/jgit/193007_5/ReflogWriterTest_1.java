	private static String oneLinePrefix = "da85355dfc525c9f6f3927b876f379f46ccf826e 3e7549db262d1e836d9bf0af7e22355468f1717c"
			+ " John Doe <john@doe.com> 1243028200 +0200\t";

	private static String oneLine = oneLinePrefix
			+ "stash: Add message with line feeds\n";

	private static final ObjectId oldId = ObjectId
			.fromString("da85355dfc525c9f6f3927b876f379f46ccf826e");

	private static final ObjectId newId = ObjectId
			.fromString("3e7549db262d1e836d9bf0af7e22355468f1717c");

	private static final PersonIdent ident = new PersonIdent("John Doe"
			"john@doe.com"
