	}

	private void verifyPack(ProgressMonitor pm
			DfsPackFile pack
					throws IOException
		FsckPackParser fpp = new FsckPackParser(objdb
		fpp.setObjectChecker(objChecker);
		fpp.overwriteObjectCount(pack.getPackDescription().getObjectCount());
		fpp.parse(pm);
		errors.getCorruptObjects().addAll(fpp.getCorruptObjects());

		fpp.verifyIndex(fpp.getSortedObjectList(null)
