		lock.lock();
		try {
			if (tableStack == null) {
				DfsObjDatabase odb = getRepository().getObjectDatabase();
				if (ctx == null) {
					ctx = odb.newReader();
				}
				tableStack = DfsReftableStack.open(ctx,
						Arrays.asList(odb.getReftables()));
			}
			return tableStack;
		} finally {
			lock.unlock();
		}
