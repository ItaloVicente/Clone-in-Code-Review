		PackWriter pw = new PackWriter(repo);
		pw.setDeltaBaseAsOffset(true);
		pw.setReuseDeltaCommits(false);
		for (ObjectIdSet idx : excludeObjects)
			pw.excludeObjects(idx);
		pw.preparePack(NullProgressMonitor.INSTANCE, want,
				Collections.<ObjectId> emptySet());
		String id = pw.computeName().getName();
		File packdir = new File(repo.getObjectsDirectory(), "pack");
		File packFile = new File(packdir, "pack-" + id + ".pack");
		FileOutputStream packOS = new FileOutputStream(packFile);
		pw.writePack(NullProgressMonitor.INSTANCE,
				NullProgressMonitor.INSTANCE, packOS);
		packOS.close();
		File idxFile = new File(packdir, "pack-" + id + ".idx");
		FileOutputStream idxOS = new FileOutputStream(idxFile);
		pw.writeIndex(idxOS);
		idxOS.close();
		pw.release();
		return PackIndex.open(idxFile);
