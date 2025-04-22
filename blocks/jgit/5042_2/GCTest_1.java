	@Test
	public void emptyRepo_noPackCreated() throws IOException {
		gc.gc(null);
		assertEquals(0
	}

	@Test
	public void oneNonReferencedObject_pruned() throws Exception {
		tr.blob("a");
		gc.gc(null);
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void nonReferencedObjectTree_pruned() throws Exception {
		tr.tree(tr.file("a"
		gc.gc(null);
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void lightweightTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		tr.lightweightTag("t"
		gc.gc(null);
		assertEquals(1
		assertTrue(packedObjectIDs().contains(a.name()));
		assertEquals(0
	}

	@Test
	public void annotatedTag_objectNotPruned() throws Exception {
		RevBlob a = tr.blob("a");
		RevTag t = tr.tag("t"
		gc.gc(null);
		assertEquals(1
		assertTrue(packedObjectIDs().contains(a.name()));
		assertTrue(packedObjectIDs().contains(t.name()));
		assertEquals(0
	}

	@Test
	public void branch_historyNotPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		gc.gc(null);
		assertEquals(1
		Set<String> packed = packedObjectIDs();
		do {
			assertTrue(packed.contains(tip.name()));
			tr.parseBody(tip);
			RevTree t = tip.getTree();
			assertTrue(packed.contains(t.name()));
			assertTrue(packed.contains(tr.get(t
			tip = tip.getParentCount() > 0 ? tip.getParent(0) : null;
		} while (tip != null);

		assertEquals(0
	}

	@Test
	public void deleteBranch_historyPruned() throws Exception {
		RevCommit tip = commitChain(10);
		tr.branch("b").update(tip);
		RefUpdate update = repo.updateRef("refs/heads/b");
		update.setForceUpdate(true);
		update.delete();
		gc.gc(null);
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void deleteOneBranch_otherBranchHistoryNotPruned() throws Exception {
		RevCommit parent = tr.commit().create();
		tr.branch("b1").commit().parent(parent).create();
		tr.branch("b2").commit().parent(parent).create();
		RefUpdate update = repo.updateRef("refs/heads/b2");
		update.setForceUpdate(true);
		update.delete();
		gc.gc(null);
		assertEquals(1
		assertTrue(packedObjectIDs().contains(parent.name()));
		assertEquals(0
	}


