		List<ObjectId> toParse = peerHas;
		HashSet<ObjectId> peerHasSet = null;
		boolean needMissing = false;

		if (wantAll.isEmpty() && !wantIds.isEmpty()) {
			peerHasSet = new HashSet<ObjectId>(peerHas);
			int cnt = wantIds.size() + peerHasSet.size();
			toParse = new ArrayList<ObjectId>(cnt);
			toParse.addAll(wantIds);
			toParse.addAll(peerHasSet);
			needMissing = true;
		}

		AsyncRevObjectQueue q = walk.parseAny(toParse
