		BaseSearch bases = new BaseSearch(countingMonitor, baseTrees, //
				objectsMap, edgeObjects, reader);
		RevObject o;
		while ((o = walker.nextObject()) != null) {
			if (o.has(RevFlag.UNINTERESTING))
				continue;

			int pathHash = walker.getPathHashCode();
			byte[] pathBuf = walker.getPathBuffer();
			int pathLen = walker.getPathLength();
