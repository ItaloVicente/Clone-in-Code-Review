		ObjectWalk ow = new ObjectWalk(repo);
		ow.markStart(ow.parseAny(data.getSrcRev().getObjectId()));
		srcRev = ow.next();

		ow.reset();
		ow.markStart(ow.parseAny(data.getDstRev().getObjectId()));
		dstRev = ow.next();
