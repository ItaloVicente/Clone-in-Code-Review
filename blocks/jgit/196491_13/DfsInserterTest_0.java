	@Test
	public void testObjectSizePopulated() throws IOException {
		byte[] contents = Constants.encode("foo");

		PersonIdent person = new PersonIdent("Committer a"
		CommitBuilder c = new CommitBuilder();
		c.setAuthor(person);
		c.setCommitter(person);
		c.setTreeId(ObjectId
				.fromString("45c4c6767a3945815371a7016532751dd558be40"));
		c.setMessage("commit message");

		TreeFormatter treeBuilder = new TreeFormatter(2);
		treeBuilder.append("filea"
				.fromString("45c4c6767a3945815371a7016532751dd558be40"));
		treeBuilder.append("fileb"
				.fromString("1c458e25ca624bb8d4735bec1379a4a29ba786d0"));

		TagBuilder tagBuilder = new TagBuilder();
		tagBuilder.setObjectId(
				ObjectId.fromString("c97fe131649e80de55bd153e9a8d8629f7ca6932")
				Constants.OBJ_COMMIT);
		tagBuilder.setTag("short name");

		try (DfsInserter ins = (DfsInserter) db.newObjectInserter()) {
			ObjectId aBlob = ins.insert(Constants.OBJ_BLOB
			assertEquals(contents.length
					ins.objectMap.get(aBlob).getFullSize());

			ObjectId aCommit = ins.insert(c);
			assertEquals(174

			ObjectId tree = ins.insert(treeBuilder);
			assertEquals(66

			ObjectId tag = ins.insert(tagBuilder);
			assertEquals(76
		}
	}

