
		map = NoteMap.read(reader
		assertEquals(data2
		assertEquals(b
		assertFalse("no b"
		assertFalse("no data2"
		assertEquals(b
				.forPath(reader
	}

	public void testRemoveDeletesTreeFanout2_38() throws Exception {
		RevBlob a = tr.blob("a");
		RevBlob data1 = tr.blob("data1");
		RevTree empty = tr.tree();

				.add(fanout(2
				.create();
		tr.parseBody(r);

		NoteMap map = NoteMap.read(reader
		map.set(a

		RevCommit n = commitNoteMap(map);
		assertEquals("empty tree"
	}

	private RevCommit commitNoteMap(NoteMap map) throws IOException {
		tr.tick(600);

		CommitBuilder builder = new CommitBuilder();
		builder.setTreeId(map.writeTree(inserter));
		tr.setAuthorAndCommitter(builder);
		return tr.getRevWalk().parseCommit(inserter.insert(builder));
