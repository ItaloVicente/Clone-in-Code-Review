
		if (depth != null) {
			pckState.writeString("deepen " + depth);
		}

		if (deepenSince != null) {
			pckState.writeString("deepen-since " + deepenSince.getEpochSecond());
		}

		if (deepenNotRefs != null) {
			for (String deepenNotRef : deepenNotRefs) {
				pckState.writeString("deepen-not " + deepenNotRef);
			}
		}

		for (ObjectId shallowCommit : local.getObjectDatabase().getShallowCommits()) {
			pckState.writeString("shallow " + shallowCommit.name());
		}

