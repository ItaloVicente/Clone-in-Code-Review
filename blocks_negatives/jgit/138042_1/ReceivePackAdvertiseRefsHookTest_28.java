		TestRepository<Repository> s = new TestRepository<>(src);
		RevBlob blob = s.blob(fakeGitmodules);
		RevCommit N = s.commit().parent(B)
				.add(".gitmodules", blob).create();
		RevTree t = s.parseBody(N).getTree();

		final TemporaryBuffer.Heap pack = new TemporaryBuffer.Heap(1024);
		packHeader(pack, 3);
		copy(pack, src.open(N));
		copy(pack, src.open(t));
		copy(pack, src.open(blob));
		digest(pack);

		final TemporaryBuffer.Heap inBuf = new TemporaryBuffer.Heap(1024);
		final PacketLineOut inPckLine = new PacketLineOut(inBuf);
		inPckLine.writeString(ObjectId.zeroId().name() + ' ' + N.name() + ' '
				+ "refs/heads/s" + '\0'
				+ BasePackPushConnection.CAPABILITY_REPORT_STATUS);
		inPckLine.end();
		pack.writeTo(inBuf, PM);
		return inBuf;
