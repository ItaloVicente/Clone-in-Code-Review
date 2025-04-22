		if (biDirectionalPipe)
			sendAdvertisedRefs();
		else {
			refs = db.getAllRefs();
			for (Ref r : refs.values()) {
				try {
					walk.parseAny(r.getObjectId()).add(ADVERTISED);
				} catch (IOException e) {
				}
			}
		}

