			try {
				PackParser parser = inserter.newPackParser(s.in);
				parser.setAllowThin(false);
				parser.setObjectChecker(objCheck);
				parser.setLockMessage(lockMessage);
				PackLock lock = parser.parse(monitor);
				if (lock != null)
					packLocks.add(lock);
			} finally {
				s.in.close();
			}
