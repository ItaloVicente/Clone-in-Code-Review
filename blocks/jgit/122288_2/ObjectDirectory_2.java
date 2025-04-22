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
		if (preservedPack != null) {
			if (!restore(preservedPack.getPackFileNames().get(PACK)) ||
				!restore(preservedPack.getPackFileNames().get(INDEX))) {
				return false;
			}
			for (PackFileName name : preservedPack.getPackFileNames().values()) {
				restore(name);
			}
			return true;
		}
		return false;
	}

	private boolean restore(PackFileName preserved) {
		PackFileName restore = preserved.createForDirectory(packed.getDirectory()
				false);
		try {
			Files.createLink(restore.toPath()
		} catch (IOException e) {
			return false;
		}
		return true;
	}

