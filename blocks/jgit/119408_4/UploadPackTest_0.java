
	@Test
	public void testSingleBranchCloneTagChain() throws Exception {
		RevBlob blob0 = remote.blob("Initial content of first file");
		RevBlob blob1 = remote.blob("Second file content");
		RevCommit commit0 = remote
				.commit(remote.tree(remote.file("prvni.txt"
		RevCommit commit1 = remote
				.commit(remote.tree(remote.file("druhy.txt"
		remote.update("master"

		RevTag heavyTag1 = remote.tag("commitTagRing"
		remote.getRevWalk().parseHeaders(heavyTag1);
		RevTag heavyTag2 = remote.tag("middleTagRing"
		remote.lightweightTag("refTagRing"

		UploadPack uploadPack = new UploadPack(remote.getRepository());

		ByteArrayOutputStream cli = new ByteArrayOutputStream();
		PacketLineOut clientWant = new PacketLineOut(cli);
		clientWant.writeString("want " + commit1.name()
				+ " multi_ack_detailed include-tag thin-pack ofs-delta agent=tempo/pflaska");
		clientWant.end();
		clientWant.writeString("done\n");

		try (ByteArrayOutputStream serverResponse = new ByteArrayOutputStream()) {

			uploadPack.setPreUploadHook(new PreUploadHook() {
				@Override
				public void onBeginNegotiateRound(UploadPack up
						Collection<? extends ObjectId> wants
						throws ServiceMayNotContinueException {
				}

				@Override
				public void onEndNegotiateRound(UploadPack up
						Collection<? extends ObjectId> wants
						int cntNotFound
						throws ServiceMayNotContinueException {
				}

				@Override
				public void onSendPack(UploadPack up
						Collection<? extends ObjectId> wants
						Collection<? extends ObjectId> haves)
						throws ServiceMayNotContinueException {
					serverResponse.reset();
				}
			});
			uploadPack.upload(new ByteArrayInputStream(cli.toByteArray())
					serverResponse
			InputStream packReceived = new ByteArrayInputStream(
					serverResponse.toByteArray());
			PackLock lock = null;
			try (ObjectInserter ins = client.newObjectInserter()) {
				PackParser parser = ins.newPackParser(packReceived);
				parser.setAllowThin(true);
				parser.setLockMessage("receive-tag-chain");
				ProgressMonitor mlc = NullProgressMonitor.INSTANCE;
				lock = parser.parse(mlc
				ins.flush();
			} finally {
				if (lock != null) {
					lock.unlock();
				}
			}
			InMemoryRepository.MemObjDatabase objDb = client
					.getObjectDatabase();
			assertTrue(objDb.has(blob0.toObjectId()));
			assertTrue(objDb.has(blob1.toObjectId()));
			assertTrue(objDb.has(commit0.toObjectId()));
			assertTrue(objDb.has(commit1.toObjectId()));
			assertTrue(objDb.has(heavyTag1.toObjectId()));
			assertTrue(objDb.has(heavyTag2.toObjectId()));
		}
	}

