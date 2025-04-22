	private void packRefTreeGraph(ProgressMonitor pm) throws IOException {
		if (txnHeads.isEmpty())
			return;

		try (PackWriter pw = newPackWriter()) {
			for (ObjectIdSet packedObjs : newPackObj)
				pw.excludeObjects(packedObjs);
			pw.preparePack(pm, txnHeads, NONE);
			if (0 < pw.getObjectCount())
				writePack(GC_TXN, pw, pm, 0 /* unknown pack size */);
		}
	}

