	private List<DfsPackFile> garbagePacksForSelectRepresentation()
			throws IOException {
		DfsPackFile[] packs = db.getPacks();
		List<DfsPackFile> garbage = new ArrayList<>(packs.length);
		for (DfsPackFile p : packs) {
			if (p.getPackDescription().getPackSource() == UNREACHABLE_GARBAGE) {
				garbage.add(p);
			}
		}
		return garbage;
	}

	private static boolean checkGarbagePacks(Iterable<ObjectToPack> objects) {
		for (ObjectToPack otp : objects) {
			if (!((DfsObjectToPack) otp).isFound()) {
				return true;
			}
		}
		return false;
	}

