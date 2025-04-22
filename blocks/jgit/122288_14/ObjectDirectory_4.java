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
		Pack preservedPack = preserved.getPack(objectId);
		if (preservedPack == null) {
			return false;
		}
		PackFile preservedFile = new PackFile(preservedPack.getPackFile());
		for (PackExt ext : PackExt.values()) {
			if (!INDEX.equals(ext)) {
				restore(preservedFile.create(ext));
			}
		}
		restore(preservedFile.create(INDEX));
		return true;
	}

	private boolean restore(PackFile preservedPack) {
		PackFile restored = preservedPack
				.createForDirectory(packed.getDirectory());
		try {
			Files.createLink(restored.toPath()
		} catch (IOException e) {
			return false;
		}
		return true;
	}

