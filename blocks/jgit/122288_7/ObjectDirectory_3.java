	private boolean restoreFromSelfOrAlternate(AnyObjectId objectId
			Set<AlternateHandle.Id> skips) {
		if (restoreFromSelf(objectId)) {
			return true;
		}

		skips = addMe(skips);
		for (AlternateHandle alt : myAlternates()) {
			if (!skips.contains(alt.getId())) {
				if (alt.db.restoreFromSelfOrAlternate(objectId
					return true;
				}
			}
		}
		return false;
	}

	private boolean restoreFromSelf(AnyObjectId objectId) {
		PackFile preservedPack = preserved.getPackFile(objectId);
		if (preservedPack == null) {
			return false;
		}
		PackFileName pack = new PackFileName(preservedPack.getPackFile());
		for (PackExt ext : PackExt.values()) {
			if (!PackExt.INDEX.equals(ext)) {
				restore(pack.create(ext));
			}
		}
		restore(pack.create(PackExt.INDEX));
		return true;
	}

	private boolean restore(PackFileName preservedPack) {
		PackFileName restored = preservedPack.createForDirectory(packed.getDirectory()
				false);
		try {
			Files.createLink(restored.toPath()
		} catch (IOException e) {
			return false;
		}
		return true;
	}

