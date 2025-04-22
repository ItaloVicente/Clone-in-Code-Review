		ip = IndexPack.create(db, rawIn);
		ip.setFixThin(true);
		ip.setNeedNewObjectIds(checkReferencedIsReachable);
		ip.setNeedBaseObjectIds(checkReferencedIsReachable);
		ip.setObjectChecking(isCheckReceivedObjects());
		ip.index(NullProgressMonitor.INSTANCE);

		String lockMsg = "jgit receive-pack";
		if (getRefLogIdent() != null)
			lockMsg += " from " + getRefLogIdent().toExternalString();
		packLock = ip.renameAndOpenPack(lockMsg);
