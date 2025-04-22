			final WalkRemoteObjectDatabase.FileStream s;
			final IndexPack ip;

			s = connection.open("pack/" + packName);
			ip = IndexPack.create(local, s.in);
			ip.setFixThin(false);
			ip.setObjectChecker(objCheck);
			ip.index(monitor);
			final PackLock keep = ip.renameAndOpenPack(lockMessage);
			if (keep != null)
				packLocks.add(keep);
