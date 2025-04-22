		if (srcId.equals(zeroId()))
			return INCOMING | ADDITION;
		if (dstId.equals(zeroId()))
			return OUTGOING | ADDITION;
		if (!srcId.equals(dstId)) {
			RevWalk rw = new RevWalk(repo);
			initializeRevWalk(rw, srcFlag, dstFlag);
