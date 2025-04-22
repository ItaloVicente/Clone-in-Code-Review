		try {
			Lfs lfs = new Lfs(db);
			LfsPointer res = LfsPointer.parseLfsPointer(in);
			if (res != null) {
				AnyLongObjectId oid = res.getOid();
				Path mediaFile = lfs.getMediaFile(oid);
				if (!Files.exists(mediaFile)) {
					downloadLfsResource(lfs
				}
				this.in = Files.newInputStream(mediaFile);
