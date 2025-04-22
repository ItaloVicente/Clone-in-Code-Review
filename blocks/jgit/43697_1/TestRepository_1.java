		try (ObjectWalk ow = new ObjectWalk(db)) {
			if (tips.length != 0) {
				for (RevObject o : tips)
					ow.markStart(ow.parseAny(o));
			} else {
				for (Ref r : db.getAllRefs().values())
					ow.markStart(ow.parseAny(r.getObjectId()));
			}
