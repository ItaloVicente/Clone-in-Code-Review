
		if (wantAll.isEmpty() && !wantIds.isEmpty()) {
			peerHasSet = new HashSet<ObjectId>(peerHas);
			int cnt = wantIds.size() + peerHasSet.size();
			toParse = new ArrayList<ObjectId>(cnt);
			toParse.addAll(wantIds);
			toParse.addAll(peerHasSet);
			needMissing = true;
		}

		Set<RevObject> notAdvertisedWants = null;
