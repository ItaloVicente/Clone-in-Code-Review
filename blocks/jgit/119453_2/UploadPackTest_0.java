		SideBandInputStream sb = new SideBandInputStream(
				recvStream
				new StringWriter()
		PackParser pp = client.newObjectInserter().newPackParser(sb);
		pp.parse(NullProgressMonitor.INSTANCE);
		return pp.getReceivedPackStatistics();
	}
