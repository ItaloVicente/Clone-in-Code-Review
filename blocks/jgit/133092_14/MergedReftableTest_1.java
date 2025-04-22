	@Test
	public void versioningSymbolicReftargetMoves() throws IOException {
		Ref master = ref(MASTER

		List<Ref> delta1 = Arrays.asList(master
		List<Ref> delta2 = Arrays.asList(ref(MASTER

		MergedReftable mr = merge(write(delta1
		Ref head = mr.exactRef(HEAD);
		assertEquals(head.getUpdateIndex()

		Ref masterRef = mr.exactRef(MASTER);
		assertEquals(masterRef.getUpdateIndex()
	}

	@Test
	public void versioningSymbolicRefMoves() throws IOException {
		Ref branchX = ref("refs/heads/branchX"

		List<Ref> delta1 = Arrays.asList(ref(MASTER
				sym(HEAD
		List<Ref> delta2 = Arrays.asList(sym(HEAD
		List<Ref> delta3 = Arrays.asList(sym(HEAD

		MergedReftable mr = merge(write(delta1
				write(delta3
		Ref head = mr.exactRef(HEAD);
		assertEquals(head.getUpdateIndex()

		Ref masterRef = mr.exactRef(MASTER);
		assertEquals(masterRef.getUpdateIndex()

		Ref branchRef = mr.exactRef(MASTER);
		assertEquals(branchRef.getUpdateIndex()
	}

	@Test
	public void versioningResolveRef() throws IOException {
		List<Ref> delta1 = Arrays.asList(sym(HEAD
				sym("refs/heads/tmp"
		List<Ref> delta2 = Arrays.asList(ref(MASTER
		List<Ref> delta3 = Arrays.asList(ref(MASTER

		MergedReftable mr = merge(write(delta1
				write(delta3
		Ref head = mr.exactRef(HEAD);
		Ref resolvedHead = mr.resolve(head);
		assertEquals(resolvedHead.getObjectId()
		assertEquals("HEAD has not moved"

		Ref master = mr.exactRef(MASTER);
		Ref resolvedMaster = mr.resolve(master);
		assertEquals(resolvedMaster.getObjectId()
		assertEquals("master also has update index"
				resolvedMaster.getUpdateIndex()
	}

