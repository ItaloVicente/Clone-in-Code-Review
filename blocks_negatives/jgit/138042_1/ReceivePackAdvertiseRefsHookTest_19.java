		TestRepository<Repository> s = new TestRepository<>(src);
		RevCommit N = s.commit().parent(B).add("q",
				s.blob(BinaryDelta.apply(dst.open(b).getCachedBytes(), delta)))
				.create();

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack, 3);
		copy(pack, src.open(N));
		copy(pack, src.open(s.parseBody(N).getTree()));
		pack.write((Constants.OBJ_REF_DELTA) << 4 | 4);
		b.copyRawTo(pack);
		deflate(pack, delta);
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(1024);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf, PM);
