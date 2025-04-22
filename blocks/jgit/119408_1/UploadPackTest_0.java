
	@Test
	public void testSingleBranchCloneTagChain() throws Exception {
		RevBlob blob0 = remote.blob("Initial content of first file");
		RevBlob blob1 = remote.blob("Second file content");
		RevCommit commit0 = remote.commit(remote.tree(remote.file("prvni.txt"
		RevCommit commit1 = remote.commit(remote.tree(remote.file("druhy.txt"
		remote.update("master"

        RevTag heavyTag1 = remote.tag("commitTagRing"
        remote.getRevWalk().parseHeaders(heavyTag1);
        RevTag heavyTag2 = remote.tag("middleTagRing"

        UploadPack up = new UploadPack(remote.getRepository());

        ByteArrayOutputStream cli = new ByteArrayOutputStream();
        PacketLineOut clientWant = new PacketLineOut(cli);
        clientWant.writeString("want " + commit1.name() + " multi_ack_detailed include-tag thin-pack ofs-delta agent=tempo/pflaska");
        clientWant.end();
        clientWant.writeString("done\n");

        ByteArrayOutputStream serverResponse = new ByteArrayOutputStream();

        up.setPreUploadHook(new PreUploadHook() {
            @Override
            public void onBeginNegotiateRound(UploadPack up
                Collection<? extends ObjectId> wants
                int cntOffered)
                throws ServiceMayNotContinueException
            {}

            @Override
            public void onEndNegotiateRound(UploadPack up
                Collection<? extends ObjectId> wants
                int cntCommon
                int cntNotFound
                boolean ready)
                throws ServiceMayNotContinueException
            {}

            @Override
            public void onSendPack(UploadPack up
                Collection<? extends ObjectId> wants
                Collection<? extends ObjectId> haves)
                throws ServiceMayNotContinueException
            {
                serverResponse.reset();
            }
        });
        up.upload(new ByteArrayInputStream(cli.toByteArray())
        InputStream packReceived = new ByteArrayInputStream(serverResponse.toByteArray());
        try (ObjectInserter ins = client.newObjectInserter()) {
            PackParser parser = ins.newPackParser(packReceived);
            parser.setAllowThin(true);
			parser.setLockMessage("receive-tag-chain");

            final ProgressMonitor mlc = NullProgressMonitor.INSTANCE;
            ins.flush();
        }
        assertTrue(client.hasObject(blob0.toObjectId()));
        assertTrue(client.hasObject(blob1.toObjectId()));
        assertTrue(client.hasObject(commit0.toObjectId()));
        assertTrue(client.hasObject(commit1.toObjectId()));
        assertTrue(client.hasObject(heavyTag1.toObjectId()));
        assertTrue(client.hasObject(heavyTag2.toObjectId()));
	}

