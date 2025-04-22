		try {
			RevObject obj = rw.parseAny(newId);
			if (obj instanceof RevTag) {
				peel = rw.peel(obj);
			}
		} catch (MissingObjectException e) {
