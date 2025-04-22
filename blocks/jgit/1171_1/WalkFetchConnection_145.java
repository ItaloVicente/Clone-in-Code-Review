		inserter.release();
		reader.release();
		for (final RemotePack p : unfetchedPacks) {
			if (p.tmpIdx != null)
				p.tmpIdx.delete();
		}
