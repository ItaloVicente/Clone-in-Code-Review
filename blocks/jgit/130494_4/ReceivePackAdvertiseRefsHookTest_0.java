	@Test
	public void testIncludesInvalidGitmodules() throws Exception {
		String fakeGitmodules = new StringBuilder()
				.append("[submodule \"test\"]\n")
				.append("    path = xlib\n")
				.append("[submodule \"test2\"]\n")
				.append("    path = zlib\n")
				.append("    url = -upayload.sh\n")
				.toString();

		TestRepository<Repository> s = new TestRepository<>(src);
		RevBlob blob = s.blob(fakeGitmodules);
		RevCommit N = s.commit().parent(B)
				.add(".gitmodules"
		RevTree t = s.parseBody(N).getTree();

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack
		copy(pack
		copy(pack
		copy(pack
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(1024);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
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
			assertTrue(err instanceof IOException);
		}

		final PacketLineIn r = asPacketLineIn(outBuf);
		String master = r.readString();
		int nul = master.indexOf('\0');
		assertTrue("has capability list"
		assertEquals(B.name() + ' ' + R_MASTER
		assertSame(PacketLineIn.END

		String errorLine = r.readString();
		System.out.println(errorLine);
		assertTrue(errorLine.startsWith(
				"unpack error Invalid submodule URL '-"));
		assertEquals("ng refs/heads/s n/a (unpacker error)"
		assertSame(PacketLineIn.END
	}

