		try (PackWriter pw = new PackWriter(repo)) {
			pw.setDeltaBaseAsOffset(true);
			pw.setReuseDeltaCommits(false);
			for (ObjectIdSet idx : excludeObjects)
				pw.excludeObjects(idx);
			pw.preparePack(NullProgressMonitor.INSTANCE
					Collections.<ObjectId> emptySet());
			String id = pw.computeName().getName();
			File packdir = new File(repo.getObjectsDirectory()
			File packFile = new File(packdir
			FileOutputStream packOS = new FileOutputStream(packFile);
			pw.writePack(NullProgressMonitor.INSTANCE
					NullProgressMonitor.INSTANCE
			packOS.close();
			File idxFile = new File(packdir
			FileOutputStream idxOS = new FileOutputStream(idxFile);
			pw.writeIndex(idxOS);
			idxOS.close();
			return PackIndex.open(idxFile);
		}
