		ObjectInserter ins = local.newObjectInserter();
		try {
			PackParser parser = ins.newPackParser(input);
			parser.setAllowThin(thinPack);
			parser.setObjectChecking(transport.isCheckFetchedObjects());
			parser.setLockMessage(lockMessage);
			packLock = parser.parse(monitor);
			ins.flush();
		} finally {
			ins.release();
		}
