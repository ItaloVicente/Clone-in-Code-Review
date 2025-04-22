
		if (checkReferencedIsReachable) {
			for (ObjectId id : baseObjects) {
				o = ow.parseAny(id);
				if (!o.has(RevFlag.UNINTERESTING))
					throw new MissingObjectException(o
			}
		}
