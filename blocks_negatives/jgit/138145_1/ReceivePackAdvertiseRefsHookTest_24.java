		TestRepository<Repository> s = new TestRepository<>(src);
		RevBlob n = s.blob("n");
		RevCommit N = s.commit().parent(B).add("q", n).create();

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack, 2);
		copy(pack, src.open(N));
		copy(pack,src.open(s.parseBody(N).getTree()));
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(1024);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf, PM);
