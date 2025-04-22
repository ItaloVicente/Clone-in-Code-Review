		ObjectInserter ins = db.newObjectInserter();
		try {
			String lockMsg = "jgit receive-pack";
			if (getRefLogIdent() != null)
				lockMsg += " from " + getRefLogIdent().toExternalString();

			parser = ins.newPackParser(rawIn);
			parser.setAllowThin(true);
			parser.setNeedNewObjectIds(checkReferencedIsReachable);
			parser.setNeedBaseObjectIds(checkReferencedIsReachable);
			parser.setObjectChecking(isCheckReceivedObjects());
			parser.setLockMessage(lockMsg);
			packLock = parser.parse(NullProgressMonitor.INSTANCE);
			ins.flush();
		} finally {
			ins.release();
		}
