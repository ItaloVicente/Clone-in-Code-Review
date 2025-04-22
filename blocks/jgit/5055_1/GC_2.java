		if (!allHeads.isEmpty()) {
			PackFile heads = writePack(pm
					Collections.<ObjectId> emptySet());
			if (heads != null)
				ret.add(heads);
		}
