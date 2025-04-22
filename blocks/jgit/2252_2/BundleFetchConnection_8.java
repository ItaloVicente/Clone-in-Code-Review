			ObjectInserter ins = transport.local.newObjectInserter();
			try {
				PackParser parser = ins.newPackParser(bin);
				parser.setAllowThin(true);
				parser.setObjectChecking(transport.isCheckFetchedObjects());
				parser.setLockMessage(lockMessage);
				packLock = parser.parse(NullProgressMonitor.INSTANCE);
				ins.flush();
			} finally {
				ins.release();
			}
