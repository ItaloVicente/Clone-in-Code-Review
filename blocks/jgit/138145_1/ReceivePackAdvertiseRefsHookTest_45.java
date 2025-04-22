		try (TestRepository<Repository> s = new TestRepository<>(src)) {
			RevCommit N = s.commit().parent(B).add("q"
			RevTree t = s.parseBody(N).getTree();

			final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
			packHeader(pack
			copy(pack
			digest(pack);

			final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(1024);
			final PacketLineOut inPckLine = new PacketLineOut(inBuf);
			inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name()
					+ ' ' + "refs/heads/s" + '\0'
					+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
			inPckLine.end();
			pack.writeTo(inBuf

			final TemporaryBuffer.Heap outBuf = new TemporaryBuffer.Heap(1024);
			final ReceivePack rp = new ReceivePack(dst);
			rp.setCheckReceivedObjects(true);
			rp.setCheckReferencedObjectsAreReachable(true);
			rp.setAdvertiseRefsHook(new HidePrivateHook());
			try {
				receive(rp
				fail("Expected UnpackException");
			} catch (UnpackException failed) {
				Throwable err = failed.getCause();
				assertTrue(err instanceof MissingObjectException);
				MissingObjectException moe = (MissingObjectException) err;
				assertEquals(t
			}
